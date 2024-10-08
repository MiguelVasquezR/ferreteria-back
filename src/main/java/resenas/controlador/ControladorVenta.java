package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;

import resenas.dao.DAOVenta;
import resenas.modelo.Venta;
import spark.Request;
import spark.Response;

public class ControladorVenta {
    private static DAOVenta daoVenta = new DAOVenta();
    private static Gson gson = new Gson();

    public static String guardarVenta(Request req, Response res){
        Venta venta = gson.fromJson(req.body(), Venta.class);
        venta.setIdVenta(UUID.randomUUID().toString());
        System.out.println(venta.toString());
        if (daoVenta.guardarVenta(venta)) {
            return "Venta guardada exitosamente";
        } else {
            return "Error al agregar producto";
        }

    }
    
}
