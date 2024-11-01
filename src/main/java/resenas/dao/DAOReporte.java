package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import resenas.conexion.SQLConnection;
import resenas.modelo.ReporteDagno;

public class DAOReporte {
    private SQLConnection sqlConnection;

    public boolean guadarReporte(ReporteDagno reporte) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO REPORTE (idReporte, nombre, urlImage, descripcion) VALUES(?,?,?,?)");
            ps.setString(1, reporte.getIdReporte());
            ps.setString(2, reporte.getnombre());
            ps.setString(3, reporte.getUrlImage());
            ps.setString(4, reporte.getDescripcion());

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
