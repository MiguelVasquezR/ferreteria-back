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
    private String estado;
    private String descripcion;
    private String idProveedor;

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

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ", codigo=" + codigo + ", nombre=" + nombre + ", cantidad="
                + cantidad + ", stockMinimo=" + stockMinimo + ", costo=" + costo + ", precioMenudeo=" + precioMenudeo
                + ", precioMayoreo=" + precioMayoreo + ", urlImage=" + urlImage + ", estado=" + estado
                + ", descripcion=" + descripcion + "]";
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

}
