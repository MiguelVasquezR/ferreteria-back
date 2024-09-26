package resenas.modelo;

public class Direccion {
    private String id;
    private String ciudad;
    private String colonia;
    private String calle;
    private String numero;

    public Direccion(){

    }

    public Direccion(String id, String ciudad, String colonia, String calle, String numero) {
        this.id=id;
        this.ciudad=ciudad;
        this.colonia=colonia;
        this.calle=calle;
        this.numero=numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Direccion [id=" + id + ", ciudad=" + ciudad + ", colonia=" + colonia + ", calle=" + calle + ", numero="
                + numero + "]";
    }

    

}
