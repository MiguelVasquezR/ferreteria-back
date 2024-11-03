package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import resenas.conexion.SQLConnection;
import resenas.modelo.Oferta;
import resenas.modelo.Producto;

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

     public boolean actualizarEstadoFinalizado() {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
      
        try {
    
            con = sqlConnection.getConnection();

             // Verifica que la conexión no sea null
        if (con == null) {
            throw new NullPointerException("La conexión a la base de datos es null");
        }

            ps = con.prepareStatement("UPDATE OFERTA SET estado = ? WHERE fechaFinal <= CURRENT_DATE");
            ps.setString(1, "Finalizado");
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
        
         public ArrayList<Oferta> getOffers() {
        sqlConnection = new SQLConnection();
        ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM OFERTA where estado != 'Finalizado'");
            rs = ps.executeQuery();
            while (rs.next()) {
                Oferta oferta = new Oferta();
                oferta.setIdOferta(rs.getString("idOferta"));
                oferta.setIdProducto(rs.getString("idProducto"));
                oferta.setFechaInicio(rs.getDate("fechaInicio"));
                oferta.setFechaFinal(rs.getDate("fechaFinal"));
                oferta.setDetalles(rs.getString("detalles"));
                oferta.setEstado(rs.getString("estado"));
                oferta.setPrecioOferta(rs.getDouble("precioOferta"));
                ofertas.add(oferta);
            }
            return ofertas;
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
