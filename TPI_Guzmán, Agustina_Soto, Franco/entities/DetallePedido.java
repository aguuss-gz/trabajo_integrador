package entities;

public class DetallePedido extends Base{
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad,
                         Producto producto) {
        super();
        setProducto(producto);
        setCantidad(cantidad);
        setSubtotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        validarCero(cantidad, "Cantidad");
        validarNegativo(cantidad, "Cantidad");

        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        validarNoNulo(producto, "Producto");
        this.producto = producto;
    }

    /*
    * El setter de subtotal, al usar calcularSubtotal
    * va al final; no podemos calcular con elementos
    * no creados todavía (Producto, Cantidad).
    */

    private Double calcularSubtotal(){
        return (getCantidad() * producto.getPrecio());
    }

    public void setSubtotal() {
        Double subtotal = calcularSubtotal();
        /* Si bien la lógica del subtotal está encapsulada,
         * agregamos validaciones para casos de prueba.
         * Por si acaso.
         * Además, si queremos cambiar el código a futuro, estas validaciones
         * ayudan a escalar fácilmente el código.
         * */
        validarNoNulo(subtotal, "Subtotal");
        validarNegativo(subtotal, "Subtotal");
        validarCero(subtotal, "Subtotal");

        this.subtotal = subtotal;
    }


    @Override
    public String toString() {
        return String.format("   - DetallePedido #[%d]: [%s] x [%d] => Subtotal: $%.2f",
                getId(), getProducto().getNombre(), getCantidad(), getSubtotal());
    }
}
