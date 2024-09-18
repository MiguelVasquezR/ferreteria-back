package resenas.modelo;

public class Persona {
private String id;
private String id_direccion;
private String nombre;
private int telefono;
private String correo;
private String rfc;
private float saldo_pendiente;



public Persona(){

}

public Persona(String id, String id_direccion, String nombre, int telefono,
 String correo, String rfc, float saldo_pendiente) {
this.id=id;
this.id_direccion=id_direccion;
this.nombre=nombre;
this.telefono=telefono;
this.correo=correo;
this.rfc=rfc;
this.saldo_pendiente=saldo_pendiente;

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

public int getTelefono() {
    return telefono;
}

public void setTelefono(int telefono) {
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

public float getSaldo_pendiente() {
    return saldo_pendiente;
}

public void setSaldo_pendiente(float saldo_pendiente) {
    this.saldo_pendiente = saldo_pendiente;
}


}