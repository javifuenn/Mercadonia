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
            	registro();
            }
        });
		btnRegistro.setBounds(106, 255, 89, 23);
		contentPane.add(btnRegistro);
		
		JButton btnLogin = new JButton("Login\r\n");
		btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean result = login();
            	if(result == true) {
            		JOptionPane.showMessageDialog(null, "Correcto"); //poner que hbara otra ventana
            	}else {
            		JOptionPane.showMessageDialog(null, "Error");
            	}
            }
        });
		btnLogin.setBounds(353, 255, 89, 23);
		contentPane.add(btnLogin);
	}
	
	public boolean login() {
		if(!textnombre_usuario.getText().equals("") && !textContraseña.getText().equals("")) {
			String usuario;
			String contraseña;
			usuario = textnombre_usuario.getText();
			contraseña = textContraseña.getText();
			boolean comp = false;
			List<Usuarios> usuarios = UsuariosResource.getUsuarios();
			for(Usuarios u : usuarios) {
				if(u.getUsername().equals(usuario) && u.getPassword().equals(contraseña)) {
					comp = true;
				}
				else {
					JOptionPane.showMessageDialog(null, "Usuario incorrecto");
					comp = false;
				}
			}
			if(comp == false) {
				return false;
			}else {
				return true;
			}
		}else {
			JOptionPane.showMessageDialog(null, "Introdir datos de acceso");
			return false;
		}
		
		
	}
	
	public void registro() {
		if(!textnombre_usuario.getText().equals("") && !textContraseña.getText().equals("")) {
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Usuarios usuario1 = new Usuarios(textnombre_usuario.getText(), textContraseña.getText());
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
