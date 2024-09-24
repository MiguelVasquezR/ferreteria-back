package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import resenas.conexion.SQLConnection;
import resenas.modelo.Direccion;
import resenas.modelo.Persona;

public class DAODireccion {
     private SQLConnection sqlConnection = new SQLConnection();
    public boolean agregarDireccion (Direccion direccion){
        Connection con = null;
        PreparedStatement ps = null;

        try {
        con=sqlConnection.getConnection();
        ps=con.prepareStatement("INSERT INTO direccion VALUES (?,?,?,?,?)");
        ps.setString(1, direccion.getId());
        ps.setString(2, direccion.getCiudad());
        ps.setString(3, direccion.getColonia());
        ps.setString(4, direccion.getCalle());
        ps.setInt(5, direccion.getNumero());

            int res=ps.executeUpdate();
            if (res>0) {
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
}
