package resenas.controlador;
import java.util.UUID;
import com.google.gson.Gson;
import resenas.dao.DAODireccion;
import resenas.modelo.Direccion;
import spark.Request;
import spark.Response;

public class ControladorDireccion {
    
    private static DAODireccion daoDireccion = new DAODireccion();
    private static Gson gson = new Gson();

    public static String crearDireccion(Request req, Response res){
        Direccion direccion = gson.fromJson(req.body(), Direccion.class);
        direccion.setId("12345678");
        if (daoDireccion.agregarDireccion(direccion)) {
            return "Dirección agregada correctamente";
        } else {
            return "Error al agregar la dirección";
        }
    }
}
