package jdo;

import javax.jdo.annotations.PersistenceCapable;


/**
 * Clase CUPON para un descuento
 * @author Sergio
 *
 */
@PersistenceCapable(detachable = "true")
public class Cupon {
	
	private String textoCupon;
	private int porcentajeDescuento;
	private String usuario;
	
	/**
	 * Constructor vacio de la clase cupon
	 */
	public Cupon() {
		
	}
	/**
	 * Constructor de la clase CUPON
	 * @param textoCupon String con el texto del CUPON
	 * @param porcentajeDescuento int con el porcentaje de descuento del CUPON
	 * @param usuario String con el nombre del USUARIO del CUPON
	 */
	public Cupon(String textoCupon, int porcentajeDescuento, String usuario) {
		this.textoCupon = textoCupon;
		this.porcentajeDescuento = porcentajeDescuento;
		this.usuario = usuario;
	}
	/**
	 * Devuelve el texto del CUPON
	 * @return String con el texto del CUPON
	 */
	public String getTextoCupon() {
		return textoCupon;
	}
	/**
	 * Devuelve el texto del CUPON
	 * @param textoCupon String con el texto del CUPON
	 */
	public void setTextoCupon(String textoCupon) {
		this.textoCupon = textoCupon;
	}
	/**
	 * Devuelve el porcentaje de descuento del CUPON 
	 * @return int con el porcentaje de descuento del CUPON
	 */
	public int getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	/**
	 * Permite cambiar el porcentaje de descuento del CUPON
	 * @param porcentajeDescuento int con el nuevo porcentaje de descuento del CUPON
	 */
	public void setPorcentajeDescuento(int porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	/**
	 * Devuelve el USUARIO dueño del CUPON
	 * @return String del USUARIO dueño del CUPON
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * Permite cambair el USUARIO dueño del CUPON
	 * @param usuario String del nuevo USUARIO dueño del CUPON
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * toString de la clase CUPON
	 */
	@Override
	public String toString() {
		return "Cupon [textoCupon=" + textoCupon + ", porcentajeDescuento=" + porcentajeDescuento + ", usuario="
				+ usuario + "]";
	}

}
