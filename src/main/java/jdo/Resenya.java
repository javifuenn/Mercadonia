package jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Clase Resenya donde un Producto puede tener la Resenya de un Usuario que haya comprado el Producto
 * @author Unai
 *
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Resenya {
	
	String producto;
	String usuario;
	int calificacion;
	String opinion;
	/**
	 * Constructor de la clase Resenya
	 * @param producto nombre del Producto de la Resenya
	 * @param usuario nombre del Usuario de la Resenya
	 * @param calificacion puntos del 1 (Muy Mal) al 5 (Muy Bien) de la Resenya
	 * @param opinion String con la opinion del Usuario respecto al Producto
	 */
	public Resenya(String producto, String usuario, int calificacion, String opinion) {
		this.producto = producto;
		this.usuario = usuario;
		this.calificacion = calificacion;
		this.opinion = opinion;
	}
	/**
	 * Devuelve el nombre del Producto de la Resenya
	 * @return String con el nombre del Producto
	 */
	public String getProducto() {
		return producto;
	}
	/**
	 * Cambia el nombre del Producto de la Resenya
	 * @param producto String con el nombre del Producto a introducir en la Resenya
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}
	/**
	 * Devuelve el nombre del Usuaeio que ha creado la Resenya
	 * @return String con el nombre del Usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * Cambia el nombre del Usuario que a creado la Resenya
	 * @param usuario String con el nombre del Usuario a introducir en la Resenya
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * Devuelve la calificacion en int del 1 al 5 del Usuario a un Producto
	 * @return int con la calificacion de la Resenya
	 */
	public int getCalificacion() {
		return calificacion;
	}
	/**
	 * Cambia la calificacion de un Producto
	 * @param calificacion int con la calificacion a introducir en la Resenya
	 */
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	/**
	 * Devuelve la opinion en Strinmg que tiene un Usuario de un Producto
	 * @return String con la opinion del Usuario
	 */
	public String getOpinion() {
		return opinion;
	}
	/**
	 * Cambia la opinion de un Usuario rtespecto a un Producto
	 * @param opinion String con la opinion a introducir en la Resenya
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	/**
	 * To String de Resenya
	 */
	@Override
	public String toString() {
		return "Reseña [producto=" + producto + ", usuario=" + usuario + ", calificacion =" + calificacion + 
				", opinion =" + opinion + "]";
	}
	

}
