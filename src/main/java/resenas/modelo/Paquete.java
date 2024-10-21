package resenas.modelo;

public class Paquete {
    String idPaquete;
    int precio;
    String descripcion;
    
    public Paquete() {
    }

    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Paquete [idPaquete=" + idPaquete + ", precio=" + precio + ", descripcion=" + descripcion + "]";
    }

}
