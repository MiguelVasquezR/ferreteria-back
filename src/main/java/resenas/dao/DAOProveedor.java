package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import resenas.conexion.SQLConnection;

public class DAOProveedor {

    private SQLConnection sqlConnection = new SQLConnection();

    public ArrayList<JsonObject> obtenerProveedores() {

        ArrayList<JsonObject> proveedores = new ArrayList<JsonObject>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "SELECT P.idPersona, P.nombre, P.telefono, P.correo, P.rfc, R.nombre, D.ciudad, D.colonia, D.calle, D.numero FROM PERSONA P JOIN ROLES R ON P.idRol = R.idRol JOIN DIRECCION D ON P.idDireccion = D.idDireccion WHERE R.nombre = ?");
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
