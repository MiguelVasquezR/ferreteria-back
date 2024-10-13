package resenas.modelo;

import java.sql.Date;

public class Venta {
   

    private String idVenta;
    private String idUsuario;
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
  

    @Override
    public String toString() {
        return "Venta [idVenta=" + idVenta + ", idUsuario=" + idUsuario + ", monto=" + monto + ", fecha=" + fecha + "]";
    }
    

}
