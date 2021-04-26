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
import jdo.Usuario;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaVentaProductos extends JFrame{

	private JTable table;
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget productTarget = appTarget.path("productos");


	/**
	 * Create the application.
	 */
	public VentanaVentaProductos(final Usuario username) {
		initialize(username);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Usuario usuario) {
		
		setBounds(100, 100, 640, 523);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		WebTarget productUserTarget = productTarget.path("user").queryParam("usuario", usuario.getUsername());
		GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
		List<Producto> productos = productUserTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
	    tableModel.addColumn("Codigo");
	    tableModel.addColumn("Nombre");
	    tableModel.addColumn("Descripcion");
	    tableModel.addColumn("Precio");
	    tableModel.addColumn("Stock");
	    
	    for(Producto p: productos){
			tableModel.insertRow(0, new Object[] {p.getCodigo(), p.getNombre(), p.getDescripcion(), String.valueOf(p.getPrecio()), String.valueOf(p.getCantidad())});
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
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaAnadirProducto v = new VentanaAnadirProducto(usuario);
				v.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(480, 428, 116, 37);
		panel.add(btnNewButton);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaOpcion v2 = new VentanaOpcion(usuario);
				v2.setVisible(true);
				dispose();
			}
		});
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnVolver.setBounds(38, 428, 116, 37);
		panel.add(btnVolver);
	}
}
