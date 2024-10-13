package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;

import resenas.dao.DAOReporte;
import resenas.modelo.Reporte;
import spark.Request;
import spark.Response;

public class ControladorReporte {
    private static DAOReporte daoReporte = new DAOReporte();
    private static Gson gson = new Gson();

    public static String guardarReporte(Request req, Response res){
        Reporte reporte = gson.fromJson(req.body(), Reporte.class);
        reporte.setIdReporte(UUID.randomUUID().toString());
        System.out.println(reporte.toString());
        if (daoReporte.guadarReporte(reporte)) {
            return "Reporte guardado exitosamente";
        } else {
            return "Error al guardar reporte";
        }

    }    
}
