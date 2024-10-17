package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import resenas.conexion.SQLConnection;
import resenas.modelo.Pago;

public class DAOPago {
    private SQLConnection sqlConnection = new SQLConnection();

    public boolean agregarPago(Pago Pago) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO PAGO (idPago, idUsuario, tipo, monto, fecha, estado) VALUES (?,?,?,?,?,?)");
            ps.setString(1, Pago.getIdPago());
            ps.setString(2, Pago.getIdUsuario());
            ps.setString(3, Pago.getTipo());
            ps.setFloat(4, Pago.getMonto());
            ps.setDate(5, Pago.getFecha());
            ps.setString(6, Pago.getEstado());

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
                    ps.close();
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Pago obtenerPagoById(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Pago pago = new Pago();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM PAGO WHERE idPago = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                pago.setIdPago(rs.getString("idPago"));
                pago.setIdUsuario(rs.getString("idUsuario"));
                pago.setTipo(rs.getString("tipo"));
                pago.setMonto(rs.getFloat("monto"));
                pago.setFecha(rs.getDate("fecha"));
                pago.setEstado(rs.getString("estado"));
            }
            return pago;
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
