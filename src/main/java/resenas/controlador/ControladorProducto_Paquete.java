package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import resenas.dao.DAOProducto_Paquete;
import resenas.modelo.Paquete;
import resenas.modelo.Producto;
import resenas.modelo.Producto_Paquete;
import spark.Request;
import spark.Response;

public class ControladorProducto_Paquete {
    private static DAOProducto_Paquete daoProducto_Paquete = new DAOProducto_Paquete();
    private static Gson gson = new Gson();

    public static String agregarProductoPaquete(Request req, Response res){
        try {
            Producto_Paquete producto_Paquete = gson.fromJson(req.body(), Producto_Paquete.class);
            Producto producto = gson.fromJson(req.body(), Producto.class);
            Paquete paquete = gson.fromJson(req.body(), Paquete.class);

            producto_Paquete.setIdProductoPaquete(UUID.randomUUID().toString());
            producto_Paquete.setIdProducto(producto.getIdProducto());
            producto_Paquete.setIdPaquete(paquete.getIdPaquete());

            if(daoProducto_Paquete.agregarProductoPaquete(producto_Paquete)){
                res.status(201);
                return "Producto_Paquete agregado exitosamente";
            } else {
                res.status(500); // Error interno del servidor
                return "Error al agregar producto_paquete";
            }

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
            res.status(400);
            return "Error al procesar la solicitud";
        }
    }
}
