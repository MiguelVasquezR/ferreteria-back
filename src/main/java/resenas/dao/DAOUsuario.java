package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import resenas.conexion.SQLConnection;
import resenas.modelo.Usuario;

public class DAOUsuario {

    private SQLConnection sqlConnection;

    public Usuario validarCredenciales(String usuario, String contrasena) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            sqlConnection = new SQLConnection();
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM usuario where usuario = ? and contrasena = ?");
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getString("id"));
                user.setUser(rs.getString("usuario"));
                user.setPassword(rs.getString("contrasena"));
                user.setRol(rs.getString("rol"));
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                con.close();
                if (con.isClosed()) {
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
