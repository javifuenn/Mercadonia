package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import SA02.UsuariosResource;
import jdo.Producto;
import jdo.Usuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextField;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.swing.JButton;

public class VentanaLogin extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JTextField textnombre_usuario;
	private JTextField textContraseña;
	private static Usuarios usuarios;

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
            	registro(textnombre_usuario.getText(), textContraseña.getText());
            }
        });
		btnRegistro.setBounds(106, 255, 89, 23);
		contentPane.add(btnRegistro);
		
		JButton btnLogin = new JButton("Login\r\n");
		btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean result = login(textnombre_usuario.getText(), textContraseña.getText());
            	if(result == true) {
            		JOptionPane.showMessageDialog(null, "Usuario Correcto");
            		VentanaBusqueda window = new VentanaBusqueda(usuarios);
					window.setVisible(true);
					dispose();
            	}else {
            		JOptionPane.showMessageDialog(null, "Usuario incorrecto");
            		JOptionPane.showMessageDialog(null, "Error");
            	}
            }
        });
		btnLogin.setBounds(353, 255, 89, 23);
		contentPane.add(btnLogin);
	}
	
	public boolean login(String usuario, String contraseña) {
		if(!usuario.equals("") && !contraseña.equals("")) {
			usuarios = UsuariosResource.getUsuariosLogin(usuario);
			if(usuarios.getPassword().equals(contraseña) || !usuarios.equals(null)) {
				return true;	
			}else {
				return false;
			}
		}else
			return false;
	}
	
	public void registro(String usuario, String contraseña) {
		if(!usuario.equals("") && !contraseña.equals("")) {
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Usuarios usuario1 = new Usuarios(usuario, contraseña);
				pm.makePersistent(usuario1);
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		}else {
			JOptionPane.showMessageDialog(null, "Introducir datos de registro");
		}
		
		
	}
}
