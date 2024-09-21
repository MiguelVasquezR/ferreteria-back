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
        producto.setId(UUID.randomUUID().toString());
        producto.setCodigo(producto.getId().replace("-", ""));
        System.out.println(producto.getCodigo());
        if (daoProducto.agregarProducto(producto)) {
            return "Producto agregado correctamente";
        } else {
            return "Error al agregar producto";

        }
    }

}
