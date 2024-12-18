package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAODireccion;
import resenas.dao.DAOPersona;
import resenas.dao.DAOProveedor;
import resenas.modelo.Direccion;
import resenas.modelo.Persona;
import resenas.utils.Utils;
import spark.Request;
import spark.Response;

public class ControladorProveedor {
    private static Gson gson = new Gson();
    private static DAOPersona daoPersona = new DAOPersona();
    private static DAODireccion daoDireccion = new DAODireccion();
    private static DAOProveedor daoProveedor = new DAOProveedor();

    public static JsonObject registrarProveedor(Request req, Response res) {
        Direccion direccion = gson.fromJson(req.body(), Direccion.class);
        direccion.setId(UUID.randomUUID().toString());
        JsonObject respuesta = new JsonObject();

        if (daoDireccion.agregarDireccion(direccion)) {
            Persona persona = gson.fromJson(req.body(), Persona.class);
            persona.setId(UUID.randomUUID().toString());
            persona.setId_direccion(direccion.getId());
            persona.setEstado("Activo");
            persona.setIdRol(Utils.MYSQL_PROVEEDOR);
            try{
                if (daoPersona.agregarPersona(persona)) {
                    respuesta.addProperty("message", "Proveedor registrado correctamente");
                    respuesta.addProperty("status", 200);
                    return respuesta;
                } else {
                    respuesta.addProperty("message", "El proveedor ya se encuentra en existencia");
                    respuesta.addProperty("status", 400);
                    return respuesta;
                }
            }catch(Exception e){
                 e.printStackTrace();
                res.status(500); // Código HTTP 500: Internal Server Error
                respuesta.addProperty("message", "Fallo al agregar al proveedor");
                respuesta.addProperty("status", 200);
            }
        } else {
            respuesta.addProperty("message", "No se pudo registrar la direccion");
            respuesta.addProperty("status", 400);
            return respuesta;
        }
        return respuesta;
    }

    public static JsonObject actualizarProveedor(Request req, Response res) {
        String idPersona = req.queryParams("id");
        Persona persona = gson.fromJson(req.body(), Persona.class);
        Direccion direccion = gson.fromJson(req.body(), Direccion.class);
        Persona personaBd = daoPersona.obtenerPersonaById(idPersona);
        JsonObject respuesta = new JsonObject();
        persona.setId(idPersona);
        if (daoPersona.editarProveedor(persona)) {
            direccion.setId(personaBd.getId_direccion());
            if (daoDireccion.editarDireccion(direccion)) {
                respuesta.addProperty("message", "Proveedor actualizado correctamente");
                respuesta.addProperty("status", 200);
                return respuesta;
            } else {
                respuesta.addProperty("message", "No se pudo actualizar la direccion");
                respuesta.addProperty("status", 400);
                return respuesta;
            }
        }
        respuesta.addProperty("message", "No se pudo actualizar el proveedor");
        respuesta.addProperty("status", 400);
        return respuesta;

    }

    public static String obtenerProveedores(Request req, Response res) {
        return gson.toJson(daoProveedor.obtenerProveedores());
    }

    public static JsonObject eliminarProveedor(Request req, Response res) {
        String idPersona = req.queryParams("id");
        JsonObject respuesta = new JsonObject();
        if (daoPersona.eliminarPersona(idPersona)) {
            respuesta.addProperty("message", "Proveedor eliminado correctamente");
            respuesta.addProperty("status", 200);
        } else {
            respuesta.addProperty("message", "No se pudo eliminar el proveedor");
            respuesta.addProperty("status", 400);
        }

        return respuesta;
    }

}
