package SA02;

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

public class MandarMail {

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
			e.printStackTrace();

		}
		return codigo;

	}

	public static int mandarRespuesta(String recipiente, String mensajecuerpo) {
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

		Message mensaje = prepararMensajeRespuesta(sesion, cuentamail, recipiente, mensajecuerpo);

		try {
			Transport.send(mensaje);
			mVerificado = "Mensaje enviado";
			System.out.println(mVerificado);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codigo;

	}

	public static int mandarmail(String texto, String asunto, int idusuario) {
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

		Message mensaje = prepararMensajeConsulta(sesion, cuentamail, texto, asunto, idusuario);

		try {
			Transport.send(mensaje);
			mVerificado = "Mensaje enviado";
			System.out.println(mVerificado);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	private static Message prepararMensajeRespuesta(Session sesion, String cuentamail, String recipiente,
			String mensajecuerpo) {

		Message mensaje = new MimeMessage(sesion);
		try {
			mensaje.setFrom(new InternetAddress(cuentamail));
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(recipiente));
			mensaje.setSubject("MisMarcadoresWaterpolo ha respondido tu consulta");
			mensaje.setText(mensajecuerpo);
			return mensaje;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	private static Message prepararMensajeConsulta(Session sesion, String cuentamail, String texto, String asunto,
			int idusuario) {
		String recipiente = "mismarcadoreswaterpolo.deusto@gmail.com";
		Message mensaje = new MimeMessage(sesion);
		try {
			mensaje.setFrom(new InternetAddress(cuentamail));
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(recipiente));
			mensaje.setSubject("Consulta de ID: " + idusuario + ". Asunto : " + asunto);
			mensaje.setText(texto);
			return mensaje;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
