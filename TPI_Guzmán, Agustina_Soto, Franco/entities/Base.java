package entities;
import java.time.LocalDateTime;

public abstract class Base {
    private final Long id;
    private boolean eliminado;
    private final LocalDateTime  createdAt;
    // Utilizamos LocalDateTime -> Además de la fecha, da la hora.

    private static Long cantidadTotal = 0L;
    /*
    * Estática para que sea compartida por toda la clase y
    * no vuelva a empezar en 0 cada vez que le sumamos 1.
    * Luego sincronizamos con id para que se auto-incremente.
    */
    public Base() {
        cantidadTotal++;
        this.id = cantidadTotal;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    // Agregamos dos métodos para validar nulos y texto
    /* Todas las clases dadas heredan de base, si colocamos
    * los métodos como protected podrán ser accedidos después por
    * las clases y evitar repetir código. */

    protected final void validarNoNulo(Object objeto, String nombreAtributo) {
        if (objeto == null) {
            throw new NullPointerException("El atributo '" + nombreAtributo + "' no puede ser nulo.");
        }

        /*
        * Object es la clase madre de todas las clases,
        * le podemos pasar lo que sea (polimorfismo)
        * */
    }

    protected final void validarTextoValido(String texto, String nombreAtributo) {
        validarNoNulo(texto, nombreAtributo); // Para evitar que .trim() falle

        if (texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El atributo '" + nombreAtributo + "' no puede estar vacío.");
        }
    }

    /*
    * Hacemos un metodo para validar negativos.
    * Object es muy genérico, al comparar object < 0 larga error;
    * utilizaremos Number como wrapper en cambio.
    * Parseamos n a double con .doubleValue() y luego comparamos para tirar
    * error en caso de que n sea negativo.

    * */
    protected final void validarNegativo(Number n, String nombreAtributo){
        validarNoNulo(n, nombreAtributo);

        if (n.doubleValue() < 0) throw new IllegalArgumentException(nombreAtributo + " no puede ser negativo.");

    }

    protected final void validarCero(Number n, String nombreAtributo){
        validarNoNulo(n, nombreAtributo);

        if (n.doubleValue() == 0) throw new IllegalArgumentException(nombreAtributo + " no puede ser cero.");
    }

    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static Long getCantidadTotal() {
        return cantidadTotal;
    }

    @Override
    public abstract String toString();
}
