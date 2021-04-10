package jdo;

import javax.jdo.annotations.*;
import java.util.Date;

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Cesta {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private int id;
    private Usuarios usuario;
    private Producto producto;
    private Date fechaExpiracion;

    public Cesta(Usuarios usuario, Producto producto, Date fechaExpiracion) {
        this.usuario = usuario;
        this.producto = producto;
        this.fechaExpiracion = fechaExpiracion;
    }

    public int getId() {
        return id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String toString() {
        return "Elemento de la cesta de: " + usuario + "\nProducto: " + producto + "\nFecha de expiración: " + fechaExpiracion;
    }
}
