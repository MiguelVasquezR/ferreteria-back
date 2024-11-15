package resenas.modelo;

public class ReporteDiario {
    private String idReporte;
    private String nombre;
    private String urlImage;
    private String descripcion;
    private float cantidad;
    private float precioMenudeo;
    private String idPersona;
    private String idProducto;

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioMenudeo() {
        return precioMenudeo;
    }

    public void setPrecioMenudeo(float precioMenudeo) {
        this.precioMenudeo = precioMenudeo;
    }

    public ReporteDiario() {
    }

    public String getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ReporteDiario [idReporte=" + idReporte + ", nombre=" + nombre + ", urlImage=" + urlImage
                + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precioMenudeo=" + precioMenudeo
                + ", idPersona=" + idPersona + ", idProducto=" + idProducto + "]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

}