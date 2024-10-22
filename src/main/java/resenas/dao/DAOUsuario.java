package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import resenas.conexion.SQLConnection;
import resenas.modelo.Persona;
import resenas.modelo.Usuario;
import resenas.modelo.Venta;

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

    public Usuario obtenerUsuarioById(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuario = new Usuario();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM usuario WHERE idUsuario = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getString("idUsuario"));
                usuario.setIdPersona(rs.getString("idPersona"));
                usuario.setUser(rs.getString("usuario"));
                usuario.setPassword(rs.getString("contraseña"));
            }
            return usuario;
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

    public boolean editarUsuario(Usuario usuario) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
    
        // Verificar que los campos no sean nulos
        if (usuario.getId() == null || usuario.getUser() == null) {
            throw new IllegalArgumentException("Los campos idUsuario y usuario no pueden ser nulos.");
        }
    
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE usuario SET usuario = ?, contraseña = ? WHERE idUsuario = ?");
            ps.setString(1, usuario.getUser()); // Asegúrate de que este campo tiene un valor válido
            ps.setString(2, usuario.getPassword()); // Asegúrate de que este campo tiene un valor válido
            ps.setString(3, usuario.getPassword()); // Aquí se espera el ID del usuario que se está actualizando
            
            int res = ps.executeUpdate();
            return res > 0; // Retorna verdadero si se actualizó algo
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    

    

}
