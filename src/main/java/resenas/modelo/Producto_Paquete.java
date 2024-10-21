package resenas.modelo;

public class Producto_Paquete {
    String idProductoPaquete;
    String idProducto;
    String idPaquete;

    public Producto_Paquete() {
    }

    public String getIdProductoPaquete() {
        return idProductoPaquete;
    }

    public void setIdProductoPaquete(String idProductoPaquete) {
        this.idProductoPaquete = idProductoPaquete;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(String idPaquete) {
        this.idPaquete = idPaquete;
    }

    @Override
    public String toString() {
        return "Producto_Paquete [idProductoPaquete=" + idProductoPaquete + ", idProducto=" + idProducto
                + ", idPaquete=" + idPaquete + "]";
    }
}
