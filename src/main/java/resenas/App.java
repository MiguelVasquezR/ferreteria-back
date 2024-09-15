package resenas;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.conexion.SQLConnection;
import resenas.utils.Correo;
import resenas.utils.Encriptar;
import resenas.utils.JwtUtils;
import resenas.controlador.ControladorUsuario;
import resenas.modelo.Usuario;
import resenas.utils.FileBinario;

public class App {

    private static Gson gson = new Gson();
    static SQLConnection sqlConnection = new SQLConnection();
    private static Map<String, String> tokensPassword = new HashMap<String, String>();
    private static FileBinario fileBinario = new FileBinario();

    static {
        tokensPassword = fileBinario.readBinaryFile();
    }

    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Credentials", "true");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, ACCESS_TOKEN");
        });
        options("/*", (request, response) -> {
            response.status(200);
            return "OK";
        });

        post("/login", (req, res) -> {
            Usuario dataCliente = gson.fromJson(req.body(), Usuario.class);
            String contrasenEncriptada = Encriptar.encriptar(dataCliente.getPassword());
            System.out.println(contrasenEncriptada);
            Usuario usuario = ControladorUsuario.iniciarSesion(dataCliente.getUser(), contrasenEncriptada);
            if (usuario != null) {
                String token = JwtUtils.generateToken(usuario.getRol(), usuario.getUser());
                if (token != null && !token.isEmpty()) {
                    res.header("Access-Control-Expose-Headers", "ACCESS_TOKEN");
                    res.header("Access-Control-Expose-Headers", "ROL");
                    res.header("ACCESS_TOKEN", token);
                    res.header("ROL", usuario.getRol());
                    return "Usuario autenticado";
                } else {
                    res.status(200);
                    return "No logramos autenticar al usuario";
                }
            } else {
                res.status(200);
                return "Usuario o contraseña incorrectos";
            }
        });

        get("/olvide-contrasena", (req, res) -> {
            String correo = req.queryParams("correo");
            String token = JwtUtils.generateTokenPassword(correo);
            tokensPassword.put(correo, token);
            String mensaje = Correo.olvideContrasena(correo, token);
            res.status(200);
            fileBinario.writeBinaryFile(tokensPassword);
            tokensPassword = fileBinario.readBinaryFile();
            return mensaje;
        });

        put("/cambiar-contrasena", (req, res) -> {
            JsonObject data = gson.fromJson(req.body(), JsonObject.class);
            String token = data.get("token").getAsString();
            String contrasena = data.get("password").getAsString();
            for (Map.Entry<String, String> entry : tokensPassword.entrySet()) {
                if (entry.getValue().equals(token)) {
                    String isValidToken = JwtUtils.verifyToken(token);
                    if (isValidToken.equals("Token valido")) {
                        String email = JwtUtils.obtenerCorreo(token);
                        String message = ControladorUsuario.cambiarContrasena(email, Encriptar.encriptar(contrasena));
                        tokensPassword.remove(entry.getKey());
                        fileBinario.writeBinaryFile(tokensPassword);
                        tokensPassword = fileBinario.readBinaryFile();
                        return message;
                    }
                }
            }
            return "Ha vencido el tiempo para actualizar la contraseña";
        });

        before((req, res) -> {
            String path = req.pathInfo();
            if ("/login".equals(path)) {
                return;
            }
            if ("/olvide-contrasena".equals(path)) {
                return;
            }
            if ("/cambiar-contrasena".equals(path)) {
                return;
            }
            String token = req.headers("Authorization");
            if (token == null || token.isEmpty()) {
                halt(401, "Acceso no autorizado");
                return;
            }
            String messageVerifiedToken = JwtUtils.verifyToken(token.replace("Bearer ", ""));
            if (messageVerifiedToken.equals("Token valido")) {
                return;
            } else {
                halt(401, "Acceso no autorizado");
                return;
            }
        });

        // Rutas protegidas por el middleware
        get("/", (req, res) -> {
            return "Hola mundo";
        });

    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
