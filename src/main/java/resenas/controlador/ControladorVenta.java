package resenas.controlador;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.google.gson.Gson;

import resenas.dao.DAOVenta;
import resenas.modelo.Venta;
import spark.Request;
import spark.Response;
import java.sql.SQLException;
import java.text.ParseException;



public class ControladorVenta {
    private static DAOVenta daoVenta = new DAOVenta();
    private static Gson gson = new Gson();

    public static String guardarVenta(Request req, Response res){
        Venta venta = gson.fromJson(req.body(), Venta.class);
        venta.setIdVenta(UUID.randomUUID().toString());
        System.out.println(venta.toString());
        if (daoVenta.guardarVenta(venta)) {
            return "Venta guardada exitosamente";
        } else {
            return "Error al agregar producto";
        }

    }


    public static String ventaDiaria(Request req, Response res) {
        // Obtener la fecha como String desde los parámetros
        String fechaString = req.queryParams("fecha");
    
        // Formato de fecha esperado (yyyy-MM-dd, por ejemplo: "2024-10-07")
        SimpleDateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd");
    
        Date fechaUtil = null;
        try {
            // Convertir el String en un objeto Date de tipo java.util.Date
            fechaUtil = fechaFormato.parse(fechaString);
        } catch (ParseException e) {
            res.status(400); // Bad Request
            return "Formato de fecha incorrecto. Usa yyyy-MM-dd, por ejemplo: 2024-10-07";
        }
    
        // Convertir java.util.Date a java.sql.Date para usarlo con la base de datos
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        System.out.println("Fecha enviada a la consulta: " + fechaSql);

    
        // Verificar si existen ventas para la fecha especificada
        if (daoVenta.ventaDiaria(fechaSql) != null) {
            return gson.toJson(daoVenta.ventaDiaria(fechaSql));

        } else {
            return "No se han agregado productos para la fecha especificada.";
        }
    }
    
}
