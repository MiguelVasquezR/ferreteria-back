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
                res.cookie("ACCESS_TOKEN", token);
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

// Clase de usuario
class User {
    private String user;
    private String password;

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUsername() {
        return user;
    }

    public void setUsername(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [password=" + password + ", user=" + user + "]";
    }
}
