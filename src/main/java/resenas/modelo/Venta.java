package resenas.modelo;

import java.sql.Date;

public class Venta {
    private String idVenta;
    private String idUsuario;
    private float cantidad;
    private float monto;
    private Date fecha;

    public Venta() {

    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Venta [idVenta=" + idVenta + ", idUsuario=" + idUsuario + ", cantidad=" + cantidad + ", monto=" + monto
                + ", fecha=" + fecha + "]";
    }

}
