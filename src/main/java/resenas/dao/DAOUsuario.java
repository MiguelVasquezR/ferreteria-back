package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import resenas.conexion.SQLConnection;
import resenas.modelo.Usuario;

public class DAOUsuario {

    private SQLConnection sqlConnection = new SQLConnection();

    public JsonObject validarCredenciales(String usuario, String contrasena) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonObject json = new JsonObject();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "SELECT r.nombre, u.usuario, u.idUsuario FROM USUARIO as u JOIN PERSONA as p ON u.idPersona = p.idPersona JOIN ROLES as r ON p.idRol = r.idRol WHERE u.usuario = ? AND u.contrasena = ?");
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            if (rs.next()) {
                json.addProperty("rol", rs.getString("nombre"));
                json.addProperty("usuario", rs.getString("usuario"));
                json.addProperty("id", rs.getString("idUsuario"));
                return json;
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

    public String cambiarContrasena(String correo, String nuevaContrasena) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE USUARIO SET contraseña=? WHERE correo=?");
            ps.setString(1, nuevaContrasena);
            ps.setString(2, correo);
            int res = ps.executeUpdate();
            if (res > 0) {
                return "Se ha actualizado la contraseña";
            }
            return "No se ha actualizado la contraseña";
        } catch (Exception e) {
            e.printStackTrace();
            return "No se ha actualizado la contraseña";
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
