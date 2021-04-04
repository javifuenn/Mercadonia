package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textnombre_usuario;
	private JTextField textContraseña;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblnombre_usuario = new JLabel("Nombre de usuario:");
		lblnombre_usuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblnombre_usuario.setBounds(55, 46, 140, 24);
		contentPane.add(lblnombre_usuario);
		
		JLabel lblcontraseña = new JLabel("Contraseña:");
		lblcontraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcontraseña.setBounds(55, 128, 140, 24);
		contentPane.add(lblcontraseña);
		
		textnombre_usuario = new JTextField();
		textnombre_usuario.setBounds(205, 48, 193, 24);
		contentPane.add(textnombre_usuario);
		textnombre_usuario.setColumns(10);
		
		textContraseña = new JTextField();
		textContraseña.setColumns(10);
		textContraseña.setBounds(205, 128, 193, 24);
		contentPane.add(textContraseña);
		
		JButton btnRegistro = new JButton("Registrar");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRegistro.setBounds(106, 255, 89, 23);
		contentPane.add(btnRegistro);
		
		JButton btnLogin = new JButton("Login\r\n");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnLogin.setBounds(353, 255, 89, 23);
		contentPane.add(btnLogin);
	}
	
	public void login(String nombre, String contraseña) {
		nombre = textnombre_usuario.getText();
		contraseña = textContraseña.getText();
		
	}
	
}
