package resenas.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonObject;

import resenas.conexion.SQLConnection;
import resenas.utils.JwtUtils;

public class AutenticationService {

    String usernameDB = "Miguel";
    String passwordDB = "Miguel1209";
    static SQLConnection sqlConnection = new SQLConnection();
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static String login(String username, String password) {
        try {
            Connection connection = sqlConnection.getConnection();
            ps = connection.prepareStatement("SELECT * FROM user WHERE user = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (username.equals(rs.getString("user")) && password.equals(rs.getString("password"))) {
                    return JwtUtils.generateToken(rs.getString("rol"), rs.getString("user"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String isAuthenticated(String token) {
        return JwtUtils.verifyToken(token);
    }

}
