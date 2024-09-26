package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAODireccion;
import resenas.dao.DAOPersona;
import resenas.modelo.Direccion;
import resenas.modelo.Persona;
import spark.Request;
import spark.Response;

public class ControladorProveedor {
    private static Gson gson = new Gson();
    private static DAOPersona daoPersona = new DAOPersona();
    private static DAODireccion daoDireccion = new DAODireccion();

    public static JsonObject registrarProveedor(Request req, Response res) {
        Direccion direccion = gson.fromJson(req.body(), Direccion.class);
        direccion.setId(UUID.randomUUID().toString());
        JsonObject respuesta = new JsonObject();

        if (daoDireccion.agregarDireccion(direccion)) {
            Persona persona = gson.fromJson(req.body(), Persona.class);
            persona.setId(UUID.randomUUID().toString());
            persona.setId_direccion(direccion.getId());
            if (daoPersona.agregarPersona(persona)) {
                respuesta.addProperty("message", "Proveedor registrado correctamente");
                res.status(201);
                return respuesta;
            } else {
                respuesta.addProperty("message", "No se pudo registrar el proveedor");
                res.status(400);
                return respuesta;
            }
        }else{
            respuesta.addProperty("message", "No se pudo registrar la direccion");
            res.status(400);
            return respuesta;
        }
    }

    public static String actualizarProveedor(Request req, Response res) {

        return "";

    }

}
