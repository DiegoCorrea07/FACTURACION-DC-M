import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProgramaFacturacion {
    private static ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<ItemFactura> itemsFactura = new ArrayList<>();
    private static String nombreCliente;
    private static String cedulaCliente;

    public static void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        String opcion;

        // Menú principal
        do {
            System.out.println("----- MENÚ PRINCIPAL -----");
            System.out.println("a. Ingresar producto");
            System.out.println("b. Facturar producto");
            System.out.println("c. Imprimir factura");
            System.out.println("d. Salir");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = scanner.nextLine();
            } catch (NoSuchElementException e) {
                opcion = "d"; // Salir del programa
            }

            // Selección de opción
            switch (opcion) {
                case "a":
                    ingresarProducto();
                    break;
                case "b":
                    facturarProducto();
                    break;
                case "c":
                    imprimirFactura();
                    break;
                case "d":
                    System.out.println("\n---DC STORE AGRADECE SU COMPRA---\n");
                    System.out.println("\n         ¡Hasta pronto!\n");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                    break;
            }

            System.out.println();
        } while (!opcion.equals("d"));

        scanner.close();
    }


    public static void ingresarProducto() {
        Scanner scanner = new Scanner(System.in);
        String respuesta;
        int contadorProductos = 0;

        // Ingreso de productos
        do {
            if (contadorProductos >= 10) {
                System.out.println("¡ADVERTENCIA!. Ha alcanzado el límite de productos.");
                break;
            }

            System.out.print("Ingrese el nombre del producto: ");
            String nombreProducto = scanner.nextLine();

            // Ingreso del precio normal del producto
            double precioNormal;
            while (true) {
                System.out.print("Ingrese el precio normal del producto: $");
                try {
                    precioNormal = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Precio inválido. Por favor, ingrese un precio válido.");
                }
            }

            // Ingreso del precio al por mayor del producto
            double precioMayorista;
            while (true) {
                System.out.print("Ingrese el precio al por mayor del producto: $");
                try {
                    precioMayorista = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Precio inválido. Por favor, ingrese un número válido.");
                }
            }

            // Ingreso de la cantidad de unidades para aplicar el precio al por mayor
            int cantidadMayorista;
            while (true) {
                System.out.print("Ingrese la cantidad de unidades para aplicar el precio al por mayor: ");
                try {
                    cantidadMayorista = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad inválida. Por favor, ingrese una cantidad válida.");
                }
            }

            // Creación y agregado del producto a la lista de productos
            Producto producto = new Producto(nombreProducto, precioNormal, precioMayorista, cantidadMayorista);
            productos.add(producto);

            contadorProductos++;

            // Pregunta si se desea ingresar otro producto
            while (true) {
                System.out.print("¿Desea ingresar otro producto? (Sí/No): ");
                respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("Si") || respuesta.equalsIgnoreCase("No")) {
                    break;
                } else {
                    System.out.println("Opción inválida. Por favor, ingrese 'Sí' o 'No'.");
                }
            }
        } while (respuesta.equalsIgnoreCase("Si"));

        System.out.println();
    }

    public static void facturarProducto() {
        Scanner scanner = new Scanner(System.in);

        // Verificación de productos ingresados
        if (productos.isEmpty()) {
            System.out.println("No hay productos ingresados. Por favor, ingrese al menos un producto.");
            return;
        }

        boolean regresarMenuPrincipal = false;

        // Facturación de productos
        do {
            System.out.println("----- LISTA DE PRODUCTOS DISPONIBLES -----");
            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                System.out.println((i + 1) + ". " + producto.getNombre() +
                        " Precio normal: $" + producto.getPrecioNormal() + " ||" +
                        " Precio al por mayor, más de " + producto.getCantidadMayorista() + " unidades: $" +
                        producto.getPrecioMayorista());
            }

            int numeroProducto;
            while (true) {
                System.out.print("Ingrese el número del producto que desea facturar: ");
                try {
                    numeroProducto = Integer.parseInt(scanner.nextLine());
                    if (numeroProducto >= 1 && numeroProducto <= productos.size()) {
                        break;
                    } else {
                        System.out.println("Número de producto inválido. Por favor, ingrese una opción válida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Número inválido. Por favor, ingrese un número válido.");
                }
            }

            Producto productoSeleccionado = productos.get(numeroProducto - 1);

            int cantidadUnidades;
            while (true) {
                System.out.print("Ingrese la cantidad de unidades del producto: ");
                try {
                    cantidadUnidades = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad inválida. Por favor, ingrese una válida.");
                }
            }

            // Creación y agregado del item de factura a la lista de items de factura
            ItemFactura itemFactura = new ItemFactura(productoSeleccionado, cantidadUnidades);
            itemsFactura.add(itemFactura);

            String respuesta;
            do {
                System.out.print("¿Desea facturar otro producto? (Sí/No): ");
                respuesta = scanner.nextLine();
            } while (!respuesta.equalsIgnoreCase("Si") && !respuesta.equalsIgnoreCase("No"));

            if (respuesta.equalsIgnoreCase("No")) {
                regresarMenuPrincipal = true;
                break;
            }

        } while (true);

        if (regresarMenuPrincipal) {
            System.out.println();
            mostrarMenuPrincipal();
        }
    }

    public static void imprimirFactura() {
        Scanner scanner = new Scanner(System.in);

        // Verificación de productos facturados
        if (itemsFactura.isEmpty()) {
            System.out.println("No hay productos facturados. Por favor, facture al menos un producto.");
            return;
        }

        // Ingreso de datos del cliente
        System.out.print("Ingrese el nombre del cliente: ");
        nombreCliente = scanner.nextLine();
        System.out.print("Ingrese el número de cédula del cliente: ");
        cedulaCliente = scanner.nextLine();

        double subtotal = 0.0;

        System.out.println("\n------ DC STORE ------");
        System.out.println("****** FACTURA ******\n");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Cédula: " + cedulaCliente);
        System.out.println("\nDetalle de la factura:");

        // Impresión de los items de factura
        for (int i = 0; i < itemsFactura.size(); i++) {
            ItemFactura itemFactura = itemsFactura.get(i);
            Producto producto = itemFactura.getProducto();

            // Cálculo del precio del producto (normal o al por mayor)
            double precio = itemFactura.getCantidad() >= producto.getCantidadMayorista() ?
                    producto.getPrecioMayorista() : producto.getPrecioNormal();
            double totalProducto = precio * itemFactura.getCantidad();
            subtotal += totalProducto;

            System.out.println((i + 1) + ". " + producto.getNombre() +
                    " - Cantidad: " + itemFactura.getCantidad() +
                    " - Precio: $" + precio +
                    " - Total: $" + totalProducto);
        }

        // Cálculo del descuento y total
        double descuento = calcularDescuento(subtotal);
        double total = subtotal - descuento;

        System.out.println("\nSubtotal: $" + subtotal);
        System.out.println("Descuento: $" + descuento);
        System.out.println("Total: $" + total);

        System.out.println();
    }

    public static double calcularDescuento(double subtotal) {
        if (subtotal <= 100) {
            return 0.0;
        } else if (subtotal <= 500) {
            return subtotal * 0.05;
        } else if (subtotal <= 1000) {
            return subtotal * 0.07;
        } else {
            return subtotal * 0.1;
        }
    }
}

