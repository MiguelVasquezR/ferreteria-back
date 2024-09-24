package resenas.controlador;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAODireccion;
import resenas.modelo.Direccion;
import spark.Request;
import spark.Response;

public class ControladorDireccion {
    
    private static DAODireccion daoDireccion = new DAODireccion();
    private static Gson gson = new Gson();

    public static JsonObject crearDireccion(Request req, Response res){
        Direccion direccion = gson.fromJson(req.body(), Direccion.class);
        direccion.setId(UUID.randomUUID().toString());
        if (daoDireccion.agregarDireccion(direccion)) {

             JsonObject response = new JsonObject();
             
    response.addProperty("id", direccion.getId());
    response.addProperty("message", "Dirección agregada correctamente");
            
    return response;
            
        } else {
            JsonObject response = new JsonObject();
            response.addProperty("message", "Error al agregar la direccion");
            return response;
        }
    }
}
