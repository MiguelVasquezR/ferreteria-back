package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import resenas.conexion.SQLConnection;
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
                    "INSERT INTO VENTA (idVenta, idUsuario, monto, fecha) VALUES(?, ?, ?, ?)");
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
    
}
