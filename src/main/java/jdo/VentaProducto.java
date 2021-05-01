package jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class VentaProducto {

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private String producto;
    private String usuario;
    private int cantidad;


    public VentaProducto() { }

    public VentaProducto(String producto, String usuario, int cantidad) {
        this.producto = producto;
        this.usuario = usuario;
        this.cantidad = cantidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String toString() {
        return "Producto: " + producto + " Usuario: " + usuario + " Cantidad: " + cantidad;
    }
}
