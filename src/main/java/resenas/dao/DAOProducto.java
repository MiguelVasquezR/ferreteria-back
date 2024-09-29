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
                    "INSERT INTO producto (idProducto, urlImage, codigo, nombre, cantidad, stockMinimo, costo, precioMenudeo, precioMayoreo) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, producto.getIdProducto());
            ps.setString(2, producto.getUrlImage());
            ps.setString(3, producto.getCodigo());
            ps.setString(4, producto.getNombre());
            ps.setFloat(5, producto.getCantidad());
            ps.setFloat(6, producto.getStockMinimo());
            ps.setFloat(7, producto.getCosto());
            ps.setFloat(8, producto.getPrecioMenudeo());
            ps.setFloat(9, producto.getPrecioMayoreo());
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
                producto.setIdPrducto(rs.getString("idProducto"));
                producto.setUrlImage(rs.getString("urlImage"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getFloat("cantidad"));
                producto.setStockMinimo(rs.getFloat("stockMinimo"));
                producto.setCosto(rs.getFloat("costo"));
                producto.setPrecioMenudeo(rs.getFloat("precioMenudeo"));
                producto.setPrecioMayoreo(rs.getFloat("precioMayoreo"));
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

    public Producto obtenerProductoByID(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producto producto = new Producto();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM producto WHERE idProducto = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                producto.setIdPrducto(rs.getString("idProducto"));
                producto.setUrlImage(rs.getString("urlImage"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getFloat("cantidad"));
                producto.setStockMinimo(rs.getFloat("stockMinimo"));
                producto.setCosto(rs.getFloat("costo"));
                producto.setPrecioMenudeo(rs.getFloat("precioMenudeo"));
                producto.setPrecioMayoreo(rs.getFloat("precioMayoreo"));
            }
            return producto;
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

    public boolean eliminarProducto(String idProducto) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();

            ps = con.prepareStatement(
                    "DELETE FROM producto WHERE idProducto = ? ");
            ps.setString(1, idProducto);
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

    public boolean editarProducto(Producto producto) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE producto SET urlImage = ?, codigo = ?, nombre = ?, cantidad = ?, stockMinimo = ?, costo = ?, precioMenudeo = ?, precioMayoreo = ? WHERE idProducto = ?");
            ps.setString(1, producto.getUrlImage());
            ps.setString(2, producto.getCodigo());
            ps.setString(3, producto.getNombre());
            ps.setFloat(4, producto.getCantidad());
            ps.setFloat(5, producto.getStockMinimo());
            ps.setFloat(6, producto.getCosto());
            ps.setFloat(7, producto.getPrecioMenudeo());
            ps.setFloat(8, producto.getPrecioMayoreo());
            ps.setString(9, producto.getIdProducto());
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
