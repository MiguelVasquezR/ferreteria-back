package resenas.controlador;

import com.google.gson.JsonObject;

import resenas.dao.DAOUsuario;

public class ControladorUsuario {

    private static DAOUsuario daoUsuario = new DAOUsuario();

    public static JsonObject iniciarSesion(String usuario, String contrasena) {
        return daoUsuario.validarCredenciales(usuario, contrasena);
    }

    public static String cambiarContrasena(String correo, String nuevaContrasena) {
        return daoUsuario.cambiarContrasena(correo, nuevaContrasena);
    }

}
