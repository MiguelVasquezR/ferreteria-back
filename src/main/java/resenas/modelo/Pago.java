package resenas.modelo;

import java.sql.Date;

public class Pago {
    private String idPago;
    private String idUsuario;
    private String tipo;
    private float monto;
    private Date fecha;
    private String estado;

    public Pago() {

    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pago [idPago=" + idPago + ", idUsuario=" + idUsuario + ", tipo=" + tipo + ", monto=" + monto
                + ", fecha=" + fecha + ", estado=" + estado + "]";
    }
    

}
