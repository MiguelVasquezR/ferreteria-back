package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import resenas.dao.DAOProducto;
import resenas.modelo.Producto;
import spark.Request;
import spark.Response;

public class ControladorProducto {
    private static DAOProducto daoProducto = new DAOProducto();
    private static Gson gson = new Gson();

    public static String obtenerProductosPocoStock(Request req, Response res) {
        return gson.toJson(daoProducto.obtenerProductosPocoStock());
    }

    public static String actualizarStock(Request req, Response res) {
        String codigo = req.queryParams("codigo");
        float stock = Float.parseFloat(req.queryParams("stockMinimo"));
        if (daoProducto.actualizarStock(codigo, stock)) {
            return "Stock actualizado correctamente";
        } else {
            return "Error al actualizar stock";
        }
    }

    public static String obtenerStock(Request req, Response res) {
        String codigo = req.queryParams("codigo");
        float stock = daoProducto.obtenerStock(codigo);
        if (stock != 0) {
            return gson.toJson(stock);
        } else {
            return "Producto no encontrado";
        }
    }

    public static JsonObject crearProducto(Request req, Response res) {
        Producto producto = gson.fromJson(req.body(), Producto.class);
        producto.setIdPrducto(UUID.randomUUID().toString());
        producto.setCodigo(producto.getIdProducto().replace("-", ""));
        producto.setIdPersona(producto.getIdPersona());
        JsonObject respuesta = new JsonObject();

        try {
            if (daoProducto.agregarProducto(producto)) {
                respuesta.addProperty("message", "Producto registrado correctamente");
                respuesta.addProperty("status", 200);
                return respuesta;
            } else {
                respuesta.addProperty("message", "El producto ya se encuentra en existencia");
                respuesta.addProperty("status", 400);
                return respuesta;
            } 
        } catch (Exception e) {
            e.printStackTrace();
            respuesta.addProperty("message", "Error al agregar un producto");
            respuesta.addProperty("status", 500);
            return respuesta;
        }
    }

    public static String obtenerProducto(Request req, Response res) {
        String idProducto = req.queryParams("idProducto");
        JsonObject producto = daoProducto.obtenerProductoByID(idProducto);
        if (producto != null) {
            return gson.toJson(producto);
        } else {
            return "Producto no encontrado";
        }
    }

    public static String obtenerProductos(Request req, Response res) {
        return gson.toJson(daoProducto.getProducts());
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

    public static String personalizarStockMinimo(Request req, Response res) {
        Producto producto = gson.fromJson(req.body(), Producto.class);
        String idProducto = req.queryParams("idProducto");
        if (daoProducto.personalizarStockMinimo(producto, idProducto)) {
            return "Stock minimo modificado exitosamente";
        } else {
            return "Error al configurar stock minimo";
        }
    }

}
