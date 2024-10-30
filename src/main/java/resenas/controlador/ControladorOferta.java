package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import resenas.dao.DAOOferta;
import resenas.modelo.Oferta;
import spark.Request;
import spark.Response;

public class ControladorOferta {
    private static DAOOferta daoOferta = new DAOOferta();
    private static Gson gson = new Gson();

    public static String crearProducto(Request req, Response res) {
        Oferta oferta = gson.fromJson(req.body(), Oferta.class);
        oferta.setIdOferta(UUID.randomUUID().toString());
        System.out.println(oferta.toString());
        if (daoOferta.agergarOferta(oferta)) {
            return "Oferta agregada correctamente";
        } else {
            return "Error al agregar oferta";
        }
    }

    public static String finalizarOferta(Request req, Response res) {
        String idOferta = req.queryParams("idOferta");
        daoOferta.actualizarEstadoFinalizado(idOferta);
        return ("Estado de la oferta actualizado a finalizado, si corresponde.");
    }
}
