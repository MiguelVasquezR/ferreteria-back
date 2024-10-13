package resenas.modelo;

public class Producto_Venta {
    private String idProductoVenta;
    private String idProducto;
    private String idVenta;
    private float cantidad;

    public Producto_Venta() {
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
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
