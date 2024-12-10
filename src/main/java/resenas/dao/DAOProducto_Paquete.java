package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import resenas.conexion.SQLConnection;
import resenas.modelo.Producto_Paquete;

public class DAOProducto_Paquete {
    private SQLConnection sqlConnection = new SQLConnection();

    public boolean agregarProductoPaquete(Producto_Paquete producto_Paquete) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO PRODUCTO_PAQUETE (idProductoPaquete, idProducto, idPaquete)" +
                    "SELECT ?, ?, ? " +
                     "WHERE NOT EXISTS (" +
                     "SELECT 1 FROM PRODUCTO_PAQUETE WHERE idProducto = ? AND idPaquete = ?" +
                     ")");
            ps.setString(1, producto_Paquete.getIdProductoPaquete());
            ps.setString(2, producto_Paquete.getIdProducto());
            ps.setString(3, producto_Paquete.getIdPaquete());

            ps.setString(4, producto_Paquete.getIdProducto());
            ps.setString(5, producto_Paquete.getIdPaquete());

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
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<JsonObject> obtenerListaProductos(String idPaqute) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<JsonObject> productosEnPaquete = new ArrayList<>();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT pp.idPaquete, p.nombre AS nombreProducto " +
                    "FROM PRODUCTO_PAQUETE pp " +
                    "JOIN PRODUCTO p ON pp.idProducto = p.idProducto " +
                    "WHERE pp.idPaquete = ?");
            ps.setString(1, idPaqute);
            rs = ps.executeQuery();
            while (rs.next()) {
                JsonObject productoInfo = new JsonObject();
                productoInfo.addProperty("idPaquete", rs.getString("idPaquete"));
                productoInfo.addProperty("nombreProducto", rs.getString("nombreProducto"));
                productosEnPaquete.add(productoInfo);
            }
            return productosEnPaquete;
        } catch (Exception e) {
            // TODO: handle exception
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

    public List<JsonObject> obtenerPaquetesConProductos() {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<JsonObject> productosEnPaquete = new ArrayList<>();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT\n" + //
                    "    p.idPaquete,\n" + //
                    "    p.descripcion,\n" + //
                    "    p.precio,\n" + //
                    "    prod.idProducto,\n" + //
                    "    prod.nombre\n" + //
                    "FROM\n" + //
                    "    PAQUETE p\n" + //
                    "        JOIN\n" + //
                    "    PRODUCTO_PAQUETE pp ON p.idPaquete = pp.idPaquete\n" + //
                    "        JOIN\n" + //
                    "    PRODUCTO prod ON pp.idProducto = prod.idProducto\n");

            rs = ps.executeQuery();
            while (rs.next()) {
                JsonObject productoInfo = new JsonObject();
                productoInfo.addProperty("idPaquete", rs.getString("idPaquete"));
                productoInfo.addProperty("descripcion", rs.getString("descripcion"));
                productoInfo.addProperty("precio", rs.getInt("precio"));
                productoInfo.addProperty("idProducto", rs.getString("idProducto"));
                productoInfo.addProperty("nombre", rs.getString("nombre"));
                productosEnPaquete.add(productoInfo);
            }
            return productosEnPaquete;
        } catch (Exception e) {
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
