package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import resenas.conexion.SQLConnection;
import resenas.modelo.Producto;

public class DAOProducto {
    private SQLConnection sqlConnection = new SQLConnection();
    
    public boolean agregarProducto(Producto producto){
        Connection con = null;
        PreparedStatement ps = null;
    
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("INSERT INTO producto (id_producto, nombre, cantidad, stock_minimo, costo, precio_menudeo, precio_mayoreo) VALUES(?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setFloat(3, producto.getCantidad());
            ps.setFloat(4, producto.getStock_minimo());
            ps.setFloat(5, producto.getCosto());
            ps.setFloat(6, producto.getPrecio_menudeo());
            ps.setFloat(7, producto.getPrecio_mayoreo());
            int res = ps.executeUpdate();
            if (res > 0) {
                return true;
            }  else{
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
