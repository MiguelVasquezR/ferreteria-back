package resenas.controlador;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAOUsuario;
import spark.Request;
import spark.Response;

public class ControladorUsuario {

    private static DAOUsuario daoUsuario = new DAOUsuario();
    private static Gson gson = new Gson();

    public static JsonObject iniciarSesion(String usuario, String contrasena) {
        return daoUsuario.validarCredenciales(usuario, contrasena);
    }

    public static String cambiarContrasena(String correo, String nuevaContrasena) {
        return daoUsuario.cambiarContrasena(correo, nuevaContrasena);
    }

    public static String obtenerSueldoMasComision(Request req, Response res){
        List<JsonObject> sueldosComisiones = daoUsuario.obtenerSuledoMasComision();
    if (sueldosComisiones != null && !sueldosComisiones.isEmpty()) {
        res.status(200);
        return gson.toJson(sueldosComisiones);
    } else {
        res.status(404);
        return "No se encontraron vendedores con sueldos y comisiones.";
    }
    }

}
