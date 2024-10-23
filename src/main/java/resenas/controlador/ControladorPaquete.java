package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import resenas.dao.DAOPaquete;
import resenas.modelo.Paquete;
import spark.Request;
import spark.Response;

public class ControladorPaquete {
    private static DAOPaquete daoPaquete = new DAOPaquete();
    private static Gson gson = new Gson();

    public static String agregarPaquete(Request req, Response res){
        Paquete paquete = gson.fromJson(req.body(), Paquete.class);
        paquete.setIdPaquete(UUID.randomUUID().toString());
        if (daoPaquete.agregarPaquete(paquete)) {
            return "Paquete agregado exitosamente";
        } else {
            return "Error al agregar paquete";
        }

    }

    public static String obtenerById(Request req, Response res){
        String idPaquete = req.queryParams("idPaquete");
        Paquete paquete = daoPaquete.obtenerById(idPaquete);
        if (paquete != null) {
            return gson.toJson(paquete);
            
        } else {
            return "Paquete no existente";
            
        }
    }

    public static String editarPaquete(Request req, Response res){
        Paquete paquete = gson.fromJson(req.body(), Paquete.class);
        if (daoPaquete.editarPaquete(paquete)) {
            return "Datos del paquete editdos exitosamente";
        }else{
            return "No se guardaron los cambios";
        }
    }
}
