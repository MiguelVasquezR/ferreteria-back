package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;


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
        if (usuario.getId() == null || usuario.getUsuario() == null) {
            throw new IllegalArgumentException("Los campos idUsuario y usuario no pueden ser nulos.");
        }
    
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE USUARIO SET usuario = ?, contrasena = ? WHERE idUsuario = ?");
            ps.setString(1, usuario.getUsuario()); 
            ps.setString(2, usuario.getPassword()); 
            ps.setString(3, usuario.getId()); 
            
            int res = ps.executeUpdate();
            return res > 0; 
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
    
    public List<JsonObject> obtenerSuledoMasComision(){
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<JsonObject> sueldosComisiones= new ArrayList<>();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT u.idUsuario, p.nombre, u.sueldo, COUNT(v.idVenta) AS totalVentas, " +
            "COALESCE(SUM(v.monto), 0) AS totalComision " +
            "FROM USUARIO u " +
            "JOIN PERSONA p ON u.idPersona = p.idPersona " +
            "LEFT JOIN VENTA v ON u.idUsuario = v.idUsuario " +
            "WHERE p.idRol = '00d0d324-31a0-415e-b9b5-9f569bf36da1' " +  
            "GROUP BY u.idUsuario, p.nombre, u.sueldo");
            rs = ps.executeQuery();
        while (rs.next()) {
            JsonObject vendedorInfo = new JsonObject();
            vendedorInfo.addProperty("nombre", rs.getString("nombre"));
            vendedorInfo.addProperty("totalVentas", rs.getInt("totalVentas"));  // número de ventas realizadas
            vendedorInfo.addProperty("totalComision", rs.getDouble("totalComision"));
            vendedorInfo.addProperty("sueldoMasComision", rs.getDouble("sueldo") + rs.getDouble("totalComision"));
            sueldosComisiones.add(vendedorInfo);
        }
        return sueldosComisiones;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
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

    public boolean personaExiste(String idPersona){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT COUNT(*) AS count FROM PERSONA WHERE idPersona = ?");
            ps.setString(1, idPersona);
            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("Cantidad de personas con idPersona " + idPersona + ": " + count);
                return count > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally{
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null && !con.isClosed()) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean agregarUsuario(Usuario usuario){
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("INSERT INTO USUARIO (idUsuario, idPersona, usuario, contrasena, sueldo) VALUES (?, ?, ?, ?, ?) ");
            ps.setString(1, usuario.getId());
            ps.setString(2, usuario.getIdPersona());
            ps.setString(3, usuario.getUsuario());
            ps.setString(4, usuario.getPassword());
            ps.setDouble(5, usuario.getSueldo());
            int res = ps.executeUpdate();
            if (res>0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
            
        }finally{
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
