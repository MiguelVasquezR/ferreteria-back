package resenas.modelo;

public class Rol {

    private String id;
    private String nombre;

    public Rol() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rol [id=" + id + ", nombre=" + nombre + "]";
    }

}
