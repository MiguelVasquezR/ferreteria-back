package resenas.modelo;

public class Usuario {

    private String id;
    private String id_persona;
    private String user;
    private String password;
    private String correo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdPersona() {
        return id_persona;
    }

    public void setIdPersona(String id_persona) {
        this.id_persona = id_persona;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", user=" + user + ", password=" + password + ", correo=" + correo + ", rol=" + rol
                + "]";
    }

    

}
