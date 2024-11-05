package resenas.controlador;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import resenas.dao.DAOUsuario;
import resenas.dao.DAOVenta;
import resenas.modelo.Direccion;
import resenas.modelo.Persona;
import resenas.modelo.Usuario;
import resenas.modelo.Producto_Venta;
import resenas.modelo.Venta;
import resenas.utils.JwtUtils;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.text.ParseException;

public class ControladorVenta {
    private static DAOVenta daoVenta = new DAOVenta();
    private static Gson gson = new Gson();
    private static DAOUsuario daoUsuario = new DAOUsuario();
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

    public static String guardarVenta(Request req, Response res) {
        System.out.println(req.body());
        String token = req.headers("Authorization").replace("Bearer ", "");
        String idUsuario = JwtUtils.obtenerIdUsuaraio(token);
        Venta venta = new Venta();
        venta.setIdVenta(UUID.randomUUID().toString());
        venta.setIdUsuario(idUsuario);
        venta.setCantidad(0);
        venta.setFecha(java.sql.Date.valueOf(java.time.LocalDate.now()));
        Type productoListType = new TypeToken<JsonObject>() {
        }.getType();
        JsonObject productosVenta = gson.fromJson(req.body(), productoListType);
        venta.setMonto(productosVenta.get("total").getAsFloat());
        if (!daoVenta.guardarVenta(venta)) {
            return "Error al guardar la venta";
        }
        for (JsonElement productoElement : productosVenta.get("productos").getAsJsonArray()) {
            JsonObject producto = productoElement.getAsJsonObject();
            String cantidadCompra = producto.get("cantidadCompra").getAsString();
            float cantidad = Float.parseFloat(cantidadCompra);

            venta.setCantidad(venta.getCantidad() + cantidad);

            Producto_Venta productoVenta = new Producto_Venta();
            productoVenta.setIdProductoVenta(UUID.randomUUID().toString());
            productoVenta.setIdProducto(producto.get("idProducto").getAsString());
            productoVenta.setIdVenta(venta.getIdVenta());
            productoVenta.setCantidad(cantidad);

            if (!daoVenta.guardarProductoVenta(productoVenta)) {
                return "No se pudo procesar la compra";
            }
        }

        if (!daoVenta.actualizarVenta(venta)) {
            return "Error al actualizar la cantidad de la venta";
        }

        return "Venta guardada exitosamente";
    }

    public static String actualizarVenta(Request req, Response res) {
         // Extraer el cuerpo de la solicitud como un String
    String body = req.body();

    // Parsear el JSON para obtener el idVenta (puedes usar una biblioteca como Gson)
    String id = req.queryParams("idVenta");
    Gson gson = new Gson();
    Venta venta = gson.fromJson(body, Venta.class);

    String idVenta = venta.getIdVenta(); // Asegúrate de tener un método getIdVenta()

    if (idVenta == null) {
        res.status(400); // Bad Request
        return "ID de venta no proporcionado"; // Mensaje de error
    }

    System.out.println("Buscando venta con ID: " + idVenta);


      //  Venta venta = gson.fromJson(req.body(), Venta.class);
        Usuario usuario = gson.fromJson(req.body(), Usuario.class);
        Venta ventaBd = daoVenta.obtenerVentaById(idVenta);
    
        if (ventaBd == null) {
            return "Venta no encontrada";
        }
    
        System.out.println("Venta BD: " + ventaBd.toString());
        System.out.println("Usuario desde la solicitud: " + usuario);
    
        // Asegúrate de que estos valores no son nulos
        if (usuario.getId() == null || usuario.getUsuario() == null) {
            throw new IllegalArgumentException("Los campos idUsuario y usuario no pueden ser nulos.");
        }
    
        venta.setIdVenta(idVenta);
    
        if (daoVenta.editarVenta(venta)) {
            usuario.setId(ventaBd.getIdUsuario()); // Asegúrate de que este ID es válido
    
            if (daoUsuario.editarUsuario(usuario)) {
                return "Venta y usuarios actualizados";
            } else {
                return "Error al actualizar usuario"; 
            }
        } else {
            return "Error al actualizar venta";
        }
    }

    public static String aV(Request req, Response res) {
        // Obtener el ID de la venta desde la URL
        String idVenta = req.queryParams("idVenta");
        System.out.println("ID de la venta recibido: " + idVenta);
    
        // Verifica si el ID de la venta es nulo o vacío
        if (idVenta == null || idVenta.isEmpty()) {
            return "Error: ID de venta no proporcionado";
        }
    
        // Parsear la venta desde el cuerpo de la solicitud
        Venta venta = gson.fromJson(req.body(), Venta.class);
        Venta ventaBd = daoVenta.obtenerVentaById(idVenta);
        
        // Parsear el usuario desde el cuerpo de la solicitud
        Usuario usuario = gson.fromJson(req.body(), Usuario.class);
    
        // Verificar si la venta se obtuvo correctamente
        if (ventaBd == null) {
            return "Error: No se encontró ninguna venta con el ID proporcionado.";
        }
    
        System.out.println("Venta encontrada: " + ventaBd.toString());
    
        // Actualizar la venta en la base de datos
        if (daoVenta.eV(venta)) {
            usuario.setId(ventaBd.getIdUsuario());
            return "Venta actualizada correctamente";
        } else {
            return "Error al actualizar venta";
        }
    }
    
    

}

