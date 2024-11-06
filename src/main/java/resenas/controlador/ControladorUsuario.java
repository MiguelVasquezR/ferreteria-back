package resenas.controlador;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.dao.DAOUsuario;
import resenas.modelo.Usuario;
import resenas.utils.Encriptar;
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
    
    public static String agregarUsuario(Request req, Response res){
        Usuario usuario = gson.fromJson(req.body(), Usuario.class);
        usuario.setId(UUID.randomUUID().toString());
        String hashedPassword = Encriptar.encriptar(usuario.getPassword());
        usuario.setPassword(hashedPassword);

        if (!daoUsuario.personaExiste(usuario.getIdPersona())) {
            res.status(400);
            return "El idPersona no existe en la base de datos";
        }
        if (daoUsuario.agregarUsuario(usuario)) {
            return "Usuario agregado exitosamente";
        } else {
            res.status(500);
            return "Error al agregar usuario";
        }
    }

    public static String editarUsuario(Request req, Response res){
        Usuario usuario = gson.fromJson(req.body(), Usuario.class);
        String hashedPassword = Encriptar.encriptar(usuario.getPassword());
        usuario.setPassword(hashedPassword);
        if (daoUsuario.editarUsuario(usuario)) {
            return "Usuario editado exitosamente";
        } else {
            return "No se guardaron los cambios del usuario";
        }
    }

    public static String eliminarUsuario(Request req, Response res){
        String idUsuario = req.queryParams("idUsuario");
        if (daoUsuario.eliminarUsuario(idUsuario)) {
            return "Usuario eliminado";
        } else {
            return "No se logro eliminar el usuario";
        }
    }

}
