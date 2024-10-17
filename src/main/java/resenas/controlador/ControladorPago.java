package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;

import resenas.dao.DAOPago;
import resenas.dao.DAOPersona;
import resenas.modelo.Pago;
import resenas.modelo.Persona;
import spark.Request;
import spark.Response;

public class ControladorPago {
     private static DAOPago daoPago = new DAOPago();
    private static Gson gson = new Gson();

    public static String crearPago(Request req, Response res){
        Pago pago = gson.fromJson(req.body(), Pago.class);
        pago.setIdPago(UUID.randomUUID().toString());
        if (daoPago.agregarPago(pago)) {
            return "Pago guardado correctamente";
        } else {
            return "Error al guardar el pago";
        }
    }
}
