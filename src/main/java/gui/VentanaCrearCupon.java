package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import sa02.MandarMail;
import sa02.UsuariosResource;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class VentanaCrearCupon extends JFrame {

	public static JTextField nombrecupontxt;
	public static JTextField porcentajecupontxt;

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
					e.printStackTrace();
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
		setBounds(100, 100, 645, 310);
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
		cerrar.setBounds(503, 220, 89, 23);
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
				
			}
		});
		recibircodigo.setBounds(185, 198, 234, 45);
		getContentPane().add(recibircodigo);

		JButton Volverbtn = new JButton("VOLVER");
		Volverbtn.setBackground(SystemColor.controlShadow);
		Volverbtn.setBounds(10, 220, 89, 23);
		getContentPane().add(Volverbtn);
		Volverbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaAdmin va =  new VentanaAdmin();;
				setVisible(false);
				va.setVisible(true);
			}
		});
		
	}}


