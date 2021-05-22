package jdo;

import javax.jdo.annotations.PersistenceCapable;
import java.util.Date;


/**
 * Clase Oferta para crear ofertas en una determinada fecha
 * @author Javi
 *
 */
@PersistenceCapable(detachable = "true")
public class Oferta {

    private String producto;
    private double precio;
    private Date fecha;

    /**
     * Constructor vacio de la clase oferta
     */
    public Oferta() {}
    /**
     * Constructor de la clase Oferta
     * @param producto String del producto asociado
     * @param precio double con el precio del producto
     * @param fecha Date de la oferta
     */
    public Oferta(String producto, double precio, Date fecha) {
        this.producto = producto;
        this.precio = precio;
        this.fecha = fecha;
    }
    /**
     * Devuelve el producto al que va asociada la oferta
     * @return producto con el nombre del producto
     */
    public String getProducto() {
        return producto;
    }
    /**
     * Establecce el producto al que va asociada la oferta
     * @param producto String con el nombre del producto
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }
    /**
     * Devuelve el precio del producto asociado a la oferta
     * @return precio double con el precio del producto asociado a la oferta
     */
    public double getPrecio() {
        return precio;
    }
    /**
     * Establece el precio del producto asociado a la oferta
     * @param precio double del precio del producto asociado a la oferta
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    /**
     * Devuelve la fecha en la que se puede aprovechar la oferta
     * @return Date con la fecha en la que se puede aprovechar la oferta
     */
    public Date getFecha() {
        return fecha;
    }
    /**
     * Establece la fecha en la que se puede aprovechar la oferta
     * @param fecha Date con la fecha en la que se puede aprovechar la oferta
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    /**
     * toString de la clase Oferta
     */
    @Override
    public String toString() {
        return "Oferta [producto=" + producto + ", precio=" + precio + ", fecha="
                + fecha + "]";
    }

}
