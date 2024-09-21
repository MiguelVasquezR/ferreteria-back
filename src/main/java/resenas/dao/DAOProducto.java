package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import resenas.conexion.SQLConnection;
import resenas.modelo.Producto;

public class DAOProducto {
    private SQLConnection sqlConnection;

    public boolean agregarProducto(Producto producto) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO producto (id_producto, url_image, codigo, nombre, cantidad, stock_minimo, costo, precio_menudeo, precio_mayoreo) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, producto.getId());
            ps.setString(2, producto.getUrl_image());
            ps.setString(3, producto.getCodigo());
            ps.setString(4, producto.getNombre());
            ps.setFloat(5, producto.getCantidad());
            ps.setFloat(6, producto.getStock_minimo());
            ps.setFloat(7, producto.getCosto());
            ps.setFloat(8, producto.getPrecio_menudeo());
            ps.setFloat(9, producto.getPrecio_mayoreo());
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

    public ArrayList<Producto> getProducts() {
        sqlConnection = new SQLConnection();
        ArrayList<Producto> productos = new ArrayList<Producto>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM producto");
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getString("id_producto"));
                producto.setUrl_image(rs.getString("url_image"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getFloat("cantidad"));
                producto.setStock_minimo(rs.getFloat("stock_minimo"));
                producto.setCosto(rs.getFloat("costo"));
                producto.setPrecio_menudeo(rs.getFloat("precio_menudeo"));
                producto.setPrecio_mayoreo(rs.getFloat("precio_mayoreo"));
                productos.add(producto);
            }
            return productos;
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
