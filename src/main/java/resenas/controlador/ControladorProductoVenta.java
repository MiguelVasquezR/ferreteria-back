package resenas.controlador;

import com.google.gson.Gson;

import resenas.dao.DAOProducto_Venta;
import spark.Request;
import spark.Response;

/**
 * ControladorProductoVenta
 */
public class ControladorProductoVenta {
    private static DAOProducto_Venta daoProducto_Venta = new DAOProducto_Venta();
    private static Gson gson = new Gson();

    public static String listaMasVendida(Request req, Response res) {
        if (daoProducto_Venta.masVendidos() != null) {
            return gson.toJson(daoProducto_Venta.masVendidos());
        } else {
            return "No hay producto mas vendido";
        }
    }


    public static String listaMenosVendido(Request req, Response res) {
        if (daoProducto_Venta.menosVendidos() != null) {
            return gson.toJson(daoProducto_Venta.menosVendidos());
        } else {
            return "No hay producto mas vendido";
        }
    }


}