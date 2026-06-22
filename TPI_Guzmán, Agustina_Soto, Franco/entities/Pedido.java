package entities;

import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;

    public Pedido(Estado estado,
                  FormaPago formaPago,
                  Usuario usuario) {
        super();

        this.fecha = LocalDate.now(); // Inicializamos fecha en la fecha de hoy
        setEstado(estado);
        this.total = 0.0; // Inicializamos en 0.0 porque se calcula solo
        setFormaPago(formaPago);
        this.detalles = new ArrayList<>(); // Inicializamos la lista vacía,
        // no se recibe como parámetro
        setUsuario(usuario);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        validarNoNulo(estado, "Estado");
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        validarNoNulo(formaPago, "Forma de Pago");
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return new ArrayList<>(this.detalles);
        /* Añadimos esto en caso de que, al llamar
        * al getter de detalles desde el main, se haga esto:
        * pedido.getDetalles().clear()
        *
        * Pasamos una copia de detalles en cambio.
        * */
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        validarNoNulo(usuario, "Usuario");
        this.usuario = usuario;
    }

    @Override
    public void calcularTotal(){
        this.total = 0.0; // Reseteamos para no pisar cálculos futuros

        for (DetallePedido d: detalles){
            this.total += d.getSubtotal();
        }
    }

    public void addDetallePedido(int cantidad, Producto p){
        validarNoNulo(p, "Producto");
        validarNegativo(cantidad, "Cantidad");
        validarCero(cantidad, "Cantidad");

        DetallePedido nuevo = new DetallePedido(cantidad, p);

        detalles.add(nuevo);

        calcularTotal(); // Calculamos el total, de lo contario queda en 0.0
        // todo el tiempo
    }

    public DetallePedido findDetallePedidoByProducto(Producto p){
        for (DetallePedido d: detalles){
            if (p.getId().equals(d.getProducto().getId())){
                return d;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto p){
        validarNoNulo(p, "Producto");
        DetallePedido buscado = findDetallePedidoByProducto(p);

        validarNoNulo(buscado, "Detalle de Pedido");

        detalles.remove(buscado);
        calcularTotal();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("> Pedido #[%d] | Fecha: [%s] | Estado: [%s] | FormaPago: [%s]%n",
                getId(), getFecha(), getEstado(), getFormaPago()));
        sb.append("-------------------------------------------------------------------\n");

        for (DetallePedido d : detalles) {
            sb.append(d.toString()).append("\n");
        }

        sb.append(String.format("TOTAL DEL PEDIDO: $%.2f%n", getTotal()));
        sb.append("-------------------------------------------------------------------");
        return sb.toString();
    }
}
