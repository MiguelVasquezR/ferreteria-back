package resenas.modelo;

public class Usuario {

    private String idUsuario;
    private String idPersona;
    private String usuario;
    private String contrasena;
    private double sueldo;
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

    @Override
    public String toString() {
        return "Usuario [id=" + idUsuario + ", ususario=" + usuario + ", password=" + contrasena + "]";
    }
    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
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

    

}
