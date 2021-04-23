package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Button;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaAdmin extends JFrame {

	private JPanel contentPane;
	private JTextField textCodigo;
	private JTextField textNombre;
	private JTextField textDesc;
	private JTextField textprecio;
	
	 Client cliente = ClientBuilder.newClient();
	 final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	 final WebTarget userTarget = appTarget.path("usuarios");
	 final WebTarget userAllTarget = userTarget.path("all");
	 
	 final WebTarget appTarget2 = cliente.target("http://localhost:8080/myapp");
	 final WebTarget productTarget = appTarget2.path("productos");
	 final WebTarget productAllTarget = productTarget.path("all");
	
	private JPanel panel1_botones;
	private JPanel panelVerResultados;
	private JList listVer_us_pro;
	private DefaultListModel<Usuario> modeloListUsuario = new DefaultListModel<>();
	private DefaultListModel<Producto> modeloListProducto = new DefaultListModel<>();
	private List<Usuario> usuarios;
	private List<Producto> productos;
	
	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblPrecio;
	private String aModificar = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdmin frame = new VentanaAdmin();
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
	public VentanaAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel1_botones = new JPanel();
		panel1_botones.setBounds(176, 97, 483, 266);
		contentPane.add(panel1_botones);
		panel1_botones.setLayout(null);
		
		panelVerResultados = new JPanel();
		panelVerResultados.setBounds(10, 10, 834, 433);
		contentPane.add(panelVerResultados);
		panelVerResultados.setLayout(null);
		panelVerResultados.setVisible(false);
		
		JButton btn_1_Usuarios = new JButton("Usuarios");
		btn_1_Usuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel1_botones.setVisible(false);
				panelVerResultados.setVisible(true);
				listVer_us_pro.setModel(modeloListUsuario);
				GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
				usuarios = userAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
				for(Usuario u : usuarios) {
					modeloListUsuario.addElement(u);
				}
				lblCodigo.setText("Usuario: ");
				lblNombre.setText("Contraseña: ");
				lblDescripcion.setVisible(false);
				lblPrecio.setVisible(false);
				textCodigo.setText("");
				textNombre.setText("");
				textDesc.setVisible(false);
				textprecio.setVisible(false);
				aModificar = "usuarios";
				
				
			}
		});
		btn_1_Usuarios.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_1_Usuarios.setBounds(10, 83, 178, 90);
		panel1_botones.add(btn_1_Usuarios);
		
		JButton btn_1_productos = new JButton("Productos");
		btn_1_productos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel1_botones.setVisible(false);
				panelVerResultados.setVisible(true);
				modeloListUsuario.removeAllElements();
				listVer_us_pro.setModel(modeloListProducto);
				GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
				productos = productAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
				for(Producto p : productos) {
					modeloListProducto.addElement(p);
				}
				lblCodigo.setText("Codigo: ");
				lblNombre.setText("Nombre: ");
				lblDescripcion.setVisible(true);
				lblPrecio.setVisible(true);
				textCodigo.setText("");
				textNombre.setText("");
				textDesc.setText("");
				textprecio.setText("");
				textDesc.setVisible(true);
				textprecio.setVisible(true);
				aModificar = "productos";
			}
		});
		btn_1_productos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_1_productos.setBounds(295, 83, 178, 90);
		panel1_botones.add(btn_1_productos);
		
		listVer_us_pro = new JList();
		listVer_us_pro.setBounds(10, 0, 335, 423);
		panelVerResultados.add(listVer_us_pro);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listVer_us_pro.getSelectedIndex() != -1 && aModificar.equals("usuarios")) {
					modeloListUsuario.remove(listVer_us_pro.getSelectedIndex());
					WebTarget userElimTarget = userTarget.path("elim").queryParam("nick", modeloListUsuario.get(listVer_us_pro.getSelectedIndex()).getUsername());
					userElimTarget.request(MediaType.APPLICATION_JSON);
				}
				else if(listVer_us_pro.getSelectedIndex() != -1 && aModificar.equals("productos")) {
					modeloListProducto.remove(listVer_us_pro.getSelectedIndex());
					WebTarget productElimTarget = productTarget.path("elim").queryParam("nombre", modeloListProducto.get(listVer_us_pro.getSelectedIndex()).getNombre());
					productElimTarget.request(MediaType.APPLICATION_JSON);
				}else {
					JOptionPane.showMessageDialog(null, "Seleccionar un elemento antes de eliminar");
				}
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEliminar.setBounds(404, 26, 131, 39);
		panelVerResultados.add(btnEliminar);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aModificar.equals("usuarios") && !textCodigo.getText().isEmpty() && !textNombre.getText().isEmpty()) {
					Usuario u = new Usuario(textCodigo.getText(), textNombre.getText());
					modeloListUsuario.addElement(u);
					WebTarget userRegTarget = userTarget.path("reg").queryParam("nick",textCodigo.getText()).queryParam("contaseña", textNombre.getText());
					userRegTarget.request(MediaType.APPLICATION_JSON);
				}
				else if(aModificar.equals("productos") && !textCodigo.getText().isEmpty() && !textNombre.getText().isEmpty() && !textDesc.getText().isEmpty() && !textprecio.getText().isEmpty()) {
					Producto p = new Producto(textCodigo.getText(), textNombre.getText(), textDesc.getText(), Double.parseDouble(textprecio.getText())); 
					modeloListProducto.addElement(p);
					WebTarget productRegTarget = productTarget.path("reg").queryParam("codigo",textCodigo.getText()).queryParam("nombre", textNombre.getText()).queryParam("descripcion", textDesc.getText()).queryParam("precio", Double.parseDouble(textprecio.getText()));
					productRegTarget.request(MediaType.APPLICATION_JSON);
				}
			}
		});
		btnAñadir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAñadir.setBounds(632, 26, 131, 39);
		panelVerResultados.add(btnAñadir);
		
		lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigo.setBounds(582, 105, 76, 32);
		panelVerResultados.add(lblCodigo);
		
		textCodigo = new JTextField();
		textCodigo.setColumns(10);
		textCodigo.setBounds(647, 114, 143, 19);
		panelVerResultados.add(textCodigo);
		
		textNombre = new JTextField();
		textNombre.setColumns(10);
		textNombre.setBounds(647, 174, 143, 19);
		panelVerResultados.add(textNombre);
		
		lblNombre = new JLabel("Nombre: ");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(582, 165, 76, 32);
		panelVerResultados.add(lblNombre);
		
		textDesc = new JTextField();
		textDesc.setColumns(10);
		textDesc.setBounds(647, 237, 143, 19);
		panelVerResultados.add(textDesc);
		
		lblDescripcion = new JLabel("Descripcion: ");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcion.setBounds(582, 228, 76, 32);
		panelVerResultados.add(lblDescripcion);
		
		textprecio = new JTextField();
		textprecio.setColumns(10);
		textprecio.setBounds(647, 312, 143, 19);
		panelVerResultados.add(textprecio);
		
		lblPrecio = new JLabel("Precio: ");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(582, 303, 76, 32);
		panelVerResultados.add(lblPrecio);
	}
}
