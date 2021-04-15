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
		
		if(producto.equals("")) {
			productos = ProductosResource.getProductos();
		}else {
			productos = ProductosResource.getProductosNom(producto);
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
		final DefaultListModel<Producto> DLM = new DefaultListModel<>();
		for(Producto p: product) {	
			DLM.addElement(p);
			}
		list.setModel(DLM);
		
		JLabel lblCesta = new JLabel("CESTA");
		lblCesta.setHorizontalAlignment(SwingConstants.CENTER);
		lblCesta.setForeground(SystemColor.textHighlight);
		lblCesta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnComprar = new JButton("COMPRAR");
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
								.addComponent(volver)
								.addComponent(list, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE))))
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
					.addComponent(volver)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
}
