package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;

import resenas.dao.DAOReporte;
import resenas.modelo.ReporteDagno;
import spark.Request;
import spark.Response;

public class ControladorReporte {
    private static DAOReporte daoReporte = new DAOReporte();
    private static Gson gson = new Gson();

    public static String guardarReporte(Request req, Response res){
        ReporteDagno reporte = gson.fromJson(req.body(), ReporteDagno.class);
        reporte.setIdReporte(UUID.randomUUID().toString());
        if (daoReporte.guadarReporte(reporte)) {
            return "Reporte guardado exitosamente";
        } else {
            return "Error al guardar reporte";
        }

    }    
}
