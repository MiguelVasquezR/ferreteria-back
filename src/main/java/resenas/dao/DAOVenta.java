package resenas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import resenas.conexion.SQLConnection;
import resenas.modelo.Producto_Venta;
import resenas.modelo.Venta;

public class DAOVenta {
    private SQLConnection sqlConnection;

    public boolean guardarVenta(Venta venta) {
        System.out.println(venta);
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO VENTA (idVenta, idUsuario, monto, fecha, cantidadProductos) VALUES(?, ?, ?, ?,?)");
            ps.setString(1, venta.getIdVenta());
            ps.setString(2, venta.getIdUsuario());
            ps.setFloat(3, venta.getMonto());
            ps.setDate(4, venta.getFecha());
            ps.setFloat(5, venta.getCantidad());

            System.out.println(ps);
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

    public boolean guardarProductoVenta(Producto_Venta pv) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO PRODUCTO_VENTA (idProductoVenta, idProducto, idVenta, cantidad) VALUES(?, ?, ?, ?)");
            ps.setString(1, pv.getIdProductoVenta());
            ps.setString(2, pv.getIdProducto());
            ps.setString(3, pv.getIdVenta());
            ps.setFloat(4, pv.getCantidad());
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

    public ArrayList<Venta> ventaDiaria(Date fecha) {
        sqlConnection = new SQLConnection();
        PreparedStatement ps = null;
        ArrayList<Venta> vDiaria = new ArrayList<Venta>();
        Connection con = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT *FROM venta WHERE DATE(fecha) = ?");
            ps.setDate(1, fecha);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getString("idVenta"));
                venta.setIdUsuario(rs.getString("idUsuario"));
                venta.setCantidad(rs.getFloat("monto"));
                venta.setFecha(rs.getDate("fecha"));
                vDiaria.add(venta);
            }
            return vDiaria;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (con.isClosed()) {
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean actualizarVenta(Venta venta) {
        System.out.println(venta.toString());
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE VENTA SET monto = ?, cantidadProductos=? WHERE idVenta = ?");
            ps.setFloat(1, venta.getMonto());
            ps.setFloat(2, venta.getCantidad());
            ps.setString(3, venta.getIdVenta());
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
