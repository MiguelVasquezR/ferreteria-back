package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import resenas.conexion.SQLConnection;
import resenas.modelo.Paquete;

public class DAOPaquete {
    private SQLConnection sqlConnection = new SQLConnection();
    
    public boolean agregarPaquete(Paquete paquete){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("INSERT INTO PAQUETE (idPaquete, precio, descripcion) VALUES (?, ?, ?)");
            ps.setString(1, paquete.getIdPaquete());
            ps.setInt(2, paquete.getPrecio());
            ps.setString(3, paquete.getDescripcion());

            int res = ps.executeUpdate();
            if (res>0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
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
    
}
