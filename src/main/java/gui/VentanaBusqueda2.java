package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JTextField;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaBusqueda2 extends JFrame {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private JPanel contentPane;
	private JTextField textBuscador;
	private JTable table;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private DefaultTableModel tableModel_ofertas = new DefaultTableModel();
	private static Usuario usuario;
	private static int cantidad;
	private static List<Producto> productos;
	private JButton btnVerOfertas;
	
	private JPanel panel_ofertas;
	private JPanel panel;
	
	private JButton btnCesta;
	
	
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget productTarget = appTarget.path("productos");
	final WebTarget productAllTarget = productTarget.path("all");
	final WebTarget cestaTarget = appTarget.path("cesta");
	private JTable table_ofertas;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario u = new Usuario();
					VentanaBusqueda2 frame = new VentanaBusqueda2(u);
					frame.setVisible(true);
				} catch (Exception e) {
					LOGGER.severe(e.getMessage());
				}
			}
		});
	}
	
	public VentanaBusqueda2(Usuario usuarioValidado) {
		usuario = usuarioValidado;
		initialize();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaBusqueda2(Usuario usuarioValidado, int cantidadproductosa) {
		usuario = usuarioValidado;
		initialize();
	}
	
	public List<Producto> busquedaProd(String producto) {
		List<Producto> productos = null;

		if (producto.equals("") || btnVerOfertas.isSelected()) {
			GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {
			};
			productos = productAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		} else {
			WebTarget productNomTarget = productTarget.path("nom").queryParam("nombre", producto);
			GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {
			};
			productos = productNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		}

		return productos;

	}
	
	

	/**
	 * Create the frame.
	 * @param usuario2 
	 */
	private void initialize() {
		WebTarget contarTarget = cestaTarget.path("contar").queryParam("Usuario", usuario.getUsername());
		GenericType<Integer> genericType7 = new GenericType<Integer>() {};
		cantidad = contarTarget.request(MediaType.APPLICATION_JSON).get(genericType7);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(87, 111, 527, 293);
		contentPane.add(panel);
		panel.setVisible(true);
		
		panel_ofertas = new JPanel();
		panel_ofertas.setBounds(54, 98, 594, 326);
		contentPane.add(panel_ofertas);
		panel_ofertas.setVisible(false);
		
		JScrollPane scroll_ofertas = new JScrollPane((Component) null);
		scroll_ofertas.setBounds(87, 109, 527, 295);
		panel_ofertas.add(scroll_ofertas);
		
		table_ofertas = new JTable((TableModel) null);
		table_ofertas.setModel(tableModel_ofertas);
		tableModel_ofertas.addColumn("Codigo");
		tableModel_ofertas.addColumn("Nombre");
		tableModel_ofertas.addColumn("Descripcion");
		tableModel_ofertas.addColumn("Stock");
		tableModel_ofertas.addColumn("Precio");
		tableModel_ofertas.addColumn("Precio nuevo");
		scroll_ofertas.setViewportView(table_ofertas);
		
		JLabel lblBuscador = new JLabel("Buscador");
		lblBuscador.setBounds(44, 38, 91, 26);
		lblBuscador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblBuscador);
		
		textBuscador = new JTextField();
		textBuscador.setBounds(131, 44, 246, 19);
		contentPane.add(textBuscador);
		textBuscador.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				panel_ofertas.setVisible(false);
				for(int i = 0; i< tableModel.getRowCount(); i++) {
					tableModel.removeRow(i);
				}
				productos = busquedaProd(textBuscador.getText());
				for (Producto p : productos) {
					tableModel.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getDescripcion(), p.getCantidad(), p.getPrecio()});
				}
			}
		});
		btnBuscar.setBounds(443, 43, 85, 21);
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnBuscar);
		
		btnCesta = new JButton("Cesta : " + cantidad);
		btnCesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCesta window = new VentanaCesta(usuario);
				window.setVisible(true);
				setVisible(false);
			}
		});
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				VentanaLogin log = new VentanaLogin();
//				log.setVisible(true);
//				setVisible(false);
				VentanaOpcion v2 = new VentanaOpcion(usuario);
				v2.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(44, 468, 91, 32);
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnVolver);
		
		JButton btnAñadir = new JButton("Añadir a la cesta");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreusuario = usuario.getUsername();
				String nombreproducto = productos.get(table.getSelectedRow()).getNombre();
				WebTarget anadirTarget = cestaTarget.path("anadir").queryParam("Producto", nombreproducto).queryParam("Usuario", nombreusuario);
				GenericType<Boolean> genericType5 = new GenericType<Boolean>() {};
				boolean respuesta = anadirTarget.request(MediaType.APPLICATION_JSON).get(genericType5);

				WebTarget contarTarget = cestaTarget.path("contar").queryParam("Usuario", nombreusuario);
				GenericType<Integer> genericType7 = new GenericType<Integer>() {};
				cantidad = contarTarget.request(MediaType.APPLICATION_JSON).get(genericType7);
				btnCesta.setText("Cesta : " + cantidad);
				if (respuesta != true) {
					System.out.println("El producto no se añade a la cesta");
				}
			}
		});
		btnAñadir.setBounds(283, 468, 146, 32);
		btnAñadir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnAñadir);
		
		
		btnCesta.setBounds(564, 468, 91, 32);
		btnCesta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnCesta);
		
		JScrollPane scroll = new JScrollPane((Component) null);
		scroll.setBounds(87, 109, 527, 295);
		panel.add(scroll);
		
		table = new JTable((TableModel) null);
		table.setModel(tableModel);
		tableModel.addColumn("Codigo");
	    tableModel.addColumn("Nombre");
	    tableModel.addColumn("Descripcion");
	    tableModel.addColumn("Stock");
	    tableModel.addColumn("Precio");
		scroll.setViewportView(table);
		
		btnVerOfertas = new JButton("Ofertas del dia");
		btnVerOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				panel_ofertas.setVisible(true);
				
				for(int i = 0; i< tableModel_ofertas.getRowCount(); i++) {
					tableModel_ofertas.removeRow(i);
				}
				productos = busquedaProd(textBuscador.getText());
				for (Producto p : productos) {
					if(p.isEnOferta() == true) {
						tableModel_ofertas.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getDescripcion(), p.getCantidad(), p.getPrecio(), p.getPrecio()*0.6});
					}
					
				}
				
			}
		});
		btnVerOfertas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVerOfertas.setBounds(564, 43, 132, 21);
		contentPane.add(btnVerOfertas);
		
		
		
		
	}
}
