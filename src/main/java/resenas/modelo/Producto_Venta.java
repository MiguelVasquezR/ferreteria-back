package resenas.modelo;

public class Producto_Venta {
    private float precioMenudeo;
    private String nombre;
    private String urlImage;
    private float cantidad;


    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }


    public float getPrecioMenudeo() {
        return precioMenudeo;
    }

    public void setPrecioMenudeo(float precioMenudeo) {
        this.precioMenudeo = precioMenudeo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Producto_Venta() {
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto_Venta [idProductoVenta=" + idProductoVenta + ", idProducto=" + idProducto + ", idVenta="
                + idVenta
                + ", cantidad=" + cantidad + "]";
    }
}
