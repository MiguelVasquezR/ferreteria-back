package resenas.controlador;

import java.sql.Date;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import resenas.dao.DAODireccion;
import resenas.dao.DAOPersona;
import resenas.dao.DAOProyecto;
import resenas.modelo.Direccion;
import resenas.modelo.Paquete;
import resenas.modelo.Persona;
import resenas.modelo.Producto;
import resenas.modelo.Proyecto;
import resenas.utils.Utils;
import spark.Request;
import spark.Response;

public class ControladorObra {

    private static Gson gson = new Gson();
    private static DAODireccion daoDireccion = new DAODireccion();
    private static DAOProyecto daoProyecto = new DAOProyecto();
    private static DAOPersona daoPersona = new DAOPersona();

    public static JsonObject guardarObra(Request req, Response res) {
        JsonObject jsonObject = JsonParser.parseString(req.body()).getAsJsonObject();
        JsonObject direccionProyectoJson = new JsonObject();
        direccionProyectoJson.addProperty("calle", jsonObject.get("calleP").getAsString());
        direccionProyectoJson.addProperty("numero", jsonObject.get("numeroP").getAsString());
        direccionProyectoJson.addProperty("colonia", jsonObject.get("coloniaP").getAsString());
        direccionProyectoJson.addProperty("ciudad", jsonObject.get("ciudadP").getAsString());

        Direccion direccionProyecto = gson.fromJson(direccionProyectoJson, Direccion.class);
        direccionProyecto.setId(UUID.randomUUID().toString());

        Direccion direccionPersona = gson.fromJson(req.body(), Direccion.class);
        direccionPersona.setId(UUID.randomUUID().toString());

        Persona persona = gson.fromJson(req.body(), Persona.class);
        persona.setId(UUID.randomUUID().toString());
        persona.setId_direccion(direccionPersona.getId());
        persona.setEstado("Activo");
        persona.setIdRol(Utils.MYSQL_ENCARGADO_OBRA);

        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(UUID.randomUUID().toString());
        proyecto.setIdPersona(persona.getId());
        proyecto.setIdDireccion(direccionProyecto.getId());
        proyecto.setFecha(new Date(System.currentTimeMillis()));
        proyecto.setDescripcion(jsonObject.get("descripcion").getAsString());

        JsonObject mensaje = new JsonObject();
        if (daoDireccion.agregarDireccion(direccionPersona)) {
            if (daoPersona.agregarPersona(persona)) {
                if (daoDireccion.agregarDireccion(direccionProyecto)) {
                    if (daoProyecto.agregarProyecto(proyecto)) {
                        mensaje.addProperty("mensaje", "Obra guardada correctamente");
                        mensaje.addProperty("status", 200);
                    } else {
                        mensaje.addProperty("mensaje", "Obra no guardada correctamente");
                        mensaje.addProperty("status", 400);
                    }
                } else {
                    mensaje.addProperty("mensaje", "Obra no guardada correctamente");
                    mensaje.addProperty("status", 400);
                }
            } else {

            }
        } else {
            mensaje.addProperty("mensaje", "Obra no guardada correctamente");
            mensaje.addProperty("status", 400);
        }

        return mensaje;
    }

    public static String obtenerObras(Request req, Response res) {
        return gson.toJson(daoProyecto.obtenerProyectos());
    }

    public static JsonObject eliminarObra(Request req, Response res) {
        String id = req.queryParams("id").replace("'", "");
        JsonObject mensaje = new JsonObject();
        if (daoProyecto.eliminarProyecto(id)) {
            mensaje.addProperty("mensaje", "Obra eliminada correctamente");
            mensaje.addProperty("status", 200);
        } else {
            mensaje.addProperty("mensaje", "Obra no eliminada correctamente");
            mensaje.addProperty("status", 400);
        }
        return mensaje;
    }

    public static String obtenerById(Request req, Response res) {
        String idProyecto = req.queryParams("idProyecto");
        JsonObject proyecto = daoProyecto.obtenById(idProyecto);
        if (proyecto != null) {
            return gson.toJson(proyecto);

        } else {
            return "Paquete no existente";

        }
    }


    public static JsonObject editarProyecto(Request req, Response res) {
        JsonObject jsonObject = JsonParser.parseString(req.body()).getAsJsonObject();
        JsonObject direccionProyectoJson = new JsonObject();
        direccionProyectoJson.addProperty("calle", jsonObject.get("calleP").getAsString());
        direccionProyectoJson.addProperty("numero", jsonObject.get("numeroP").getAsString());
        direccionProyectoJson.addProperty("colonia", jsonObject.get("coloniaP").getAsString());
        direccionProyectoJson.addProperty("ciudad", jsonObject.get("ciudadP").getAsString());

        Direccion direccionProyecto = gson.fromJson(direccionProyectoJson, Direccion.class);
        Direccion direccionPersona = gson.fromJson(req.body(), Direccion.class);
        Persona persona = gson.fromJson(req.body(), Persona.class);
        Proyecto proyecto = new Proyecto();

        JsonObject mensaje = new JsonObject();
        if (daoDireccion.editarDireccion(direccionPersona)) {
            if (daoPersona.editarPersona(persona)) {
                if (daoDireccion.editarDireccion(direccionProyecto)) {
                    if (daoProyecto.editarProyecto(proyecto)) {
                        mensaje.addProperty("mensaje", "Obra editada correctamente");
                        mensaje.addProperty("status", 200);
                    } else {
                        mensaje.addProperty("mensaje", "Obra no editada correctamente");
                        mensaje.addProperty("status", 400);
                    }
                } else {
                    mensaje.addProperty("mensaje", "Obra no editada correctamente");
                    mensaje.addProperty("status", 400);
                }
            } else {

            }


}
return mensaje;
    }
}