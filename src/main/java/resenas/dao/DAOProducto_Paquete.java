package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import resenas.conexion.SQLConnection;
import resenas.modelo.Paquete;
import resenas.modelo.Producto;
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

    public List<Paquete> obtenerPaquetesConProductos() {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT p.descripcion   as descripcion,\n" +
                    "       p.precio        as precio,\n" +
                    "       p.nombre        as nombrePaquete,\n" +
                    "       p.idPaquete     as IDPaquete,\n" +
                    "       prod.idProducto as IDProducto,\n" +
                    "       prod.nombre     as nombreProducto\n" +
                    "FROM PAQUETE p\n" +
                    "         INNER JOIN PRODUCTO_PAQUETE pp ON p.idPaquete = pp.idPaquete\n" +
                    "         INNER JOIN PRODUCTO prod ON pp.idProducto = prod.idProducto");

            rs = ps.executeQuery();

            Map<String, Paquete> mapaPaquetes = new HashMap<>();
            List<Paquete> listaPaquetes = new ArrayList<>();

            while (rs.next()) {
                String idPaquete = rs.getString("IDPaquete");
                Paquete paquete = mapaPaquetes.get(idPaquete);
                if (paquete == null) {
                    paquete = new Paquete();
                    paquete.setIdPaquete(idPaquete);
                    paquete.setNombre(rs.getString("nombrePaquete"));
                    paquete.setDescripcion(rs.getString("descripcion"));
                    paquete.setPrecio(rs.getFloat("precio"));
                    paquete.setProductos(new ArrayList<>());
                    mapaPaquetes.put(idPaquete, paquete);
                    listaPaquetes.add(paquete);
                }
                Producto producto = new Producto();
                producto.setIdProducto(rs.getString("IDProducto"));
                producto.setNombre(rs.getString("nombreProducto"));
                paquete.getProductos().add(producto);
            }
            return listaPaquetes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        finally {
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
