# Food Store - Sistema de Gestión de Pedidos

Sistema de consola desarrollado en **Java 21** para la gestión transaccional de un negocio de comidas. Toda la persistencia opera de forma temporal en memoria activa a través de **Colecciones de Java** (`ArrayList`), aplicando herencia, polimorfismo y lógica defensiva en cascada.

---

## Arquitectura de Paquetes

* `entities/`: Modelos del dominio (Base, Categoria, Producto, Usuario, Pedido, DetallePedido).
* `enums/`: Enumeradores estrictos (Rol, Estado, FormaPago).
* `exception/`: Excepciones personalizadas (ListaVaciaException, OpcionInvalidaException).
* `interfaces/`: Contrato de negocio (Calculable).
* `Menu.java`: Controlador central e interfaz de usuario por consola.

---

## Cómo Ejecutar el Proyecto

### Opción 1: Desde la Terminal (Consola)
Abrí la terminal de tu sistema, parate dentro de la carpeta `src/` de este proyecto y ejecutá el siguiente comando unificado para compilar e iniciar el programa en el acto:

```bash
javac Menu.java entities/*.java enums/*.java exception/*.java interfaces/*.java && java Menu

```
## Link del video: https://drive.google.com/file/d/19FEptKIlC6JprRJnLFrcIbv3JIfBjZny/view?usp=sharing
