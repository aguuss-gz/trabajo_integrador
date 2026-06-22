package entities;

public class Producto extends Base{
    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private int stock;
    private boolean disponible;

    private Categoria categoria;

    public Producto(String nombre, String descripcion,
                    String imagen, Double precio,
                    int stock,
                    Categoria categoria) {
        super();
        setNombre(nombre);
        setDescripcion(descripcion);
        setImagen(imagen);
        setPrecio(precio);
        setStock(stock);
        this.disponible = true;
        setCategoria(categoria);
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        validarNoNulo(imagen, "Imagen");
        validarTextoValido(imagen, "Imagen");

        this.imagen = imagen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        validarNoNulo(precio, "Precio");
        validarCero(precio, "Precio");
        validarNegativo(precio, "Precio");

        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        validarNegativo(stock, "Stock");
        validarCero(stock, "Stock");

        this.stock = stock;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        validarNoNulo(categoria, "Categoría");

        this.categoria = categoria;

        // Sincronizamos la agregación Categoria-Producto
        categoria.agregarProducto(this);
    }

    @Override
    public String toString() {
        String nombreCategoria = (this.categoria == null) ? "Sin categoría" : this.categoria.getNombre();

        return "Producto #[" + getId() + "]: [" + getNombre() +
                "] | Precio: $" + getPrecio() +
                " | Stock: " + getStock() + " u." +
                " | Categoría: [" + nombreCategoria + "]";
    }
}
