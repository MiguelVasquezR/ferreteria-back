package resenas.modelo;

import java.sql.Date;

public class Oferta {
    private String idOferta;
    private String idProducto;
    private Date fechaInicio;
    private Date fechaFinal;
    private String detalles;
    private String estado;
    
    public Oferta() {
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(String idOferta) {
        this.idOferta = idOferta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Oferta [idOferta=" + idOferta + ", idProducto=" + idProducto + ", fechaInicio=" + fechaInicio
                + ", fechaFinal=" + fechaFinal + ", detalles=" + detalles + "]";
    }
}
