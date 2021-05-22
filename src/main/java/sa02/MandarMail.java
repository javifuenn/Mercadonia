package sa02;

import java.util.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

import jdo.Producto;

public class MandarMail {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static int codigo;
	public static String mVerificado;
	public static String cuentamail = "mercadoniadeusto@gmail.com";
	public static String contrasenya = "doniadonia";

	public static int recibircodigo(String recipiente) {
		Properties propiedades = new Properties();

		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.starttls.enable", "true");
		propiedades.put("mail.smtp.host", "smtp.gmail.com");
		propiedades.put("mail.smtp.port", "587");

		Session sesion = Session.getInstance(propiedades, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(cuentamail, contrasenya);
			}

		});

		Message mensaje = prepararMensajeCodigo(sesion, "mercadoniadeusto@gmail.com", recipiente);

		try {
			Transport.send(mensaje);
			mVerificado = "Mensaje enviado";
			System.out.println(mVerificado);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			LOGGER.severe(e.getMessage());

		}
		return codigo;

	}

	public static int mandarStockAviso(String recipiente,Producto producto) {
		Properties propiedades = new Properties();

		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.starttls.enable", "true");
		propiedades.put("mail.smtp.host", "smtp.gmail.com");
		propiedades.put("mail.smtp.port", "587");

		Session sesion = Session.getInstance(propiedades, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(cuentamail, contrasenya);
			}

		});

		Message mensaje = prepararMensajeRespuesta(sesion, cuentamail, recipiente, producto);

		try {
			Transport.send(mensaje);
			mVerificado = "Mensaje enviado";
			System.out.println(mVerificado);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			LOGGER.severe(e.getMessage());
		}
		return codigo;

	}

	
	private static Message prepararMensajeCodigo(Session sesion, String cuentamail, String recipiente) {

		Message mensaje = new MimeMessage(sesion);
		try {
			mensaje.setFrom(new InternetAddress(cuentamail));
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(recipiente));
			mensaje.setSubject("Codigo de verificacion");
			codigo = (int) (Math.random() * 8999) + 1000;
			mensaje.setText("Tu cogido de verificacion es : " + codigo);
			return mensaje;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			LOGGER.severe(e.getMessage());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			LOGGER.severe(e.getMessage());
		}

		return null;

	}

	private static Message prepararMensajeRespuesta(Session sesion, String cuentamail, String recipiente,
			Producto producto) {

		Message mensaje = new MimeMessage(sesion);
		try {
			mensaje.setFrom(new InternetAddress(cuentamail));
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(recipiente));
			mensaje.setSubject("Aviso de falta de stock del producto "+ producto.getNombre());
			mensaje.setText("El producto "+ producto.getNombre() + " solo tiene "+ producto.getCantidad() + " unidades disponibles.");
			return mensaje;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			LOGGER.severe(e.getMessage());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			LOGGER.severe(e.getMessage());
		}

		return null;

	}

	

}
