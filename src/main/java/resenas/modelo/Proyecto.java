package resenas.modelo;

import java.sql.Date;

public class Proyecto {

    private String idProyecto;
    private String idPersona;
    private String idDireccion;
    private Date fecha;
    private String descripcion;

    public Proyecto(String idProyecto, String idPersona, String idDireccion, Date fecha, String descripcion) {
        this.idProyecto = idProyecto;
        this.idPersona = idPersona;
        this.idDireccion = idDireccion;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Proyecto() {
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + ", idPersona=" + idPersona + ", idDireccion=" + idDireccion
                + ", fecha=" + fecha + ", descripcion=" + descripcion + "]";
    }

}
