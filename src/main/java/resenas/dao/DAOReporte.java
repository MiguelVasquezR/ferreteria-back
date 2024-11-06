package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import resenas.conexion.SQLConnection;
import resenas.modelo.ReporteDiario;

public class DAOReporte {
    private SQLConnection sqlConnection;

      // Constructor para inicializar sqlConnection
      public DAOReporte() {
        this.sqlConnection = new SQLConnection();
    }

    public boolean guadarReporte(ReporteDiario reporte) {
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

    public List<ReporteDiario> obtenerReportesPorFrecuencia(String frecuencia) {
        List<ReporteDiario> reportes = new ArrayList<>();
        String queryBase = "SELECT p.nombre, p.urlImage, p.precioMenudeo, SUM(pv.cantidad) AS cantidad_vendida " +
                           "FROM PRODUCTO_VENTA pv " +
                           "INNER JOIN PRODUCTO p ON pv.idProducto = p.idProducto " +
                           "INNER JOIN VENTA v ON pv.idVenta = v.idVenta WHERE ";
    
        // Ajustar la cláusula WHERE según la frecuencia
        switch (frecuencia.toLowerCase()) {
            case "diario":
                queryBase += "DATE(v.fecha) = CURDATE() ";
                break;
            case "semanal":
                queryBase += "YEARWEEK(v.fecha, 1) = YEARWEEK(CURDATE(), 1) ";
                break;
            case "mensual":
                queryBase += "MONTH(v.fecha) = MONTH(CURDATE()) AND YEAR(v.fecha) = YEAR(CURDATE()) ";
                break;
            case "anual":
                queryBase += "YEAR(v.fecha) = YEAR(CURDATE()) ";
                break;
            default:
                // Si la frecuencia no es válida, devolver lista vacía o manejar como prefieras
                return reportes;
        }
    
        queryBase += "GROUP BY p.nombre, p.urlImage, p.precioMenudeo " +
                     "ORDER BY cantidad_vendida DESC"; // Cambia ASC si prefieres ascendente
    
        try (Connection con = sqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(queryBase);
             ResultSet rs = ps.executeQuery()) {
    
            while (rs.next()) {
                ReporteDiario reporte = new ReporteDiario();
                reporte.setnombre(rs.getString("nombre"));
                reporte.setUrlImage(rs.getString("urlImage"));
                reporte.setPrecioMenudeo(rs.getFloat("precioMenudeo")); // Asegúrate de tener este método
                reporte.setCantidad(rs.getFloat("cantidad_vendida")); // Añadir este método si necesitas almacenar la cantidad vendida
                reportes.add(reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportes;
    }
    

}
