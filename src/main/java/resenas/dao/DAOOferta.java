package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import resenas.conexion.SQLConnection;
import resenas.modelo.Oferta;

public class DAOOferta {
    private SQLConnection sqlConnection;

    public boolean agergarOferta(Oferta oferta) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO OFERTA (idOferta, idProducto, fechaInicio, fechaFinal, detalles, estado) VALUES(?, ?, ?, ?, ?, ?)");
            ps.setString(1, oferta.getIdOferta());
            ps.setString(2, oferta.getIdProducto());
            ps.setDate(3, oferta.getFechaInicio());
            ps.setDate(4, oferta.getFechaFinal());
            ps.setString(5,  oferta.getDetalles());
            ps.setString(6,  ("Disponible"));
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

     public boolean actualizarEstadoFinalizado(String idOferta) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
      
        try {
    
            con = sqlConnection.getConnection();

             // Verifica que la conexión no sea null
        if (con == null) {
            throw new NullPointerException("La conexión a la base de datos es null");
        }

            ps = con.prepareStatement("UPDATE OFERTA SET estado = ? WHERE idOferta = ? AND fechaFinal <= CURRENT_DATE");
            ps.setString(1, "finalizado");
            ps.setString(2, idOferta);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Error en los parámetros: " + e.getMessage());
            return false;
        } finally{
             // Cierra PreparedStatement
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Cierra Connection
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
