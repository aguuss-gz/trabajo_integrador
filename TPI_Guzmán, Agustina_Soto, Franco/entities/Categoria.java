package entities;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(String nombre, String descripcion) {
        super();
        setNombre(nombre);
        setDescripcion(descripcion);
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarNoNulo(nombre, "Nombre");
        validarTextoValido(nombre, "Nombre");

        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        validarNoNulo(descripcion, "Descripción");
        validarTextoValido(descripcion, "Descripción");

        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(this.productos);
    }

    public void agregarProducto(Producto p){
        validarNoNulo(p, "Producto");

        for (Producto b: productos){
            if (b.getNombre().equalsIgnoreCase(p.getNombre())){
                throw new IllegalArgumentException("Ese objeto ya se encuentra en la lista.");
            }
        }

        productos.add(p);
    }

    @Override
    public String toString() {
        return String.format("%n- Categoria: %s (ID: %d)" +
                "%n- Descripcion: %s" +
                "%n- Total Productos: %d",
                getNombre(), getId(), getDescripcion(), productos.size());
    }
}
