package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;


import javax.swing.SwingConstants;
import javax.swing.UIManager;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Cupon;
import jdo.Producto;
import sa02.MandarMail;
import sa02.UsuariosResource;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class VentanaCrearCupon extends JFrame {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static JTextField nombrecupontxt;
	public static JTextField porcentajecupontxt;
	private JTextField usuariocupontxt;
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget cupontarget = appTarget.path("cupones");
	final WebTarget anadirtarget = cupontarget.path("anadir");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCrearCupon cupon = new VentanaCrearCupon();
					cupon.setVisible(false);
				} catch (Exception e) {
					LOGGER.severe(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaCrearCupon() {
		initialize();
	}

	/**
	 * Inicializamos todos los elementos de la ventana crearcuenta los cuales
	 * separaremos mas adelante mediante mas usuarios
	 */

	private void initialize() {

		/**
		 * Valores propios de la ventana JFRAME
		 */
		setBounds(100, 100, 646, 357);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		// Inizializamos todos los Jlabel de dentro de la ventana y los retocamos para
		// que sea mas bonitos visualmente hablando
		JLabel titulo = new JLabel("Cupones");
		titulo.setToolTipText("");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setForeground(SystemColor.textHighlight);
		titulo.setFont(new Font("Leelawadee UI", Font.PLAIN, 24));
		titulo.setBounds(226, 11, 165, 45);
		getContentPane().add(titulo);

		final JLabel nombre = new JLabel("Nombre Cupon");
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nombre.setBounds(33, 107, 104, 29);
		getContentPane().add(nombre);

		final JLabel apellido = new JLabel("Descuento");
		apellido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		apellido.setBounds(325, 107, 97, 29);
		getContentPane().add(apellido);

		// Los JTextField
		nombrecupontxt = new JTextField();
		nombrecupontxt.setBounds(147, 100, 138, 45);
		getContentPane().add(nombrecupontxt);
		nombrecupontxt.setColumns(10);

		porcentajecupontxt = new JTextField();
		porcentajecupontxt.setBounds(432, 99, 131, 45);
		getContentPane().add(porcentajecupontxt);
		porcentajecupontxt.setColumns(10);

		// Jbutton cerrar. Simplemente cierra la aplicacion
		JButton cerrar = new JButton("CERRAR");
		cerrar.setBackground(SystemColor.controlShadow);
		cerrar.setBounds(505, 263, 89, 23);
		getContentPane().add(cerrar);
		cerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		JButton recibircodigo = new JButton("Crear cupon");
		recibircodigo.setBackground(SystemColor.textHighlight);
		recibircodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

//				WebTarget anadir = cupontarget.path("anadir");
//				Cupon cupon = new Cupon();
//				cupon.setPorcentajeDescuento(Integer.parseInt(porcentajecupontxt.getText()));
//				cupon.setTextoCupon(nombrecupontxt.getText());
//				cupon.setUsuario(usuariocupontxt.getText());
//				anadir.request().post(Entity.entity(cupon, MediaType.APPLICATION_JSON));
//				
				Cupon cuponn = new Cupon(nombrecupontxt.getText(), Integer.parseInt(porcentajecupontxt.getText()),
						usuariocupontxt.getText());
				WebTarget anadirr = cupontarget.path("anadir");
				anadirr.request().post(Entity.entity(cuponn, MediaType.APPLICATION_JSON));

			}
		});
		recibircodigo.setBounds(188, 240, 234, 45);
		getContentPane().add(recibircodigo);

		JButton Volverbtn = new JButton("VOLVER");
		Volverbtn.setBackground(SystemColor.controlShadow);
		Volverbtn.setBounds(10, 263, 89, 23);
		getContentPane().add(Volverbtn);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsuario.setBounds(150, 179, 104, 29);
		getContentPane().add(lblUsuario);

		usuariocupontxt = new JTextField();
		usuariocupontxt.setColumns(10);
		usuariocupontxt.setBounds(264, 172, 138, 45);
		getContentPane().add(usuariocupontxt);

		JButton btnNewButton = new JButton("Borrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cupon = nombrecupontxt.getText();
				System.out.println(cupon);
				Cupon cup = new Cupon();

				WebTarget buscarcupon = cupontarget.path("buscar1").queryParam("nombrecupon", cupon);
				GenericType<Cupon> genericType = new GenericType<Cupon>() {
				};
				cup = buscarcupon.request(MediaType.APPLICATION_JSON).get(genericType);

				WebTarget productElimTarget = cupontarget.path("borrar");
				productElimTarget.request().post(Entity.entity(cup, MediaType.APPLICATION_JSON));

			}
		});
		btnNewButton.setBounds(505, 229, 89, 23);
		getContentPane().add(btnNewButton);
		Volverbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaAdmin va = new VentanaAdmin();
				;
				setVisible(false);
				va.setVisible(true);
			}
		});

	}
}
