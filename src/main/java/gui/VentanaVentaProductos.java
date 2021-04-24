package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;

public class VentanaVentaProductos {

	private JFrame frame;
	private JTable table;
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget productTarget = appTarget.path("productos");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaVentaProductos window = new VentanaVentaProductos("javi");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaVentaProductos(String usuario) {
		initialize(usuario);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String usuario) {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		WebTarget productUserTarget = productTarget.path("user").queryParam("usuario", usuario);
		GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
		List<Producto> productos = productUserTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
	    tableModel.addColumn("Codigo");
	    tableModel.addColumn("Nombre");
	    tableModel.addColumn("Descripcion");
	    tableModel.addColumn("Precio");
	    
	    for(Producto p: productos){
			tableModel.insertRow(0, new Object[] {p.getCodigo(), p.getNombre(), p.getDescripcion(), String.valueOf(p.getPrecio())});
		}
		
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(97, 57, 425, 351);
		panel.add(scroll);
		
		JLabel lblNewLabel = new JLabel("Tus Productos");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(156, 10, 283, 37);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(480, 428, 116, 37);
		panel.add(btnNewButton);
	}
}
