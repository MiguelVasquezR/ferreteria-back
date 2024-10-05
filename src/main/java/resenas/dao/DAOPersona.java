package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import resenas.conexion.SQLConnection;
import resenas.modelo.Persona;
import resenas.modelo.Producto;
import resenas.modelo.Proveedor;

public class DAOPersona {
    private SQLConnection sqlConnection = new SQLConnection();

    public boolean agregarPersona(Persona Persona) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("INSERT INTO persona VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, Persona.getId());
            ps.setString(2, Persona.getId_direccion());
            ps.setString(3, Persona.getNombre());
            ps.setString(4, Persona.getTelefono());
            ps.setString(5, Persona.getCorreo());
            ps.setString(6, Persona.getRfc());
            ps.setString(7, Persona.getIdRol());
            int res = ps.executeUpdate();
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            try {
                con.close();
                if (con.isClosed()) {
                    ps.close();
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


     public boolean editarProveedor(Persona persona) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE persona SET nombre = ?, telefono = ?, correo = ?, rfc = ? WHERE idPersona = ?");
            ps.setString(1,persona.getNombre());
            ps.setString(2,persona.getTelefono());
            ps.setString(3,persona.getCorreo());
            ps.setString(4,persona.getRfc());
            ps.setString(5,persona.getId());
            int res = ps.executeUpdate();
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    public Persona obtenerPersonaById(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Persona persona = new Persona();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM persona WHERE idPersona = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                persona.setId(rs.getString("idPersona"));
                persona.setId_direccion(rs.getString("idDireccion"));
                persona.setNombre(rs.getString("nombre"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setCorreo(rs.getString("correo"));
                persona.setRfc(rs.getString("rfc"));
            }
            return persona;
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
