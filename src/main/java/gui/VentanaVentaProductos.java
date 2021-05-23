package gui;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;
import jdo.VentaProducto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import java.util.logging.Logger;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.util.List;

public class VentanaVentaProductos extends JFrame{
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private JTable table;
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget productTarget = appTarget.path("productos");
	final WebTarget ventasTarget = appTarget.path("ventasproductos");


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


		final JButton btnVendidos = new JButton("Mostrar vendidos");

		btnVendidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, 0).toString();

				WebTarget ventasUserTarget = ventasTarget.path("cod").queryParam("codigo", value);
				GenericType<List<VentaProducto>> genericVentas = new GenericType<List<VentaProducto>>() {};
				List<VentaProducto> prod = ventasUserTarget.request(MediaType.APPLICATION_JSON).get(genericVentas);
				String prod_name = table.getModel().getValueAt(row, 1).toString();
				VentanaNumVentas vnv = new VentanaNumVentas(prod.get(0), prod_name);
				vnv.setVisible(true);
			}
		});




		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(38, 57, 425, 351);
		panel.add(scroll);
		
		JLabel lblNewLabel = new JLabel("Tus Productos");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(93, 10, 283, 37);
		panel.add(lblNewLabel);
		
		btnVendidos.setEnabled(false);
		btnVendidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnVendidos.setBounds(473, 102, 143, 27);
		panel.add(btnVendidos);
		
		
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

		final JButton btnCrearOferta = new JButton("Crear oferta");
		btnCrearOferta.setEnabled(false);
		
		final JButton btnGenFactura = new JButton("Generar reporte");
		btnGenFactura.setEnabled(false);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				btnGenFactura.setEnabled(true);
				btnCrearOferta.setEnabled(true);
				btnVendidos.setEnabled(true);
			}
		});
		btnGenFactura.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnGenFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Document document = new Document();
				try {
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
							"Producto " + table.getModel().getValueAt(row, 0).toString() + ".pdf"));
					document.open();
					document.add(new Paragraph("MERCADONIA - Producto"));
					document.add(new Paragraph("Producto: " + table.getModel().getValueAt(row, 1).toString()));
					document.add(new Paragraph("Descripcion: " + table.getModel().getValueAt(row, 2).toString()));
					document.add(new Paragraph("Precio: " + table.getModel().getValueAt(row, 3).toString() + "€"));
					document.add(new Paragraph("Stock: " + table.getModel().getValueAt(row, 4).toString()));
					document.close();
					writer.close();
					JOptionPane.showMessageDialog(null, "Archivo creado");
				} catch (DocumentException ex) {
					LOGGER.severe(ex.getMessage());
				} catch (FileNotFoundException e2) {
					LOGGER.severe(e2.getMessage());
				}

			}
		});
		btnGenFactura.setBounds(220, 418, 185, 27);
		panel.add(btnGenFactura);
		
		
		btnCrearOferta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCrearOferta.setBounds(490, 55, 106, 21);
		panel.add(btnCrearOferta);
		
		
		btnCrearOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				String descuento = JOptionPane.showInputDialog(null, "Porcentaje de descuento:", "%");
				int porc = descuento.indexOf('%');
				StringBuilder sb = new StringBuilder(descuento);
				sb.setCharAt(porc, ' ');
				descuento = sb.toString();
				double porcentaje = Double.parseDouble(descuento) * 0.01;
				if (!(descuento.equals(" "))) {
					Producto p = new Producto();
					p.setCodigo(table.getModel().getValueAt(row, 0).toString());
					p.setNombre(table.getModel().getValueAt(row, 1).toString());
					p.setDescripcion(table.getModel().getValueAt(row, 2).toString());
					p.setPrecio(Double.parseDouble(table.getModel().getValueAt(row, 3).toString()) * porcentaje);
					p.setUsuario("");
					p.setCantidad(Integer.parseInt(table.getModel().getValueAt(row, 4).toString()));

					WebTarget productInsTarget = productTarget.path("modifprecio");
					productInsTarget.request().post(Entity.entity(p, MediaType.APPLICATION_JSON));
					JOptionPane.showMessageDialog(null, "Descuento aplicado!");
				} else {
					JOptionPane.showMessageDialog(null, "Introduce un porcentaje válido");
				}
			}
		});
			
	}
}
