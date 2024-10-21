package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import resenas.conexion.SQLConnection;
import resenas.modelo.Producto_Paquete;

public class DAOProducto_Paquete {
    private SQLConnection sqlConnection = new SQLConnection();

    public boolean agregarProductoPaquete(Producto_Paquete producto_Paquete){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("INSERT INTO PRODUCTO_PAQUETE (idProductoPaquete, idProducto, idPaquete) VALUES (?, ?, ?)");
            ps.setString(1, producto_Paquete.getIdProductoPaquete());
            ps.setString(2, producto_Paquete.getIdProducto());
            ps.setString(3, producto_Paquete.getIdPaquete());

            int res = ps.executeUpdate();
            if (res > 0) {
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
                    sqlConnection.closeConnection();
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
