package resenas.controlador;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

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

        Persona persona = daoPersona.obtenerPersonaById(reporte.getIdProveedor());
        Direccion direccion = daoDireccion.obtenerDireccionById(persona.getId_direccion());
        Producto producto = daoProducto.obtenerProductoID(reporte.getIdProducto());

        return Correo.llenarComprobante(producto, persona, direccion, reporte);
        

        /*
         * if (daoReporte.guadarReporte(reporte)) {
         * return "Reporte guardado exitosamente";
         * } else {
         * return "Error al guardar reporte";
         * }
         */

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

}
