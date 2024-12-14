package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import resenas.dao.DAOPaquete;
import resenas.dao.DAOProducto_Paquete;
import resenas.modelo.Paquete;
import resenas.modelo.Producto;
import resenas.modelo.Producto_Paquete;
import spark.Request;
import spark.Response;

public class ControladorPaquete {
    private static DAOPaquete daoPaquete = new DAOPaquete();
    private static Gson gson = new Gson();
    static boolean existe = true;

    public static String agregarPaquete(Request req, Response res) {
        Paquete paquete = gson.fromJson(req.body(), Paquete.class);
        paquete.setIdPaquete(UUID.randomUUID().toString());

        if(daoPaquete.validarPaquete(paquete)){
            for(Producto producto : paquete.getProductos()){
                if(!daoPaquete.validarProductoPaquete(producto.getIdProducto())){
                    existe = false;
                }
            }
        }else{
            existe = false;
        }

        if(existe){
            return "El paquete ya existe, verifica los productos, el nombre y precio de tus paquetes en la lista de paquetes";
        }

        if (daoPaquete.agregarPaquete(paquete)) {
            Producto_Paquete producto_Paquete;
            DAOProducto_Paquete daoProducto_Paquete;
            for (Producto producto : paquete.getProductos()) {
                producto_Paquete = new Producto_Paquete();
                producto_Paquete.setIdProductoPaquete(UUID.randomUUID().toString());
                producto_Paquete.setIdProducto(producto.getIdProducto());
                producto_Paquete.setIdPaquete(paquete.getIdPaquete());
                daoProducto_Paquete = new DAOProducto_Paquete();
                daoProducto_Paquete.agregarProductoPaquete(producto_Paquete);
            }
            return "Paquete agregado exitosamente";
        } else {
            return "Error al agregar paquete";
        }
    }

    public static String obtenerPaquetes(Request req, Response res) {
        if (daoPaquete.obtenerPaquetes() != null) {
            return gson.toJson(daoPaquete.obtenerPaquetes());
        } else {
            return "No has agregado paquetes";
        }
    }

    public static String obtenerById(Request req, Response res) {
        String idPaquete = req.queryParams("idPaquete");
        Paquete paquete = daoPaquete.obtenerById(idPaquete);
        if (paquete != null) {
            return gson.toJson(paquete);

        } else {
            return "Paquete no existente";

        }
    }

    public static String editarPaquete(Request req, Response res) {
        Paquete paquete = gson.fromJson(req.body(), Paquete.class);
        if (daoPaquete.editarPaquete(paquete)) {
            return "Datos del paquete editdos exitosamente";
        } else {
            return "No se guardaron los cambios";
        }
    }

    public static String eliminarPaquete(Request req, Response res) {
        String idPaquete = req.queryParams("idPaquete");
        if (daoPaquete.eliminarPaquete(idPaquete)) {
            return "Paquete eliminado exitosamente";
        } else {
            return "Error al elminar paquete";
        }
    }
}
