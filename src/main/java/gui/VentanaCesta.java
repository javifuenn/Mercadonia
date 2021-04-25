package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

import SA02.CestaResource;
import SA02.ProductosResource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;

import javax.swing.JButton;
import javax.swing.JList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VentanaCesta extends JFrame{
	
	private static List<Producto> productos;
	private static Usuario usuario;
	private static int cantidadproductosa;
	
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget productTarget = appTarget.path("productos");
    final WebTarget productAllTarget = productTarget.path("all");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCesta window = new VentanaCesta(usuario,cantidadproductosa);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaCesta(Usuario usuarioVerificado, int cantidadproductos) {
		usuario=usuarioVerificado;
		cantidadproductosa=cantidadproductos;
		initialize();
	}
	
	public List<Producto> busquedaProd(String producto){
		List<Producto> productos = null;
		GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
		if(producto.equals("")) {
			//productos = ProductosResource.getProductos();
	    	productos = productAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		}else {
			//productos = ProductosResource.getProductosNom(producto);
			WebTarget productNomTarget = productTarget.path("nom").queryParam("nombre",producto);
			productos = productNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
			
		}
		
		return productos;
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setBounds(100, 100, 670, 475);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JList list = new JList();
		
		final List<Producto> product=CestaResource.verProductosCesta(usuario);
		productos = CestaResource.verProductosCesta(usuario);
		final DefaultListModel<Producto> DLM = new DefaultListModel<>();
		for(Producto p: product) {	
			DLM.addElement(p);
			}
		list.setModel(DLM);
		
		JLabel lblCesta = new JLabel("CESTA");
		lblCesta.setHorizontalAlignment(SwingConstants.CENTER);
		lblCesta.setForeground(SystemColor.textHighlight);
		lblCesta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnComprar = new JButton("PAGAR");
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usenv =usuario;
				VentanaPago ventana = new VentanaPago(usenv,productos);
				double precio = 0.0;
				for(Producto p: product) {
					ventana.modelProducto.addElement(p);
					precio += p.getPrecio();
				}
				ventana.textPrecio.setText(String.valueOf(precio));
				ventana.setVisible(true);
				dispose();
			}
		});
		btnComprar.setBackground(new Color(135, 206, 250));
		
		JButton btnVaciarCesta = new JButton("VACIAR CESTA");
		btnVaciarCesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CestaResource.vaciarCesta(usuario);
				cantidadproductosa=0 ;
				final List<Producto> product=CestaResource.verProductosCesta(usuario);
				final DefaultListModel<Producto> DLM = new DefaultListModel<>();
				for(Producto p: product) {	
					DLM.addElement(p);
					}
				list.setModel(DLM);
			}
		});
		btnVaciarCesta.setBackground(new Color(135, 206, 250));
		
		JButton volver = new JButton("VOLVER");
		volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario us = usuario;
				VentanaBusqueda vb = new VentanaBusqueda(us,cantidadproductosa);
				vb.setVisible(true);
				setVisible(false);
			}
		});
		volver.setBackground(new Color(135, 206, 250));
		
		JButton btnNewButton = new JButton("Pedidos Realizados");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCompras ventana = new VentanaCompras(usuario,cantidadproductosa);
				ventana.setVisible(true);
				dispose();
				
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCesta, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(216)
							.addComponent(btnComprar, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnVaciarCesta, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(50)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(list, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(volver)
									.addPreferredGap(ComponentPlacement.RELATED, 369, Short.MAX_VALUE)
									.addComponent(btnNewButton)
									.addGap(65)))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addComponent(lblCesta, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnComprar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnVaciarCesta, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(volver)
						.addComponent(btnNewButton))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
}
