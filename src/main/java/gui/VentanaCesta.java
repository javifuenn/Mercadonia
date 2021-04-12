package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

import SA02.CestaResource;
import SA02.ProductosResource;
import jdo.Cesta;
import jdo.Producto;
import jdo.Usuarios;

import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCesta extends JFrame{
	private static List<Producto> productos;
	private static Usuarios usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCesta window = new VentanaCesta(usuario);
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
	public VentanaCesta(Usuarios usuarioVerificado) {
		usuario=usuarioVerificado;
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
		
		setBounds(100, 100, 670, 402);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JList list = new JList();
		
		List<Producto> product=CestaResource.verProductosCesta(usuario);
		DefaultListModel<Producto> DLM = new DefaultListModel<>();
		for(Producto p: product) {	
			DLM.addElement(p);
			}
		list.setModel(DLM);
		
		JButton btnNewButton_2 = new JButton("Comprar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(50)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(281)
							.addComponent(btnNewButton_2)))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(81)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_2)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
}
