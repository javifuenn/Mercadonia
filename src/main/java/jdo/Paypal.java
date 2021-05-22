package jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Clase PAYPAL para el pago
 * @author Sergio
 *
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Paypal {
	
	private String correo;
	private String contrasena;
	
	/**
	 * Contrctor vacio de la clase PAYPAL
	 */
	public Paypal() {
		
	}
	/**
	 * Constructor de la clase PAYPAL
	 * @param correo String del correo de PAYPAL
	 * @param contrasena String de la contraseña de PAYPAL
	 */
	public Paypal(String correo, String contrasena) {
		super();
		this.correo = correo;
		this.contrasena = contrasena;
	}
	/**
	 * Devuelve el correo de PAYPAL
	 * @return String del correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * Permite cambiar el correo de PAYPAL
	 * @param correo String del nuevo correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * Devuelve la contrasela de PAYPAL
	 * @return String con la contraseña nueva
	 */
	public String getContrasena() {
		return contrasena;
	}
	/**
	 * Permite cambiar la contraseña de PAYPAL
	 * @param contrasena String con la nueva contraseña
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	/**
	 * toString de PAYPAL
	 */
	@Override
	public String toString() {
		return "Paypal [correo=" + correo + ", contrasena=" + contrasena + "]";
	}
	
	
}
