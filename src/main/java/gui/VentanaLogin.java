package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import SA02.UsuariosResource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JTextField textnombre_usuario;
	private JTextField textContraseña;
	private static Usuario usuarios;
	private JLabel lblNewLabel;
	
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget userTarget = appTarget.path("usuarios");
    final WebTarget userAllTarget = userTarget.path("all");

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
		setBounds(100, 100, 386, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblnombre_usuario = new JLabel("Nombre de usuario");
		lblnombre_usuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblnombre_usuario.setBounds(53, 70, 129, 24);
		contentPane.add(lblnombre_usuario);

		JLabel lblcontraseña = new JLabel("Contraseña");
		lblcontraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcontraseña.setBounds(53, 169, 140, 24);
		contentPane.add(lblcontraseña);

		textnombre_usuario = new JTextField();
		textnombre_usuario.setBounds(53, 113, 284, 24);
		contentPane.add(textnombre_usuario);
		textnombre_usuario.setColumns(10);

		textContraseña = new JTextField();

		// Passwordfield para la contraseï¿½a
		textContraseña = new JPasswordField();
		textContraseña.setColumns(10);
		textContraseña.setBounds(53, 204, 284, 24);
		contentPane.add(textContraseña);

		// Checkbox de mostrar contraseï¿½a
		final JCheckBox showpass = new JCheckBox("Mostrar Contrase\u00F1a");
		showpass.setForeground(SystemColor.textHighlight);
		showpass.setBackground(UIManager.getColor("Button.highlight"));
		showpass.setBounds(215, 241, 121, 23);
		getContentPane().add(showpass);
		showpass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (showpass.isSelected()) {
					((JPasswordField) textContraseña).setEchoChar((char) 0);
				} else {
					((JPasswordField) textContraseña).setEchoChar('*');
				}
			}
		});

		JLabel lblLoginMecradonia = new JLabel("LOGIN MERCADONIA");
		lblLoginMecradonia.setToolTipText("");
		lblLoginMecradonia.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginMecradonia.setForeground(SystemColor.textHighlight);
		lblLoginMecradonia.setFont(new Font("Leelawadee UI", Font.PLAIN, 24));
		lblLoginMecradonia.setBounds(10, 0, 350, 59);
		contentPane.add(lblLoginMecradonia);

		JButton cerrar = new JButton("CERRAR");
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		cerrar.setBackground(Color.LIGHT_GRAY);
		cerrar.setBounds(200, 351, 137, 36);
		contentPane.add(cerrar);

		JButton crear = new JButton("REGISTRARSE");
		crear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registro(textnombre_usuario.getText(), textContraseña.getText());
			}
		});
		crear.setBackground(Color.LIGHT_GRAY);
		crear.setBounds(56, 351, 137, 36);
		contentPane.add(crear);

		JButton loginbtn = new JButton("LOGIN");
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textnombre_usuario.getText().equals("admin") && textContraseña.getText().equals("admin")) {
					VentanaAdmin v = new VentanaAdmin();
					v.setVisible(true);
					dispose();
				}else {
					boolean result = login(textnombre_usuario.getText(), textContraseña.getText());
					if (result == true) {
						JOptionPane.showMessageDialog(null, "Usuario Correcto");
						VentanaBusqueda window = new VentanaBusqueda(usuarios);
						window.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Usuario incorrecto");
						JOptionPane.showMessageDialog(null, "Error");
					}
				}
			}
		});
		loginbtn.setForeground(Color.WHITE);
		loginbtn.setBackground(SystemColor.textHighlight);
		loginbtn.setBounds(53, 297, 284, 43);
		contentPane.add(loginbtn);

		lblNewLabel = new JLabel("BIENVENIDO!");
		lblNewLabel.setVisible(false);

		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 432, 350, 14);
		contentPane.add(lblNewLabel);
	}

	public boolean login(String usuario, String contraseña) {
		if (!usuario.equals("") && !contraseña.equals("")) {
			//usuarios = UsuariosResource.getUsuariosLogin(usuario);
			GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
			usuarios = userTarget.request(MediaType.APPLICATION_JSON).get(genericType).get(0);
			if (usuarios.getPassword().equals(contraseña) || !usuarios.equals(null)) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	public void registro(String usuario, String contraseña) {
		if (!usuario.equals("") && !contraseña.equals("")) {
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Usuario usuario1 = new Usuario(usuario, contraseña);
				pm.makePersistent(usuario1);
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
				lblNewLabel.setVisible(true);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Introducir datos de registro");
		}

	}
}
