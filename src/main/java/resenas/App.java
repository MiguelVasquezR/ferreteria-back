package resenas;

import static spark.Spark.*;

import com.google.gson.Gson;
import resenas.conexion.SQLConnection;
import resenas.utils.JwtUtils;

import resenas.controlador.ControladorUsuario;
import resenas.modelo.Usuario;

public class App {

    private static Gson gson = new Gson();
    static SQLConnection sqlConnection = new SQLConnection();

    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        post("/login", (req, res) -> {
            Usuario dataCliente = gson.fromJson(req.body(), Usuario.class);
            Usuario usuario = ControladorUsuario.iniciarSesion(dataCliente.getUser(), dataCliente.getPassword());
            if (usuario != null) {
                String token = JwtUtils.generateToken(usuario.getRol(), usuario.getUser());
                if (token != null || token != "") {
                    res.cookie("ACCESS_TOKEN", token);
                    res.status(200);
                } else {
                    res.body("No logramos autenticar al usuario");
                    res.status(401);
                }
            } else {
                res.status(401);
                res.body("Usuario o contraseña incorrectos");
            }
            return null;
        });

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        before((request, response) -> {
            String path = request.pathInfo();
            if ("/login".equals(path)) {
                return;
            }
            String token = request.cookie("ACCESS_TOKEN");
            System.out.println(token);
            if (token == null) {
                halt(401, "Acceso no autorizado");
                return;
            }
            String messageVerifiedToken = JwtUtils.verifyToken(token);
            System.out.println(messageVerifiedToken);
            if (messageVerifiedToken.equals("Token valido")) {
                return;
            } else if (messageVerifiedToken.equals("Token invalido")) {
                halt(401, "Acceso no autorizado");
                return;
            }
        });

        // Ruta protegida por el middleware
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