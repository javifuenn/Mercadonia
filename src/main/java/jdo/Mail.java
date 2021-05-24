package jdo;

import java.util.Date;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.mail.Address;
/**
 * Clase MAIL para el pago
 * @author Jon Ander
 *
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Mail {

	private Date date;
	private Address from;
	private String Subject;
	private String Content;
	private String Message;
	/**
	 * Contrctor vacio de la clase MAIL
	 */
	public Mail() {

	}
	
	/**
	 * Constructor de la clase MAIL
	 * @param date Date de MAIL
	 * @param from Address de MAIL
	 * @param Subject String de MAIL
	 * @param Content String de MAIL
	 * @param Message String de MAIL
	 */
	public Mail(Date date, Address from, String subject, String content, String message) {
		super();
		this.date = date;
		this.from = from;
		Subject = subject;
		Content = content;
		Message = message;
	}
	
	/**
	 * Devuelve la fecha de MAIL
	 * @return Date de date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Permite cambiar la fecha de MAIL
	 * @param date Date del nuevo correo
	 */
	public void setDate(Date date2) {
		this.date = date2;
	}
	/**
	 * Devuelve el emisor de MAIL
	 * @return Address de from
	 */
	public Address getFrom() {
		return from;
	}
	/**
	 * Permite cambiar la direccion de MAIL
	 * @param from2 Address del nuevo correo
	 */
	public void setFrom(Address from2) {
		this.from = from2;
	}
	/**
	 * Devuelve el sujeto de MAIL
	 * @return String de Subject
	 */
	public String getSubject() {
		return Subject;
	}
	/**
	 * Permite cambiar el sujeto de MAIL
	 * @param subject String del nuevo correo
	 */
	public void setSubject(String subject) {
		Subject = subject;
	}
	/**
	 * Devuelve el contenido de MAIL
	 * @return String de Content
	 */
	public String getContent() {
		return Content;
	}
	/**
	 * Permite cambiar el contenido de MAIL
	 * @param content String del nuevo correo
	 */
	public void setContent(String content) {
		Content = content;
	}
	/**
	 * Devuelve la mensaje de MAIL
	 * @return String de Message
	 */
	public String getMessage() {
		return Message;
	}
	/**
	 * Permite cambiar el mensaje  de MAIL
	 * @param message String del nuevo correo
	 */
	public void setMessage(String message) {
		Message = message;
	}

}
