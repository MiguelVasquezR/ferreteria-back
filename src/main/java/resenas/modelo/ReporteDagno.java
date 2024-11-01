package resenas.modelo;

public class ReporteDagno {
    private String idReporte;
    private String nombre;
    private String urlImage;
    private String descripcion;
    
    public ReporteDagno() {
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
        return "Reporte [idReporte=" + idReporte + ", nombre=" + nombre + ", urlImage=" + urlImage
                + ", descripcion=" + descripcion + "]";
    }

    
}
