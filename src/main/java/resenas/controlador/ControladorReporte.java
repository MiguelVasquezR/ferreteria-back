package resenas.controlador;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAODireccion;
import resenas.dao.DAOPersona;
import resenas.dao.DAOProducto;
import resenas.dao.DAOReporte;
import resenas.modelo.Direccion;
import resenas.modelo.Persona;
import resenas.modelo.Producto;
import resenas.modelo.ReporteDiario;
import resenas.utils.Correo;
import spark.Request;
import spark.Response;

public class ControladorReporte {
    private static DAOReporte daoReporte = new DAOReporte();
    private static Gson gson = new Gson();

    public static String guardarReporte(Request req, Response res) {
        ReporteDiario reporte = gson.fromJson(req.body(), ReporteDiario.class);
        reporte.setIdReporte(UUID.randomUUID().toString());

        DAOPersona daoPersona = new DAOPersona();
        DAODireccion daoDireccion = new DAODireccion();
        DAOProducto daoProducto = new DAOProducto();

        Producto producto = daoProducto.obtenerProductoID(reporte.getNombre());
        Persona persona = daoPersona.obtenerPersonaById(producto.getIdPersona());
        Direccion direccion = daoDireccion.obtenerDireccionById(persona.getId_direccion());

        if (daoReporte.guadarReporte(reporte)) {
            String path = Correo.llenarComprobante(producto, persona, direccion, reporte);
            if (path != null || !path.equals("")) {

                if (Correo.productoDanadoo(path, persona.getCorreo(), producto.getNombre())) {
                    return "Reporte guardado correctamente";
                } else {
                    return "Reporte no guardado";
                }
            }
        }

        return "Reporte no guardado";

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

    public static String obtenerReporteDaños(Request req, Response res) {
        String idReporte = req.queryParams("idReporte");
        JsonObject reporte = daoReporte.obtenerReporteProdcutoDañado(idReporte);
        if (reporte != null) {
            return gson.toJson(reporte);
        } else {
            return "Reporte no encontrado";
        }
    }

}
