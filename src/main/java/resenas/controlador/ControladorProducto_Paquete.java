package resenas.controlador;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import resenas.dao.DAOPaquete;
import resenas.dao.DAOProducto_Paquete;
import resenas.modelo.Paquete;
import resenas.modelo.Producto;
import resenas.modelo.Producto_Paquete;
import spark.Request;
import spark.Response;

public class ControladorProducto_Paquete {
    private static DAOProducto_Paquete daoProducto_Paquete = new DAOProducto_Paquete();
    private static DAOPaquete daoPaquete = new DAOPaquete();
    private static Gson gson = new Gson();

    public static String agregarProductoPaquete(Request req, Response res) {
        try {
            JsonObject jsonPaquete = gson.fromJson(req.body(), JsonObject.class);

            String nombre = jsonPaquete.get("nombre").getAsString();
            int precio = jsonPaquete.get("precio").getAsInt();
            String descripcion = jsonPaquete.get("descripcion").getAsString();

            Type tipoListaProductos = new TypeToken<List<Producto>>() {
            }.getType();
            List<Producto> productos = gson.fromJson(jsonPaquete.get("productos"), tipoListaProductos);

            Paquete paquete = new Paquete();
            paquete.setIdPaquete(UUID.randomUUID().toString());
            paquete.setNombre(nombre);
            paquete.setPrecio(precio);
            paquete.setDescripcion(descripcion);
            paquete.setProductos(productos);

            if (daoPaquete.agregarPaquete(paquete)) {
                for (Producto producto : productos) {
                    Producto_Paquete productoPaquete = new Producto_Paquete();
                    productoPaquete.setIdProductoPaquete(UUID.randomUUID().toString());
                    productoPaquete.setIdPaquete(paquete.getIdPaquete());
                    productoPaquete.setIdProducto(producto.getIdProducto());

                    if (!daoProducto_Paquete.agregarProductoPaquete(productoPaquete)) {
                        return "Error al agregar producto al paquete";
                    }
                }
                return "Paquete y productos agregados exitosamente";
            } else {
                return "Error al agregar el paquete";
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.status(500);
            return "Error interno del servidor";
        }
    }

    public static String obtenerProductosEnPaquete(Request req, Response res) {
        String idPaquete = req.queryParams("idPaquete");
        if (idPaquete == null || idPaquete.isEmpty()) {
            res.status(400);
            return "idPaquete es requerido";
        }
        List<JsonObject> productosEnPaquete = daoProducto_Paquete.obtenerListaProductos(idPaquete);
        if (productosEnPaquete.isEmpty()) {
            res.status(404);
            return "No se encontraron productos en el paquete especificado";
        } else {
            return gson.toJson(productosEnPaquete);
        }
    }

    public static String obtenerPaquetesConProductos(Request req, Response res) {
        return gson.toJson(daoProducto_Paquete.obtenerPaquetesConProductos());

    }
}
