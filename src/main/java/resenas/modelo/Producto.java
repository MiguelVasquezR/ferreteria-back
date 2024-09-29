package resenas.modelo;

public class Producto {
    private String idProducto;
    private String codigo;
    private String nombre;
    private float cantidad;
    private float stockMinimo;
    private float costo;
    private float precioMenudeo;
    private float precioMayoreo;
    private String urlImage;

    public Producto(String idProducto, String codigo, String nombre, float cantidad, float stockMinimo, float costo,
            float precioMenudeo, float precioMayoreo, String urlImage) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.stockMinimo = stockMinimo;
        this.costo = costo;
        this.precioMenudeo = precioMenudeo;
        this.precioMayoreo = precioMayoreo;
        this.urlImage = urlImage;
    }

    public Producto() {
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdPrducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(float stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getPrecioMenudeo() {
        return precioMenudeo;
    }

    public void setPrecioMenudeo(float precioMenudeo) {
        this.precioMenudeo = precioMenudeo;
    }

    public float getPrecioMayoreo() {
        return precioMayoreo;
    }

    public void setPrecioMayoreo(float precioMayoreo) {
        this.precioMayoreo = precioMayoreo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", codigo=" + codigo + ", nombre=" + nombre + ", cantidad="
                + cantidad + ", stockMinimo=" + stockMinimo + ", costo=" + costo + ", precioMenudeo=" + precioMenudeo
                + ", precioMayoreo=" + precioMayoreo + ", urlImage=" + urlImage + "]";
    }

}
