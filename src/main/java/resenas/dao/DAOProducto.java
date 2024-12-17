package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

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
                    "INSERT INTO PRODUCTO (idProducto, urlImage, codigo, nombre, cantidad, stockMinimo, costo, precioMenudeo, precioMayoreo, estado, descripcion, idPersona) "
                            +
                            "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
                            "WHERE NOT EXISTS (" +
                            "    SELECT 1 FROM PRODUCTO WHERE nombre = ? AND idPersona = ?" +
                            ")");
            ps.setString(1, producto.getIdProducto());
            ps.setString(2, producto.getUrlImage());
            ps.setString(3, producto.getCodigo());
            ps.setString(4, producto.getNombre());
            ps.setFloat(5, producto.getCantidad());
            ps.setFloat(6, producto.getStockMinimo());
            ps.setFloat(7, producto.getCosto());
            ps.setFloat(8, producto.getPrecioMenudeo());
            ps.setFloat(9, producto.getPrecioMayoreo());
            ps.setString(10, "Disponible");
            ps.setString(11, producto.getDescripcion());
            ps.setString(12, producto.getIdPersona());

            ps.setString(13, producto.getNombre());
            ps.setString(14, producto.getIdPersona());
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

    public ArrayList<JsonObject> getProducts() {
        sqlConnection = new SQLConnection();
        ArrayList<JsonObject> productos = new ArrayList<JsonObject>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT\n" + //
                    "    p.idProducto, p.urlImage, p.codigo, p.nombre, p.cantidad, p.stockMinimo,\n" + //
                    "    p.costo, p.precioMenudeo, p.precioMayoreo, p.estado, p.descripcion,\n" + //
                    "    per.idPersona, per.nombre AS nombrePersona, per.telefono, per.correo,\n" + //
                    "    per.rfc, per.estado AS estadoPersona\n" + //
                    "FROM\n" + //
                    "    PRODUCTO p\n" + //
                    "        LEFT JOIN\n" + //
                    "    PERSONA per ON p.idPersona = per.idPersona\n" + //
                    "WHERE p.estado = 'Disponible';\n" + //
                    ";\n" + //
                    "");
            rs = ps.executeQuery();
            while (rs.next()) {
                JsonObject producto = new JsonObject();
                producto.addProperty("idProducto", rs.getString("idProducto"));
                producto.addProperty("urlImage", rs.getString("urlImage"));
                producto.addProperty("codigo", rs.getString("codigo"));
                producto.addProperty("nombre", rs.getString("nombre"));
                producto.addProperty("cantidad", rs.getFloat("cantidad"));
                producto.addProperty("stockMinimo", rs.getFloat("stockMinimo"));
                producto.addProperty("costo", rs.getFloat("costo"));
                producto.addProperty("precioMenudeo", rs.getFloat("precioMenudeo"));
                producto.addProperty("precioMayoreo", rs.getFloat("precioMayoreo"));
                producto.addProperty("estado", rs.getString("estado"));
                producto.addProperty("descripcion", rs.getString("descripcion"));

                producto.addProperty("idPersona", rs.getString("idPersona"));
                producto.addProperty("nombrePersona", rs.getString("nombrePersona"));
                producto.addProperty("telefono", rs.getString("telefono"));
                producto.addProperty("correo", rs.getString("correo"));
                producto.addProperty("rfc", rs.getString("rfc"));
                producto.addProperty("estadoPersona", rs.getString("estadoPersona"));
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

    public JsonObject obtenerProductoByID(String idProducto) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonObject producto = new JsonObject();

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "SELECT " +
                            "    p.idProducto, p.urlImage, p.codigo, p.nombre, p.cantidad, p.stockMinimo, " +
                            "    p.costo, p.precioMenudeo, p.precioMayoreo, p.estado, p.descripcion, " +
                            "    per.idPersona, per.nombre AS nombrePersona, per.telefono, per.correo, " +
                            "    per.rfc, per.estado AS estadoPersona " +
                            "FROM " +
                            "    PRODUCTO p " +
                            "JOIN " +
                            "    PERSONA per ON p.idPersona = per.idPersona " +
                            "WHERE " +
                            "    p.idProducto = ?;");

            ps.setString(1, idProducto);
            rs = ps.executeQuery();

            if (rs.next()) {
                producto.addProperty("idProducto", rs.getString("idProducto"));
                producto.addProperty("urlImage", rs.getString("urlImage"));
                producto.addProperty("codigo", rs.getString("codigo"));
                producto.addProperty("nombre", rs.getString("nombre"));
                producto.addProperty("cantidad", rs.getFloat("cantidad"));
                producto.addProperty("stockMinimo", rs.getFloat("stockMinimo"));
                producto.addProperty("costo", rs.getFloat("costo"));
                producto.addProperty("precioMenudeo", rs.getFloat("precioMenudeo"));
                producto.addProperty("precioMayoreo", rs.getFloat("precioMayoreo"));
                producto.addProperty("estado", rs.getString("estado"));
                producto.addProperty("descripcion", rs.getString("descripcion"));

                producto.addProperty("idPersona", rs.getString("idPersona"));
                producto.addProperty("nombrePersona", rs.getString("nombrePersona"));
                producto.addProperty("telefono", rs.getString("telefono"));
                producto.addProperty("correo", rs.getString("correo"));
                producto.addProperty("rfc", rs.getString("rfc"));
                producto.addProperty("estadoPersona", rs.getString("estadoPersona"));
            }

            return producto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (con != null)
                    con.close();
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
                    "UPDATE PRODUCTO SET estado = ? WHERE idProducto = ? ");
            ps.setString(1, "No disponible");
            ps.setString(2, idProducto);
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
                    "UPDATE PRODUCTO SET urlImage = ?, codigo = ?, nombre = ?, cantidad = ?, stockMinimo = ?, costo = ?, precioMenudeo = ?, precioMayoreo = ? WHERE idProducto = ?");
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

    public boolean personalizarStockMinimo(Producto producto, String idProducto) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE PRODUCTO SET stockMinimo = ? WHERE idProducto = ?");
            ps.setFloat(1, producto.getStockMinimo());
            ps.setString(2, idProducto);
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

    public float obtenerStock(String codigo) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT stockMinimo FROM PRODUCTO WHERE codigo = ?");
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("stockMinimo");
            }

            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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

    public boolean actualizarCantidad(float cantidad, String codigo) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE PRODUCTO SET cantidad = ? WHERE codigo = ?");
            ps.setFloat(1, cantidad);
            ps.setString(2, codigo);
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

    public boolean actualizarStock(String codigo, float cantidad) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE PRODUCTO SET stockMinimo = ? WHERE codigo = ?");
            ps.setFloat(1, cantidad);
            ps.setString(2, codigo);
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

    public List<String> obtenerProductosPocoStock() {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> productos = new ArrayList<String>();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT nombre FROM PRODUCTO WHERE cantidad < stockMinimo");
            rs = ps.executeQuery();
            while (rs.next()) {
                productos.add(rs.getString("nombre"));
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

    public Producto obtenerProductoID(String idProducto) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Producto producto = new Producto();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM PRODUCTO WHERE idProducto = ?");
            ps.setString(1, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setUrlImage(rs.getString("urlImage"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCantidad(rs.getFloat("cantidad"));
                producto.setStockMinimo(rs.getFloat("stockMinimo"));
                producto.setCosto(rs.getFloat("costo"));
                producto.setPrecioMenudeo(rs.getFloat("precioMenudeo"));
                producto.setPrecioMayoreo(rs.getFloat("precioMayoreo"));
                producto.setEstado(rs.getString("estado"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setIdPersona(rs.getString("idPersona"));
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

}
