package resenas.modelo;

public class Usuario {

    private String idUsuario;
    private String idPersona;
    private String usuario;
    private String contrasena;
    private String sueldo;
    private String estado;

    public String getId() {
        return idUsuario;
    }

    public void setId(String id) {
        this.idUsuario = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUser(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return contrasena;
    }

    public void setPassword(String password) {
        this.contrasena = password;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = sueldo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", idPersona=" + idPersona + ", usuario=" + usuario + ", contrasena="
                + contrasena + ", sueldo=" + sueldo + ", estado=" + estado + "]";
    }

    

}
