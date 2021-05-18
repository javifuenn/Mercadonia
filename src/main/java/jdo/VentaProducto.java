package jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
/**
 * Clase VentaProducto donde un Usuario compra un Producto con una cantidad
 * @author Unai
 *
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class VentaProducto {

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private String producto;
    private String usuario;
    private int cantidad;

    /**
     * Constructor de la clase VentaProducto vacio
     */
    public VentaProducto() { }
    /**
     * Constructor de la clase VentaProducto
     * @param producto nombre del Producto Vendido
     * @param usuario nombre del Usuario que ha comprado el Producto
     * @param cantidad int de la cantidad comprada del Producto por el Usuario
     */
    public VentaProducto(String producto, String usuario, int cantidad) {
        this.producto = producto;
        this.usuario = usuario;
        this.cantidad = cantidad;
    }
    /**
     * Devuelve el nombre del Usuario de la VentaProducto
     * @return String con el nombre del Usuario que ha comprado un Producto
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * Cambia el nombre del Usuario de la VentaProducto
     * @param usuario String con el nombre del Usuario a introducir en la VentaProducto
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * Devuelve el nombre del Producto de la VentaProducto
     * @return String con el nombre del Producto a introducir en la VentaProducto
     */
    public String getProducto() {
        return producto;
    }
    /**
     * Cambia el nombre del Producto de la VentaProducto
     * @param producto String con el nombre del Producto a introducir en la VentaProducto
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }
    /**
     * Devuelve la cantidad del Producto que de la VentaProducto
     * @return int con la cantidad de Producto comprado en la VentaProducto
     */
    public int getCantidad() {
        return cantidad;
    }
    /**
     * Cambia la cantidad de Producto que se ha comprado en la VentaProducto
     * @param cantidad int con la cantidad de Producto a introducir en la VentaProducto
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    /**
     * To String de VentaProducto
     */
    public String toString() {
        return "Producto: " + producto + " Usuario: " + usuario + " Cantidad: " + cantidad;
    }
}
