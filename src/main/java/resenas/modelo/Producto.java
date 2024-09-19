package resenas.modelo;

public class Producto {
    private String id; 
    private String nombre;
    private float cantidad;
    private float stock_minimo;
    private float costo;
    private float precio_menudeo;
    private float precio_mayoreo;

    public Producto(String id, String nombre, float cantidad, float stock_minimo, float costo, float precio_menudeo,
            float precio_mayoreo) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.stock_minimo = stock_minimo;
        this.costo = costo;
        this.precio_menudeo = precio_menudeo;
        this.precio_mayoreo = precio_mayoreo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(float stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Producto() {

    }

    public float getPrecio_menudeo() {
        return precio_menudeo;
    }

    public void setPrecio_menudeo(float precio_menudeo) {
        this.precio_menudeo = precio_menudeo;
    }

    public float getPrecio_mayoreo() {
        return precio_mayoreo;
    }

    public void setPrecio_mayoreo(float precio_mayoreo) {
        this.precio_mayoreo = precio_mayoreo;
    }

}

