package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import resenas.conexion.SQLConnection;
import resenas.modelo.Paquete;

public class DAOPaquete {
    private SQLConnection sqlConnection = new SQLConnection();

    public boolean agregarPaquete(Paquete paquete) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();

            ps = con.prepareStatement("INSERT INTO PAQUETE (idPaquete, nombre, precio, descripcion, estado)" +
                    "SELECT ?, ?, ?, ?, ?" +
                    "WHERE NOT EXISTS (" +
                    "SELECT 1 FROM PAQUETE WHERE idPaquete = ?" +
                    ")");
            ps.setString(1, paquete.getIdPaquete());
            ps.setString(2, paquete.getNombre());
            ps.setFloat(3, paquete.getPrecio());
            ps.setString(4, paquete.getDescripcion());
            ps.setString(5, "Disponible");

            ps.setString(6, paquete.getIdPaquete());
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

    public Paquete obtenerById(String idPaquete) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Paquete paquete = new Paquete();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM PAQUETE WHERE idPaquete = ?");
            ps.setString(1, idPaquete);
            rs = ps.executeQuery();
            while (rs.next()) {
                paquete.setNombre(rs.getString("nombre"));
                paquete.setIdPaquete(rs.getString("idPaquete"));
                paquete.setPrecio(rs.getFloat("precio"));
                paquete.setDescripcion(rs.getString("descripcion"));
                paquete.setEstado(rs.getString("estado"));
            }
            return paquete;
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

    public ArrayList<Paquete> obtenerPaquetes() {
        sqlConnection = new SQLConnection();
        ArrayList<Paquete> paquetes = new ArrayList<Paquete>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM PAQUETE where estado != 'No disponible'");
            rs = ps.executeQuery();
            while (rs.next()) {
                Paquete paquete = new Paquete();
                paquete.setNombre(rs.getString("nombre"));
                paquete.setIdPaquete(rs.getString("idPaquete"));
                paquete.setPrecio(rs.getFloat("precio"));
                paquete.setDescripcion(rs.getString("descripcion"));
                paquete.setEstado(rs.getString("estado"));
                paquetes.add(paquete);
            }
            return paquetes;
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

    public boolean editarPaquete(Paquete paquete) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE PAQUETE SET nombre = ?, precio = ?, descripcion = ?, estado = ? WHERE idPaquete = ?");
            ps.setString(1, paquete.getNombre());
            ps.setFloat(2, paquete.getPrecio());
            ps.setString(3, paquete.getDescripcion());
            ps.setString(4, paquete.getEstado());
            ps.setString(5, paquete.getIdPaquete());
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

    public boolean eliminarPaquete(String idPaquete) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("UPDATE PAQUETE SET estado = ? WHERE idPaquete = ?");
            ps.setString(1, "No disponible");
            ps.setString(2, idPaquete);
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

    public boolean validarPaquete(Paquete paquete) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM PAQUETE WHERE nombre=? AND ABS(precio - ?) < 0.01;");
            ps.setString(1, paquete.getNombre());
            ps.setFloat(2, paquete.getPrecio());
            rs = ps.executeQuery();
            if (rs.next()) {
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

    public boolean validarProductoPaquete(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM PRODUCTO_PAQUETE WHERE idProducto = ?;");
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
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
