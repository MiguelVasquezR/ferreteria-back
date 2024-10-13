package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import resenas.conexion.SQLConnection;
import resenas.modelo.Proveedor;
import resenas.modelo.Usuario;
import resenas.modelo.Venta;

public class DAOVenta {
    private SQLConnection sqlConnection;
    
    public boolean guardarVenta(Venta venta){
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO venta (idVenta, idUsuario, monto, fecha) VALUES(?, ?, ?, ?)");
            ps.setString(1, venta.getIdVenta());
            ps.setString(2, venta.getIdUsuario());
            ps.setFloat(3, venta.getMonto());
            ps.setDate(4, venta.getFecha());
            
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

    public boolean editarVenta(Venta venta) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE venta SET monto = ?, fecha = ? WHERE idVenta = ? AND idUsuario = ?");
            ps.setFloat(1, venta.getMonto());
            ps.setDate(2, venta.getFecha());
            ps.setString(3, venta.getIdVenta());
            ps.setString(4, venta.getIdUsuario()); // Asegúrate de que el ID del usuario sea parte de la venta
            
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

    public Venta obtenerVentaById(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Venta venta = null; // Inicializar como null para retornar null si no se encuentra la venta
        
        try {
            con = sqlConnection.getConnection();
            System.out.println("Conexión a la base de datos exitosa");
            
    
            // Verificar que el ID no sea null o vacío
            if (id == null || id.trim().isEmpty()) {
                System.out.println("ID proporcionado es nulo o vacío.");
                return null;
            }
            
            System.out.println("Buscando venta con ID: " + id);
            ps = con.prepareStatement("SELECT * FROM venta WHERE idVenta = ?");
            ps.setString(1, id);
    
            rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("No se encontró ninguna venta con el ID proporcionado.");
            } else {
                // Crear un nuevo objeto Venta y asignar valores
                venta = new Venta();
                venta.setIdVenta(rs.getString("idVenta"));
                venta.setIdUsuario(rs.getString("idUsuario"));
                venta.setMonto(rs.getFloat("monto"));
                venta.setFecha(rs.getDate("fecha"));
                
                System.out.println("Venta encontrada: " + venta.toString());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al buscar la venta: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
                System.out.println("Conexión cerrada");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return venta; // Retornar la venta encontrada o null si no se encontró
    }
    

    public boolean eV(Venta venta) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE venta SET idUsuario = ?, monto = ?, fecha = ? WHERE idVenta = ?");
            ps.setString(1, venta.getIdUsuario()); // Asegúrate de que el ID del usuario sea parte de la venta
            ps.setFloat(2, venta.getMonto());
            ps.setDate(3, venta.getFecha());
            ps.setString(4, venta.getIdVenta());
            
            
            
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