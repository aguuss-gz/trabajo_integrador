package entities;

import enums.Rol;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;

    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario(String nombre,
                   String apellido, String mail,
                   String celular,
                   String contrasenia, Rol rol) {
        super();
        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setCelular(celular);
        setContrasenia(contrasenia);
        setRol(rol);
        this.pedidos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarNoNulo(nombre, "Nombre");
        validarTextoValido(nombre, "Nombre");

        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        validarNoNulo(apellido, "Apellido");
        validarTextoValido(apellido, "Apellido");

        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        validarNoNulo(mail, "Mail");
        validarTextoValido(mail, "Mail");

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        /* Verificamos con regex:
        * 1. Que haya letras, números y algunos caracteres como puntos,
        * guiones, etc. antes del @
        * 2. Que el arroba se encuentre sí o sí
        * 3. Luego del arroba aceptamos letras, números y caracteres
        * 4. Que haya sí o sí un punto
        * 5. Que luego del punto haya solo texto de mínimo 2 caracteres
        *
        * pepitoLopez123@gmail.com -> Aceptado
        * Chinchulín 343 --> Denegado
        */

        if (!mail.matches(regex)){
            throw new IllegalArgumentException("Formato de mail inválido.");
        }

        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        validarNoNulo(celular, "Celular");
        validarTextoValido(celular, "Celular");

        String regex = "^(\\+54|54)?[\\s-]?(9)?[\\s-]?\\d{2,4}[\\s-]?\\d{6,8}$";
        /* Verificamos que el número
        * siga el estándar de Argentina:
        * Ejemplo: +54 1 123 4567
        *
        * Acepta:
        * 1. Espacios
        * 2. No espacios
        * 3. Guiones
        * Siempre y cuando respete el formato.
        */
        if (!celular.matches(regex)) throw new IllegalArgumentException("Formato de celular inválido.");

        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        validarNoNulo(contrasenia, "Contraseña");
        validarTextoValido(contrasenia, "Contraseña");

        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        validarNoNulo(rol, "Rol");
        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(this.pedidos);
    }

    public void agregarPedido(Pedido p){
        validarNoNulo(p, "Pedido");
        for (Pedido e: pedidos){
            if (e.getId().equals(p.getId())){
                throw new IllegalArgumentException("El producto ya se encuentra en la lista.");
            }
        }

        pedidos.add(p);

        if (p.getUsuario() != this){
            p.setUsuario(this);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "=========================================================================================%n" +
                        "USUARIO: [%s %s] | Mail: [%s] | Rol: [%s]%n" +
                        "=========================================================================================",
                getNombre(), getApellido(), getMail(), getRol()
        );
    }
}
