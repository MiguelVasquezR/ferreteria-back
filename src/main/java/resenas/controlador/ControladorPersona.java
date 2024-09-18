package resenas.controlador;

import java.util.UUID;

import com.google.gson.Gson;
import resenas.dao.DAOPersona;
import resenas.modelo.Persona;
import spark.Request;
import spark.Response;

public class ControladorPersona {
    private static DAOPersona daoPersona = new DAOPersona();
    private static Gson gson = new Gson();

    public static String crearPersona(Request req, Response res){
        Persona persona = gson.fromJson(req.body(), Persona.class);
        persona.setId("12345");
        if (daoPersona.agregarPersona(persona)) {
            return "Persona agregada correctamente";
        } else {
            return "Error al agregar la persona";
        }
    }
}
