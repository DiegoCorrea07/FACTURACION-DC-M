public class Producto {
    private String nombre;
    private double precioNormal;
    private double precioMayorista;
    private int cantidadMayorista;

    public Producto(String nombre, double precioNormal, double precioMayorista, int cantidadMayorista) {
        this.nombre = nombre;
        this.precioNormal = precioNormal;
        this.precioMayorista = precioMayorista;
        this.cantidadMayorista = cantidadMayorista;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioNormal() {
        return precioNormal;
    }

    public double getPrecioMayorista() {
        return precioMayorista;
    }

    public int getCantidadMayorista() {
        return cantidadMayorista;
    }
}
