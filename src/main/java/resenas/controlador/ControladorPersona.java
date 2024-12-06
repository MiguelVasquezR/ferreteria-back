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
        try{
            Persona persona = gson.fromJson(req.body(), Persona.class);
            persona.setId(UUID.randomUUID().toString());
            if (daoPersona.agregarPersona(persona)) {
                return "Persona agregada correctamente";
            } else {
                return "La persona ya existe en la base de datos";
            }
        }catch(Exception e){
             e.printStackTrace();
            res.status(500); // Código HTTP 500: Internal Server Error
            return "Ocurrió un error interno en el servidor";
        }
    }
}
