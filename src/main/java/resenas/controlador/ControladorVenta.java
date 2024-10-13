package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;

import resenas.dao.DAOUsuario;
import resenas.dao.DAOVenta;
import resenas.modelo.Direccion;
import resenas.modelo.Persona;
import resenas.modelo.Usuario;
import resenas.modelo.Venta;
import spark.Request;
import spark.Response;

public class ControladorVenta {
    private static DAOVenta daoVenta = new DAOVenta();
    private static Gson gson = new Gson();
    private static DAOUsuario daoUsuario = new DAOUsuario();
    public static String guardarVenta(Request req, Response res){
        Venta venta = gson.fromJson(req.body(), Venta.class);
        venta.setIdVenta(UUID.randomUUID().toString());
        System.out.println(venta.toString());
        if (daoVenta.guardarVenta(venta)) {
            return "Producto agregado correctamente";
        } else {
            return "Error al agregar producto";
        }

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
        if (usuario.getId() == null || usuario.getUser() == null) {
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

