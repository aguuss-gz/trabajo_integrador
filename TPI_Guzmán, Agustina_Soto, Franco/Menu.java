import entities.*;
import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import exception.ListaVaciaException;
import exception.OpcionInvalidaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static List<Categoria> listaCategorias = new ArrayList<>();
    public static List<Pedido> listaPedidos = new ArrayList<>();
    public static List<Usuario> listaUsuarios = new ArrayList<>();
    public static List<Producto> listaProductos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) === ");
            System.out.println("1. Categorías ");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.println("Seleccione:");

            String entrada = sc.nextLine();

            try {
                opcion = Integer.parseInt(entrada);

                validarEntrada(opcion);

                switch (opcion) {
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    case 1:
                        menuCategoria(sc);
                        break;
                    case 2:
                        menuProducto(sc);
                        break;
                    case 3:
                        menuUsuario(sc);
                        break;
                    case 4:
                        menuPedido(sc);
                        break;

                }
            } catch (NumberFormatException e){
                System.out.println("Error: Entrada inválida.");
                opcion = -1;

            } catch (OpcionInvalidaException e){
                System.out.println("Error: " + e.getMessage());
                opcion = -1;
            }
        } while (opcion != 0);


        sc.close();
    }

    // Validaciones
    public static  void validarEntrada(int num){
        if (num < 0 || num > 4) throw new OpcionInvalidaException("Opción fuera de rango.");
    }

    public static void validarListaVacia(List<?> lista){
        if (lista.isEmpty()) throw new ListaVaciaException("La lista se encuentra vacía.");
    }

    // Buscar Objeto
    public static <T extends Base> T pedirYBuscarObjeto(Scanner sc, List<T> lista, String mensajePedirId) {
        validarListaVacia(lista);

        Long idInput = null;

        while (idInput == null) {
            System.out.print(mensajePedirId);
            try {
                idInput = Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: El ID debe ser un número entero válido. Intente de nuevo.");
            }
        }

        for (T elemento : lista) {
            if (elemento.getId().equals(idInput) && !elemento.isEliminado()) {
                return elemento;
            }
        }

        System.out.println("Error: El registro con ID " + idInput + " no existe o fue eliminado.");
        return null;
    }

    // Pedir Double
    public static Double pedirDouble(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número decimal válido (ejemplo: 1500.50).");
            }
        }
    }

    // Pedir Int
    public static int pedirInt(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número entero válido (sin decimales).");
            }
        }
    }

    // Mostrar submenú
    public static void mostrarSubMenu(){
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Eliminar");
        System.out.println("4. Editar");
        System.out.println("0. Salir");
        System.out.println("Seleccione:");
    }

    // Listar
    public static <T extends Base> void listar(List<T> lista) {
        validarListaVacia(lista);
        int mostradas = 0;

        for (T elemento : lista) {
            if (!elemento.isEliminado()) {
                System.out.println("\n---------------------------");
                System.out.println(elemento);
                System.out.println("---------------------------");
                mostradas++;
            }
        }

        if (mostradas == 0) {
            System.out.println("No hay registros activos para mostrar.");
        }
    }


 // Crear
        // CATEGORÍA
        public static void crearCateg(Scanner sc) {
        String opcionCrearCateg;

        do {
            System.out.print("Nombre: ");
            String nombreCat = sc.nextLine().trim();

            System.out.print("Descripción: ");
            String descCat = sc.nextLine().trim();

            if (!nombreCat.isEmpty() && !descCat.isEmpty()) {

                boolean yaExiste = false;
                for (Categoria c : listaCategorias) {
                    if (c.getNombre().equalsIgnoreCase(nombreCat) && !c.isEliminado()) {
                        yaExiste = true;
                        break;
                    }
                }

                if (yaExiste) {
                    System.out.println("Error: Ya existe una categoría activa con el nombre '" + nombreCat + "'.");
                } else {
                    try {
                        Categoria nuevaCategoria = new Categoria(nombreCat, descCat);
                        listaCategorias.add(nuevaCategoria);

                        System.out.println("\n¡Éxito! Categoría \"" + nuevaCategoria.getNombre() + "\" creada con ID: " + nuevaCategoria.getId());
                    } catch (IllegalArgumentException | NullPointerException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("Error: El nombre y la descripción no pueden estar vacíos.");
            }

            System.out.print("¿Desea crear otra categoría? (s/n): ");
            opcionCrearCateg = sc.nextLine().trim().toLowerCase();

        } while (opcionCrearCateg.equals("s"));
    }

        // PRODUCTO
        public static void crearProducto(Scanner sc) {
            if (listaCategorias.isEmpty()) {
                System.out.println("Error: No se pueden registrar productos si no existen categorías primero.");
                return;
            }

            String continuar;
            do {
                System.out.println("\n--- CREAR NUEVO PRODUCTO ---");
                System.out.print("Nombre: ");
                String nombre = sc.nextLine().trim();

                boolean yaExiste = false;
                for (Producto p : listaProductos) {
                    if (p.getNombre().equalsIgnoreCase(nombre) && !p.isEliminado()) {
                        yaExiste = true;
                        break;
                    }
                }

                if (yaExiste) {
                    System.out.println("Error: Ya existe un producto activo con el nombre '" + nombre + "'.");
                } else {
                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine().trim();

                    System.out.print("Nombre/URL de la Imagen: ");
                    String imagen = sc.nextLine().trim();

                    Double precio = pedirDouble(sc, "Precio: ");
                    int stock = pedirInt(sc, "Stock inicial: ");

                    Categoria catAsociada = pedirYBuscarObjeto(sc, listaCategorias, "Seleccione el ID de la categoría: ");

                    if (catAsociada == null) {
                        System.out.println("Error: La categoría no es válida. Producto no registrado.");
                    } else {
                        try {
                            Producto nuevoProducto = new Producto(nombre, descripcion, imagen, precio, stock, catAsociada);
                            listaProductos.add(nuevoProducto);

                            System.out.println("\n¡Éxito! Producto creado correctamente con ID: " + nuevoProducto.getId());
                        } catch (IllegalArgumentException | NullPointerException e) {
                            System.out.println("Error de validación en la entidad: " + e.getMessage());
                        }
                    }
                }

                System.out.print("\n¿Desea cargar otro producto? (s/n): ");
                continuar = sc.nextLine().trim().toLowerCase();

            } while (continuar.equals("s"));
        }

        // PEDIDO
        public static void crearPedido(Scanner sc){
            if (listaUsuarios.isEmpty() || listaProductos.isEmpty()) {
                System.out.println("Error: Se necesitan usuarios y productos activos para generar un pedido.");
                return;
            }

            System.out.println("\n--- CREACIÓN DE PEDIDO ---");
            Usuario cliente = pedirYBuscarObjeto(sc, listaUsuarios, "Ingrese el ID del usuario (cliente): ");
            if (cliente == null) return;

            System.out.println("\nSeleccione Forma de Pago:\n1. EFECTIVO\n2. TARJETA\n3. TRANSFERENCIA");
            int fpOpcion = pedirInt(sc, "Opción: ");
            FormaPago fp = (fpOpcion == 1) ? FormaPago.EFECTIVO : (fpOpcion == 2) ? FormaPago.TARJETA : FormaPago.TRANSFERENCIA;

            Pedido nuevoPedido = new Pedido(Estado.PENDIENTE, fp, cliente);

            String cargarMas;
            try {
                do {
                    System.out.println("\n--- Productos Disponibles ---");
                    for (Producto p : listaProductos) {
                        if (!p.isEliminado()) {
                            System.out.println("ID: " + p.getId() + " - " + p.getNombre() + " ($" + p.getPrecio() + " | Stock: " + p.getStock() + ")");
                        }
                    }

                    Producto prodElegido = pedirYBuscarObjeto(sc, listaProductos, "Seleccione el ID del producto a agregar: ");
                    if (prodElegido == null) {
                        throw new IllegalArgumentException("Selección de producto inválida. Operación abortada.");
                    }

                    int cantidad = pedirInt(sc, "Ingrese la cantidad de unidades: ");

                    nuevoPedido.addDetallePedido(cantidad, prodElegido);
                    System.out.println("¡Producto añadido al detalle!");

                    System.out.print("\n¿Desea agregar otro producto al pedido? (s/n): ");
                    cargarMas = sc.nextLine().trim().toLowerCase();

                } while (cargarMas.equals("s"));

                if (nuevoPedido.getDetalles().isEmpty()) {
                    System.out.println("Error: No se puede registrar un pedido sin detalles de compra.");
                } else {
                    listaPedidos.add(nuevoPedido);
                    System.out.println("\n¡Éxito! Pedido registrado.");
                }

            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("La creación del pedido fue cancelada.");
            }
        }

        // USUARIO
        public static void crearUsuario(Scanner sc){
            String opcionCrearUs = "s";

            do {
                System.out.println("\n--- CREAR NUEVO USUARIO ---");
                System.out.print("Nombre: ");
                String nombre = sc.nextLine().trim();

                System.out.print("Apellido: ");
                String apellido = sc.nextLine().trim();

                System.out.print("Mail: ");
                String mail = sc.nextLine().trim();

                boolean mailDuplicado = false;
                for (Usuario u : listaUsuarios) {
                    if (u.getMail().equalsIgnoreCase(mail) && !u.isEliminado()) {
                        mailDuplicado = true;
                        break;
                    }
                }

                if (mailDuplicado) {
                    System.out.println("Error: Ya existe un usuario activo registrado con el email '" + mail + "'.");
                } else {
                    System.out.print("Contraseña: ");
                    String contrasenia = sc.nextLine();

                    System.out.print("Celular: ");
                    String celular = sc.nextLine().trim();

                    System.out.println("Seleccione el Rol:\n1. ADMIN\n2. USUARIO");
                    System.out.print("Opción: ");
                    String entradaRol = sc.nextLine();

                    Rol rolFinal = Rol.USUARIO;
                    try {
                        int rolOpcion = Integer.parseInt(entradaRol);
                        if (rolOpcion == 1) {
                            rolFinal = Rol.ADMIN;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Aviso: Entrada de rol inválida. Se asignará 'USUARIO' por defecto.");
                    }

                    try {
                        Usuario nuevoUsuario = new Usuario(nombre, apellido, mail, celular, contrasenia, rolFinal);

                        listaUsuarios.add(nuevoUsuario);

                        System.out.println("\n¡Éxito! Usuario creado correctamente con ID: " + nuevoUsuario.getId());

                    } catch (NullPointerException | IllegalArgumentException e) {
                        System.out.println("Error de validación en la entidad: " + e.getMessage());
                    }
                }

                System.out.print("\n¿Desea crear otro usuario? (s/n): ");
                opcionCrearUs = sc.nextLine().trim().toLowerCase();

            } while (opcionCrearUs.equals("s"));
        }

    // Eliminar
        // CATEGORIA
        public static void eliminarCategoria(Scanner sc){
            Categoria buscado = pedirYBuscarObjeto(sc, listaCategorias, "Ingrese el ID de la categoría buscada: ");

            if (buscado != null) {
                System.out.println("¿Seguro que desea continuar? (s/n): ");
                String entrada = sc.nextLine().trim().toLowerCase();

                if (entrada.equals("s")){
                    buscado.setEliminado(true);

                    for (Producto p : listaProductos) {
                        if (p.getCategoria() != null && p.getCategoria().getId().equals(buscado.getId())) {
                            p.setCategoria(null); // Desvinculamos la categoría eliminada
                        }
                    }

                    System.out.println("\n¡Éxito! Categoría eliminada del sistema.");
                }
            }
        }

        // PEDIDO
        public static void eliminarPedido(Scanner sc){
            Pedido buscado = pedirYBuscarObjeto(sc, listaPedidos, "Ingrese el ID del pedido a eliminar: ");

            if (buscado != null) {
                System.out.print("¿Está seguro de que desea eliminar este pedido? (s/n): ");
                String entrada = sc.nextLine().trim().toLowerCase();

                if (entrada.equals("s")) {
                    buscado.setEliminado(true);
                    if (buscado.getEstado() == Estado.PENDIENTE) {
                        for (DetallePedido det : buscado.getDetalles()) {
                            if (det.getProducto() != null) {
                                int stockActual = det.getProducto().getStock();
                                det.getProducto().setStock(stockActual + det.getCantidad());
                            }
                        }
                    }
                    System.out.println("\n¡Éxito! Pedido cancelado y stock reincorporado.");
                } else {
                    System.out.println("\nOperación cancelada.");
                }
            }
        }

        // PRODUCTO
        public static void eliminarProducto(Scanner sc) {
            Producto buscado = pedirYBuscarObjeto(sc, listaProductos, "Ingrese el ID del producto a eliminar: ");

            if (buscado != null) {
                System.out.print("¿Está seguro que desea dar de baja este producto? (s/n): ");
                String entrada = sc.nextLine().trim().toLowerCase();

                if (entrada.equals("s")) {
                    buscado.setEliminado(true);

                    for (Pedido ped : listaPedidos) {
                        if (ped.getEstado() == Estado.PENDIENTE) {
                            for (DetallePedido det : ped.getDetalles()) {
                                if (det.getProducto() != null && det.getProducto().getId().equals(buscado.getId())) {
                                    det.setProducto(null);
                                }
                            }
                        }
                    }
                    System.out.println("\n¡Éxito! Producto eliminado del catálogo.");
                } else {
                    System.out.println("\nOperación cancelada. El producto sigue activo.");
                }
            }
        }

        // USUARIO
        public static void eliminarUsuario(Scanner sc){
            Usuario buscado = pedirYBuscarObjeto(sc, listaUsuarios, "Ingrese el ID del usuario a eliminar: ");

            if (buscado != null) {
                System.out.println("\nUsuario seleccionado: " + buscado.getNombre() + " " + buscado.getApellido() + " (" + buscado.getMail() + ")");
                System.out.print("¿Está seguro que desea dar de baja a este usuario? (s/n): ");
                String entrada = sc.nextLine().trim().toLowerCase();

                if (entrada.equals("s")) {
                    buscado.setEliminado(true);

                    for (Pedido p : listaPedidos) {
                        if (p.getUsuario() != null && p.getUsuario().getId().equals(buscado.getId())) {
                            if (p.getEstado() == Estado.PENDIENTE) {
                                p.setUsuario(null);
                            }
                        }
                    }
                    System.out.println("\n¡Éxito! Usuario eliminado logícamente.");
                } else {
                    System.out.println("\nOperación cancelada. No se modificó el estado del usuario.");
                }
            }
        }

    // Editar
        // CATEGORIA
        public static void editarCategoria(Scanner sc){
            Categoria buscado = pedirYBuscarObjeto(sc, listaCategorias, "Ingrese el ID de la categoría a editar: ");

            if (buscado != null) {
                System.out.print("Ingrese el nuevo nombre: ");
                String nuevoNombre = sc.nextLine().trim();

                System.out.print("Ingrese la nueva descripción: ");
                String nuevaDesc = sc.nextLine().trim();


                if (!nuevoNombre.isEmpty()) {
                    try {
                        buscado.setNombre(nuevoNombre);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage() + ". No se modificó el nombre.");
                    }
                }

                if (!nuevaDesc.isEmpty()) {
                    try {
                        buscado.setDescripcion(nuevaDesc);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage() + ". No se modificó la descripción.");
                    }
                }

                System.out.println("\n¡Éxito! Proceso de edición finalizado.");
            }
        }

        // PEDIDO
        public static void actualizarPedido(Scanner sc) {
            Pedido buscado = pedirYBuscarObjeto(sc, listaPedidos, "Ingrese el ID del pedido a actualizar: ");

            if (buscado != null) {
                System.out.println("\n¿Qué desea modificar?\n1. Estado\n2. Forma de Pago\n3. Ambos\n4. Cancelar");
                int opcion = pedirInt(sc, "Opción: ");

                if (opcion == 1 || opcion == 3) {
                    System.out.println("\nSeleccione el nuevo Estado:\n1. PENDIENTE\n2. CONFIRMADO\n3. TERMINADO\n4. CANCELADO");
                    int estOpcion = pedirInt(sc, "Opción: ");
                    Estado nuevoEstado = (estOpcion == 1) ? Estado.PENDIENTE :
                            (estOpcion == 2) ? Estado.CONFIRMADO :
                                    (estOpcion == 3) ? Estado.TERMINADO : Estado.CANCELADO;
                    buscado.setEstado(nuevoEstado);
                }

                if (opcion == 2 || opcion == 3) {
                    System.out.println("\nSeleccione la nueva Forma de Pago:\n1. TARJETA\n2. TRANSFERENCIA\n3. EFECTIVO");
                    int fpOpcion = pedirInt(sc, "Opción: ");
                    FormaPago nuevaFP = (fpOpcion == 1) ? FormaPago.TARJETA :
                            (fpOpcion == 2) ? FormaPago.TRANSFERENCIA : FormaPago.EFECTIVO;
                    buscado.setFormaPago(nuevaFP);
                }

                System.out.println("\n¡Éxito! Pedido actualizado correctamente.");
            }
        }

        // PRODUCTO
        public static void editarProducto(Scanner sc) {
            Producto buscado = pedirYBuscarObjeto(sc, listaProductos, "Ingrese el ID del producto a editar: ");

            if (buscado != null) {
                System.out.print("Nuevo Nombre: ");
                String nuevoNombre = sc.nextLine().trim();
                if (!nuevoNombre.isEmpty()) buscado.setNombre(nuevoNombre);

                System.out.print("Nueva Descripción: ");
                String nuevaDesc = sc.nextLine().trim();
                if (!nuevaDesc.isEmpty()) buscado.setDescripcion(nuevaDesc);

                System.out.print("Nueva Imagen: ");
                String nuevaImg = sc.nextLine().trim();
                if (!nuevaImg.isEmpty()) buscado.setImagen(nuevaImg);

                System.out.print("Nuevo Precio: ");
                String entradaPrecio = sc.nextLine().trim();
                if (!entradaPrecio.isEmpty()) {
                    try {
                        buscado.setPrecio(Double.parseDouble(entradaPrecio));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de precio inválido. No se modificó.");
                    }
                }

                System.out.print("Nuevo Stock: ");
                String entradaStock = sc.nextLine().trim();
                if (!entradaStock.isEmpty()) {
                    try {
                        buscado.setStock(Integer.parseInt(entradaStock));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Stock inválido. No se modificó.");
                    }
                }

                System.out.print("¿Desea cambiar la categoría de este producto? (s/n): ");
                String cambiarCat = sc.nextLine().trim().toLowerCase();
                if (cambiarCat.equals("s")) {
                    System.out.println("\n--- Categorías Disponibles ---");
                    for (Categoria c : listaCategorias) {
                        if (!c.isEliminado()) {
                            System.out.println("ID: " + c.getId() + " - " + c.getNombre());
                        }
                    }

                    Categoria nuevaCat = pedirYBuscarObjeto(sc, listaCategorias, "Seleccione el ID de la nueva categoría: ");
                    if (nuevaCat != null) {
                        buscado.setCategoria(nuevaCat);
                    }
                }

                System.out.println("\n¡Éxito! Proceso de edición de producto finalizado.");
            }
        }

        // USUARIO
        public static void editarUsuario(Scanner sc){
            Usuario buscado = pedirYBuscarObjeto(sc, listaUsuarios, "Ingrese el ID del usuario a editar: ");

            if (buscado != null) {
                System.out.print("Ingrese el nuevo nombre: ");
                String nuevoNombre = sc.nextLine().trim();

                System.out.print("Ingrese el nuevo apellido: ");
                String nuevoApellido = sc.nextLine().trim();

                System.out.print("Ingrese el nuevo mail: ");
                String nuevoMail = sc.nextLine().trim();

                System.out.print("Ingrese la nueva contraseña: ");
                String nuevaContrasenia = sc.nextLine();

                System.out.print("Ingrese el nuevo celular: ");
                String nuevoCelular = sc.nextLine().trim();

                if (!nuevoNombre.isEmpty()) buscado.setNombre(nuevoNombre);
                if (!nuevoApellido.isEmpty()) buscado.setApellido(nuevoApellido);
                if (!nuevaContrasenia.isEmpty()) buscado.setContrasenia(nuevaContrasenia);
                if (!nuevoCelular.isEmpty()) buscado.setCelular(nuevoCelular);

                if (!nuevoMail.isEmpty() && !nuevoMail.equalsIgnoreCase(buscado.getMail())) {
                    boolean mailDuplicado = false;
                    for (Usuario u : listaUsuarios) {
                        if (u.getMail().equalsIgnoreCase(nuevoMail) && !u.isEliminado()) {
                            mailDuplicado = true;
                            break;
                        }
                    }

                    if (mailDuplicado) {
                        System.out.println("Error: El mail '" + nuevoMail + "' ya está siendo usado por otro usuario. No se modificó el correo.");
                    } else {
                        buscado.setMail(nuevoMail);
                    }
                }

                System.out.println("\n¡Éxito! Proceso de edición de usuario finalizado.");
            }
        }

    // Menú Categoría
    public static void menuCategoria(Scanner sc){
        int opcionCategoria = -1;

        do {
            mostrarSubMenu();

            String entrada = sc.nextLine();

            try {
                opcionCategoria = Integer.parseInt(entrada);
                validarEntrada(opcionCategoria);

                switch (opcionCategoria){
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    case 1:
                        listar(listaCategorias);
                        break;
                    case 2:
                        crearCateg(sc);
                        break;
                    case 3:
                        eliminarCategoria(sc);
                        break;
                    case 4:
                        editarCategoria(sc);
                        break;

                }
            } catch(OpcionInvalidaException e){
                System.out.println("Error: " + e.getMessage());
                opcionCategoria = -1;
            } catch (NumberFormatException e){
                System.out.println("Error: Entrada inválida");
                opcionCategoria = -1;
            } catch (ListaVaciaException e) {
                System.out.println("Aviso: " + e.getMessage());
                opcionCategoria = -1;
            }

        } while(opcionCategoria != 0);
    }

    // Menú Producto
    public static void menuProducto(Scanner sc){
        int opcionProd = -1;

        do {
            mostrarSubMenu();

            String entrada = sc.nextLine();

            try {
                opcionProd = Integer.parseInt(entrada);
                validarEntrada(opcionProd);

                switch (opcionProd){
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    case 1:
                        listar(listaProductos);
                        break;
                    case 2:
                        crearProducto(sc);
                        break;
                    case 3:
                        eliminarProducto(sc);
                        break;
                    case 4:
                        editarProducto(sc);
                        break;

                }
            } catch(OpcionInvalidaException e){
                System.out.println("Error: " + e.getMessage());
                opcionProd = -1;
            } catch (NumberFormatException e){
                System.out.println("Error: Entrada inválida");
                opcionProd = -1;
            } catch (ListaVaciaException e) {
                System.out.println("Aviso: " + e.getMessage());
                opcionProd = -1;
            }


        } while(opcionProd != 0);
    }

    // Menú Usuario
    public static void menuUsuario(Scanner sc){
        int opcionUs = -1;

        do {
            mostrarSubMenu();

            String entrada = sc.nextLine();

            try {
                opcionUs = Integer.parseInt(entrada);
                validarEntrada(opcionUs);

                switch (opcionUs){
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    case 1:
                        listar(listaUsuarios);
                        break;
                    case 2:
                        crearUsuario(sc);
                        break;
                    case 3:
                        eliminarUsuario(sc);
                        break;
                    case 4:
                        editarUsuario(sc);
                        break;

                }
            } catch(OpcionInvalidaException e){
                System.out.println("Error: " + e.getMessage());
                opcionUs = -1;
            } catch (NumberFormatException e){
                System.out.println("Error: Entrada inválida");
                opcionUs = -1;
            } catch (ListaVaciaException e) {
                System.out.println("Aviso: " + e.getMessage());
                opcionUs = -1;
            }


        } while(opcionUs != 0);
    }

    // Menú Pedido
    public static void menuPedido(Scanner sc){
        int opcionPedido = -1;

        do {
            mostrarSubMenu();

            String entrada = sc.nextLine();

            try {
                opcionPedido = Integer.parseInt(entrada);
                validarEntrada(opcionPedido);

                switch (opcionPedido){
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    case 1:
                        listar(listaPedidos);
                        break;
                    case 2:
                        crearPedido(sc);
                        break;
                    case 3:
                        eliminarPedido(sc);
                        break;
                    case 4:
                        actualizarPedido(sc);
                        break;

                }
            } catch(OpcionInvalidaException e){
                System.out.println("Error: " + e.getMessage());
                opcionPedido = -1;
            } catch (NumberFormatException e){
                System.out.println("Error: Entrada inválida");
                opcionPedido = -1;
            } catch (ListaVaciaException e) {
                System.out.println("Aviso: " + e.getMessage());
                opcionPedido = -1;
            }


        } while(opcionPedido != 0);
    }
}
