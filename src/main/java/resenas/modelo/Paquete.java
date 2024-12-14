package resenas.modelo;

import java.util.List;

public class Paquete {

    private String idPaquete;
    private String nombre;
    private float precio;
    private String descripcion;
    private String estado;
    private List<Producto> productos;

    public Paquete() {
    }

    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Paquete [idPaquete=" + idPaquete + ", nombre=" + nombre + ", precio=" + precio + ", descripcion="
                + descripcion + ", estado=" + estado + ", productos=" + productos + "]";
    }

}
