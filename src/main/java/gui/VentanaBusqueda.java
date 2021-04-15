package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
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
import jdo.Usuario;

import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VentanaBusqueda extends JFrame{
	public VentanaBusqueda() {
	}

	private JTextField textField;
	private static List<Producto> productos;
	private static Usuario usuario;
	private static Cesta cesta;
	private static int cantidadproductos = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBusqueda window = new VentanaBusqueda(usuario);
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
	
	public VentanaBusqueda(Usuario usuarioValidado) {
		usuario=usuarioValidado;
		initialize();
	}
	public VentanaBusqueda(Usuario usuarioValidado, int cantidadproductosa) {
		usuario=usuarioValidado;
		cantidadproductos=cantidadproductosa;
		System.out.println(cantidadproductosa +" fff "+ cantidadproductos);
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
		
		setBounds(100, 100, 670, 573);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		final JList list = new JList();
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				productos = busquedaProd(textField.getText());
				DefaultListModel<Producto> DLM = new DefaultListModel<>();
				for(Producto p: productos) {	
					DLM.addElement(p);
					}
				list.setModel(DLM);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		final JButton btnCesta = new JButton("Cesta : 0");
		btnCesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaCesta window = new VentanaCesta(usuario,cantidadproductos);
				window.setVisible(true);
				setVisible(false);
			}
		});
		
		JLabel lblBuscador = new JLabel("BUSCADOR");
		lblBuscador.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscador.setForeground(SystemColor.textHighlight);
		lblBuscador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnAadir = new JButton("AÑADIR");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cantidadproductos = cantidadproductos + 1;
				btnCesta.setText("Cesta : "+ cantidadproductos);
				boolean respuesta = CestaResource.anadirProductoCesta(usuario,productos.get(list.getSelectedIndex()));
				if(respuesta!=true) {
					System.out.println("El producto no se añade a la cesta");
				}
			}
		});
		btnAadir.setBackground(new Color(135, 206, 250));
		
		JButton volver = new JButton("VOLVER");
		volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaLogin log = new VentanaLogin();
				log.setVisible(true);
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
							.addComponent(lblBuscador, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(60)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
											.addGap(20))
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(volver)
												.addGap(418))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnAadir, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnCesta, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
												.addGap(32))))
									.addGap(92))
								.addComponent(list, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblBuscador, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
					.addGap(26)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAadir, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCesta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(volver)
					.addGap(16))
		);
		getContentPane().setLayout(groupLayout);
	}
}
