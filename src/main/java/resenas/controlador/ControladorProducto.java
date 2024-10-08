package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import resenas.dao.DAOProducto;
import resenas.modelo.Producto;
import spark.Request;
import spark.Response;

public class ControladorProducto {
    private static DAOProducto daoProducto = new DAOProducto();
    private static Gson gson = new Gson();

    public static String crearProducto(Request req, Response res) {
        Producto producto = gson.fromJson(req.body(), Producto.class);
        producto.setIdPrducto(UUID.randomUUID().toString());
        producto.setCodigo(producto.getIdProducto().replace("-", ""));
        System.out.println(producto.toString());
        if (daoProducto.agregarProducto(producto)) {
            return "Producto agregado correctamente";
        } else {
            return "Error al agregar producto";
        }
    }

    public static String obtenerProducto(Request req, Response res) {
        String idProducto = req.queryParams("id");
        Producto producto = daoProducto.obtenerProductoByID(idProducto);
        if (producto != null) {
            return gson.toJson(producto);
        } else {
            return "Producto no encontrado";
        }
    }

    public static String obtenerProductos(Request req, Response res) {
        if (daoProducto.getProducts() != null) {
            return gson.toJson(daoProducto.getProducts());
        } else {
            return "No has agregado productos";
        }
    }

    public static String eliminarProducto(Request req, Response res) {
        String idProducto = req.queryParams("id");
        if (daoProducto.eliminarProducto(idProducto)) {
            return "Producto eliminado exitosamente";

        } else {
            return "Producto no existente";
        }
    }

    public static String editarProducto(Request req, Response res) {
        Producto producto = gson.fromJson(req.body(), Producto.class);
        System.out.println(producto.toString());
        if (daoProducto.editarProducto(producto)) {
            return "Datos del producto guardados exitosamente";
        } else {
            return "No se guardaron los datos del producto";
        }
    }

}
