package resenas.services;

import resenas.utils.JwtUtils;

public class AutenticationService {

    public static String login(String username, String password) {
        if (username.equals("Miguel") && password.equals("Miguel1209")) {
            // Recibe el rol y el usuario
            return JwtUtils.generateToken("admin", username);
        }
        return null;
    }

    public static boolean isAuthenticated(String token) {
        return JwtUtils.verifyToken(token);
    }

}
