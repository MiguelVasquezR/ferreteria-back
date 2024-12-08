package resenas.controlador;


import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAOOferta;
import resenas.modelo.Oferta;

import spark.Request;
import spark.Response;

public class ControladorOferta {
    private static DAOOferta daoOferta = new DAOOferta();
    private static Gson gson = new Gson();

    public static String crearOferta(Request req, Response res) {
        JsonObject json = gson.fromJson(req.body(), JsonObject.class);

        Oferta oferta = new Oferta();
        oferta.setIdOferta(UUID.randomUUID().toString());
        oferta.setIdProducto(json.get("idProducto").getAsString());
        oferta.setPrecioOferta(json.get("precio").getAsDouble());
        String fecha = json.get("fechaFinal").getAsString();
        oferta.setFechaFinal(java.sql.Date.valueOf(fecha));
        oferta.setFechaInicio(new java.sql.Date(System.currentTimeMillis()));
        oferta.setDetalles("");
        try {
            if (daoOferta.agergarOferta(oferta)) {
                return "Oferta agregada correctamente";
            } else {
                return "Oferta en Existencia";
            } 
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al agregar oferta";
        }
       
    }

    public static String finalizarOferta(Request req, Response res) {
        daoOferta.actualizarEstadoFinalizado();
        return ("Estado de la oferta actualizado a finalizado, si corresponde.");
    }

    public static String obtenerOfertas(Request req, Response res) {
        if (daoOferta.getOffers() != null) {
            return gson.toJson(daoOferta.getOffers());
        } else {
            return "No has agregado ofertas";
        }
    }
}
