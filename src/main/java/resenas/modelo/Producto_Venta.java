package resenas.modelo;

public class Producto_Venta {
    private String idProductoVenta;
    private String idProducto;
    private String idVenta;
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

    public String getIdProductoVenta() {
        return idProductoVenta;
    }

    public void setIdProductoVenta(String idProductoVenta) {
        this.idProductoVenta = idProductoVenta;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }


    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }



}
