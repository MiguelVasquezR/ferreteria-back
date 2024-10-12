package resenas.modelo;

public class Reporte {
    private String idReporte;
    private String idProducto;
    private String urlImage;
    private String descripcion;
    
    public Reporte() {
    }

    public String getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
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
        return "Reporte [idReporte=" + idReporte + ", idProducto=" + idProducto + ", urlImage=" + urlImage
                + ", descripcion=" + descripcion + "]";
    }

    
}
