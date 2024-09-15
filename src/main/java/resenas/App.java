package resenas;

import static spark.Spark.*;

import com.google.gson.Gson;
import resenas.conexion.SQLConnection;
import resenas.utils.Encriptar;
import resenas.utils.JwtUtils;
import resenas.controlador.ControladorUsuario;
import resenas.modelo.Usuario;

public class App {

    private static Gson gson = new Gson();
    static SQLConnection sqlConnection = new SQLConnection();

    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Credentials", "true");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, ACCESS_TOKEN");
        });
        options("/*", (request, response) -> {
            return "OK";
        });

        post("/login", (req, res) -> {
            Usuario dataCliente = gson.fromJson(req.body(), Usuario.class);
            Usuario usuario = ControladorUsuario.iniciarSesion(dataCliente.getUser(), dataCliente.getPassword());
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

        before((req, res) -> {
            String path = req.pathInfo();
            if ("/login".equals(path)) {
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
