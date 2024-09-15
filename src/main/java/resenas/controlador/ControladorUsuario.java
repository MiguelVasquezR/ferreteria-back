package resenas.controlador;

import resenas.dao.DAOUsuario;
import resenas.modelo.Usuario;

public class ControladorUsuario {

    private static DAOUsuario daoUsuario = new DAOUsuario();

    public static Usuario iniciarSesion(String usuario, String contrasena) {
        return daoUsuario.validarCredenciales(usuario, contrasena);
    }

}
