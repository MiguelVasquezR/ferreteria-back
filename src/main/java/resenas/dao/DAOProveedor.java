package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import resenas.conexion.SQLConnection;
import resenas.modelo.Persona;
import resenas.modelo.Proveedor;

public class DAOProveedor {
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

    public boolean editarProveedor(Proveedor proveedor) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE persona SET idPersona = ?, idDireccion = ?, nombre = ?, telefono = ?, correo = ?, rfc = ? WHERE idPersona = ? AND tipo ='proveedor'");
            ps.setString(1, proveedor.getId());
            ps.setString(2, proveedor.getId_direccion());
            ps.setString(3, proveedor.getNombre());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getCorreo());
            ps.setString(6, proveedor.getRfc());
            ps.setString(7, proveedor.getId());
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
}
