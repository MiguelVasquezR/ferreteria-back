package resenas.modelo;

public class Proveedor {
    private String id;
    private String id_direccion;
    private String nombre;
    private String telefono;
    private String correo;
    private String rfc;

    
    
    
    public Proveedor(){
    
    }
    
    public Proveedor(String id, String id_direccion, String nombre, String telefono,
     String correo, String rfc) {
    this.id=id;
    this.id_direccion=id_direccion;
    this.nombre=nombre;
    this.telefono=telefono;
    this.correo=correo;
    this.rfc=rfc;
    
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId_direccion() {
        return id_direccion;
    }
    
    public void setId_direccion(String id_direccion) {
        this.id_direccion = id_direccion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getRfc() {
        return rfc;
    }
    
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
    @Override
    public String toString() {
        return "Proveedor [id=" + id + ", id_direccion=" + id_direccion + ", nombre=" + nombre + ", telefono=" + telefono
                + ", correo=" + correo + ", rfc=" + rfc + "]";
    }
 
    }