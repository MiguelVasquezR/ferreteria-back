package resenas;

import static spark.Spark.*;

import com.google.gson.Gson;

import resenas.conexion.SQLConnection;
import resenas.services.AutenticationService;
import resenas.utils.JwtUtils;

public class App {

    private static Gson gson = new Gson();
    static SQLConnection sqlConnection = new SQLConnection();

    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        post("/login", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            String token = AutenticationService.login(user.getUsername(), user.getPassword());
            if (token == null) {
                halt(401, "Usuario o contraseña incorrectos");
            } else {
                res.header("Authorization", token.toString());
                halt(200, "Usuario autenticado");
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
            String token = request.headers("Authorization");
            if (token == null) {
                halt(401, "Acceso no autorizado");
                return;
            }
            boolean isTokenValid = JwtUtils.verifyToken(token);
            if (isTokenValid) {
                return;
            } else {
                if (JwtUtils.isTokenExpired(token)) {
                    String newToken = JwtUtils.renewAccessToken(token);
                    if (newToken != null) {
                        response.header("Authorization", newToken);
                    } else {
                        halt(401, "Token expirado y no se pudo renovar");
                    }
                } else {
                    halt(401, "Token inválido");
                }
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

// Clase de usuario
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [password=" + password + ", username=" + username + "]";
    }
}
