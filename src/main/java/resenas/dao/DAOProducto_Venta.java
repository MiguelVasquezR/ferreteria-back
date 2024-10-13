package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import resenas.conexion.SQLConnection;
import resenas.modelo.Producto_Venta;

public class DAOProducto_Venta {
    private SQLConnection sqlConnection;

    public ArrayList<Producto_Venta> menosVendidos(){
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Producto_Venta> producto_ventas = new ArrayList<Producto_Venta>();
        ResultSet rs = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT idProducto, SUM(cantidad) AS total_vendido FROM producto_venta GROUP BY idProducto ORDER BY total_vendido ASC"
            );
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto_Venta producto_Venta = new Producto_Venta();
                producto_Venta.setIdProductoVenta(rs.getString("idProducto"));
                producto_Venta.setIdProducto(rs.getString("idProducto"));
                // Aquí puedes agregar idVenta si lo necesitas
                producto_Venta.setCantidad(rs.getFloat("total_vendido"));
                producto_ventas.add(producto_Venta);
            }
            return producto_ventas;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Producto_Venta> masVendidos(){
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ArrayList<Producto_Venta> producto_ventas = new ArrayList<Producto_Venta>();
        ResultSet rs = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                "SELECT idProducto, SUM(cantidad) AS total_vendido FROM producto_venta GROUP BY idProducto ORDER BY total_vendido DESC"
            );
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto_Venta producto_Venta = new Producto_Venta();
                producto_Venta.setIdProductoVenta(rs.getString("idProducto"));
                producto_Venta.setIdProducto(rs.getString("idProducto"));
                // Aquí puedes agregar idVenta si lo necesitas
                producto_Venta.setCantidad(rs.getFloat("total_vendido"));
                producto_ventas.add(producto_Venta);
            }
            return producto_ventas;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}