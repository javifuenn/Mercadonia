package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
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
import java.util.ArrayList;
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
	private Producto p;
	private Usuario u;
	
	private static Usuario usuario;
	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblPrecio;
	private String aModificar = null;
	private JButton btnActualizar;

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
					System.out.println(modeloListUsuario.getSize());
				}
				lblCodigo.setText("Usuario: ");
				lblNombre.setText("Passw: ");
				lblDescripcion.setText("Email: ");
				lblPrecio.setVisible(false);
				textCodigo.setText("");
				textNombre.setText("");
				textDesc.setText("");
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
				lblDescripcion.setText("Descripcion: ");
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
		
		JButton btnNewButton = new JButton("Cupones");
		btnNewButton.setBounds(198, 208, 89, 23);
		panel1_botones.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaCrearCupon cc = new VentanaCrearCupon();
				cc.setVisible(true);
			}});
		
		panelVerResultados = new JPanel();
		panelVerResultados.setBounds(10, 10, 834, 433);
		contentPane.add(panelVerResultados);
		panelVerResultados.setLayout(null);
		panelVerResultados.setVisible(false);
		
		listVer_us_pro = new JList();
		listVer_us_pro.setBounds(10, 0, 335, 423);
		panelVerResultados.add(listVer_us_pro);
		
		final JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(listVer_us_pro.getSelectedIndex());
				if(listVer_us_pro.getSelectedIndex() != -1 && aModificar.equals("usuarios")) {
					WebTarget userElimTarget = userTarget.path("elim");
					List<String> usuarioL = new ArrayList<>(); 
					usuarioL.add(modeloListUsuario.get(listVer_us_pro.getSelectedIndex()).getUsername());
					userElimTarget.request().post(Entity.entity(usuarioL, MediaType.APPLICATION_JSON));
					modeloListUsuario.remove(listVer_us_pro.getSelectedIndex());
					
				}
				else if(listVer_us_pro.getSelectedIndex() != -1 && aModificar.equals("productos")) {
					WebTarget productElimTarget = productTarget.path("elim");
					List<String> productoL = new ArrayList<>(); 
					productoL.add(modeloListProducto.get(listVer_us_pro.getSelectedIndex()).getNombre());
					productElimTarget.request().post(Entity.entity(productoL, MediaType.APPLICATION_JSON));
					modeloListProducto.remove(listVer_us_pro.getSelectedIndex());
				}else {
					JOptionPane.showMessageDialog(null, "Seleccionar un elemento antes de eliminar");
				}
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEliminar.setBounds(404, 26, 131, 39);
		panelVerResultados.add(btnEliminar);
		
		final JButton btnAñadir = new JButton("Añadir");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aModificar.equals("usuarios") && !textCodigo.getText().isEmpty() && !textNombre.getText().isEmpty()) {
					Usuario u = new Usuario(textCodigo.getText(), textNombre.getText(), "email-null");
					modeloListUsuario.addElement(u);
					
					WebTarget userRegTarget = userTarget.path("reg");
					List<String> usuarioL = new ArrayList<>(); 
					usuarioL.add(textCodigo.getText());
					usuarioL.add(textNombre.getText());
					usuarioL.add(textDesc.getText());
					userRegTarget.request().post(Entity.entity(usuarioL, MediaType.APPLICATION_JSON));
				}
				else if(aModificar.equals("productos") && !textCodigo.getText().isEmpty() && !textNombre.getText().isEmpty() && !textDesc.getText().isEmpty() && !textprecio.getText().isEmpty()) {
					Producto p = new Producto(textNombre.getText(), textDesc.getText(), Double.parseDouble(textprecio.getText()), "admin",5); //ARREGLA EL 5
					modeloListProducto.addElement(p);
					
					WebTarget productInsTarget = productTarget.path("ins");
					List<String> productoL = new ArrayList<>(); 
					productoL.add(p.getNombre());
					productoL.add(p.getDescripcion());
					productoL.add(textprecio.getText());
					productoL.add(p.getUsuario());
					productoL.add(String.valueOf(p.getCantidad()));
					productInsTarget.request().post(Entity.entity(productoL, MediaType.APPLICATION_JSON));
				}else {
					JOptionPane.showMessageDialog(null, "Rellenar todos los campos");
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
		lblDescripcion.setBounds(558, 228, 100, 32);
		panelVerResultados.add(lblDescripcion);
		
		textprecio = new JTextField();
		textprecio.setColumns(10);
		textprecio.setBounds(647, 312, 143, 19);
		panelVerResultados.add(textprecio);
		
		lblPrecio = new JLabel("Precio: ");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(582, 303, 76, 32);
		panelVerResultados.add(lblPrecio);
	
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(listVer_us_pro.getSelectedIndex() != -1 && aModificar.equals("usuarios")) {
					u = usuarios.get(listVer_us_pro.getSelectedIndex());
					textCodigo.setText(u.getUsername());
					textNombre.setText(u.getPassword());
					textDesc.setText(u.getEmail());
					btnActualizar.setVisible(true);
					btnEliminar.setVisible(false);
					btnAñadir.setVisible(false);
					
				}
				else if(listVer_us_pro.getSelectedIndex() != -1 && aModificar.equals("productos")) {
					p=productos.get(listVer_us_pro.getSelectedIndex());
					textCodigo.setText(p.getCodigo());
					textNombre.setText(p.getNombre());
					textDesc.setText(p.getDescripcion());
					String precio = Double.toString(p.getPrecio());
					textprecio.setText(precio);
					btnActualizar.setVisible(true);
					btnEliminar.setVisible(false);
					btnAñadir.setVisible(false);
					
					
				}else {
					JOptionPane.showMessageDialog(null, "Seleccionar un elemento antes de eliminar");
				}
				
			}
		});
		
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnModificar.setBounds(659, 371, 131, 39);
		panelVerResultados.add(btnModificar);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aModificar.equals("productos")) {
					WebTarget productUpdateTarget = productTarget.path("update");
					p.setNombre(textNombre.getText());
					p.setDescripcion(textDesc.getText());
					double preci = Double.parseDouble(textprecio.getText());
					p.setPrecio(preci);
					productUpdateTarget.request().post(Entity.entity(p, MediaType.APPLICATION_JSON));
					btnActualizar.setVisible(false);
					btnEliminar.setVisible(true);
					btnAñadir.setVisible(true);
				}else {
					WebTarget userUpdateTarget = userTarget.path("update");
					u.setPassword(textNombre.getText());
					u.setEmail(textDesc.getText());
					userUpdateTarget.request().post(Entity.entity(u, MediaType.APPLICATION_JSON));
					btnActualizar.setVisible(false);
					btnEliminar.setVisible(true);
					btnAñadir.setVisible(true);
				}
				
				
			}
		});
		btnActualizar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnActualizar.setBounds(502, 371, 131, 39);
		panelVerResultados.add(btnActualizar);
	}
}
