package resenas.modelo;

public class Persona {
    private String id;
    private String id_direccion;
    private String nombre;
    private String telefono;
    private String correo;
    private String rfc;
    private String idRol;

    public Persona() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(String id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", id_direccion=" + id_direccion + ", nombre=" + nombre + ", telefono=" + telefono
                + ", correo=" + correo + ", rfc=" + rfc + ", idRol=" + idRol + "]";
    }

    

}