package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.google.gson.JsonObject;

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
            ps = con.prepareStatement("INSERT INTO PERSONA VALUES (?,?,?,?,?,?,?)");
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
                    "UPDATE PERSONA SET idPersona = ?, idDireccion = ?, nombre = ?, telefono = ?, correo = ?, rfc = ? WHERE idPersona = ? AND tipo ='proveedor'");
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

    public ArrayList<JsonObject> obtenerProveedores() {

        ArrayList<JsonObject> proveedores = new ArrayList<JsonObject>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "SELECT P.idPersona, P.nombre, P.telefono, P.correo, P.rfc, R.nombre, D.ciudad, D.colonia, D.calle, D.numero, P.estado FROM PERSONA P JOIN ROLES R ON P.idRol = R.idRol JOIN DIRECCION D ON P.idDireccion = D.idDireccion WHERE R.nombre = ? AND P.estado != 'Inactivo'");
            ps.setString(1, "Proveedor");
            rs = ps.executeQuery();
            while (rs.next()) {
                JsonObject proveedor = new JsonObject();
                proveedor.addProperty("idPersona", rs.getString(1));
                proveedor.addProperty("nombre", rs.getString(2));
                proveedor.addProperty("telefono", rs.getString(3));
                proveedor.addProperty("correo", rs.getString(4));
                proveedor.addProperty("rfc", rs.getString(5));
                proveedor.addProperty("rol", rs.getString(6));
                proveedor.addProperty("ciudad", rs.getString(7));
                proveedor.addProperty("colonia", rs.getString(8));
                proveedor.addProperty("calle", rs.getString(9));
                proveedor.addProperty("numero", rs.getString(10));
                proveedor.addProperty("estado", rs.getString(11));
                proveedores.add(proveedor);
            }
            return proveedores;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

}
