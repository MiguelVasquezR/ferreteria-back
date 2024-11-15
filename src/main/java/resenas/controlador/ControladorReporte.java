package resenas.controlador;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAOReporte;
import resenas.modelo.ReporteDiario;
import spark.Request;
import spark.Response;

public class ControladorReporte {
    private static DAOReporte daoReporte = new DAOReporte();
    private static Gson gson = new Gson();

    public static String guardarReporte(Request req, Response res){
        ReporteDiario reporte = gson.fromJson(req.body(), ReporteDiario.class);
        reporte.setIdReporte(UUID.randomUUID().toString());
        if (daoReporte.guadarReporte(reporte)) {
            return "Reporte guardado exitosamente";
        } else {
            return "Error al guardar reporte";
        }

    }    

    public static String obtenerReportePorFrecuencia(Request req, Response res) {
        String frecuencia = req.queryParams("frecuencia");
        if (frecuencia == null || frecuencia.isEmpty()) {
            res.status(400);
            return "El parámetro 'frecuencia' es obligatorio.";
        }

        List<ReporteDiario> reportes = daoReporte.obtenerReportesPorFrecuencia(frecuencia);
        if (reportes.isEmpty()) {
            res.status(404);
            return "No se encontraron reportes para la frecuencia especificada.";
        }

        return gson.toJson(reportes);
    }

    public static String obtenerReporteDaños(Request req, Response res){
        String idReporte = req.queryParams("idReporte");
        JsonObject reporte = daoReporte.obtenerReporteProdcutoDañado(idReporte);
        if (reporte != null) {
            return gson.toJson(reporte);
        } else {
            return "Reporte no encontrado";
        }
    }

}
