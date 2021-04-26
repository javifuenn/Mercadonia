package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jdo.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaAnadirProducto extends JFrame{

	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget productTarget = appTarget.path("productos");
	private JTextField textField_3;


	/**
	 * Create the application.
	 */
	public VentanaAnadirProducto(Usuario usuario) {
		initialize(usuario);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Usuario usuario) {
		
		setBounds(100, 100, 640, 523);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Añadir Producto");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setBounds(220, 28, 157, 48);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(224, 150, 186, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(224, 197, 186, 19);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(224, 257, 186, 19);
		getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(224, 314, 186, 19);
		getContentPane().add(textField_3);
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!(textField.getText().equals("") || textField_1.getText().equals("") || textField_2.getText().equals(""))) {
					List<String> productoL = new ArrayList<>();
					productoL.add(textField.getText());
					productoL.add(textField_1.getText());
					productoL.add(textField_2.getText());
					productoL.add(usuario.getUsername());
					productoL.add(textField_3.getText());
					WebTarget productInsTarget = productTarget.path("ins");
					productInsTarget.request().post(Entity.entity(productoL, MediaType.APPLICATION_JSON));
					VentanaVentaProductos v = new VentanaVentaProductos(usuario);
					v.setVisible(true);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Hay campos sin rellenar");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(457, 419, 108, 34);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(220, 121, 71, 19);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Descripcion:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(220, 173, 114, 19);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Precio:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(220, 228, 71, 19);
		getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Stock:");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(220, 285, 71, 19);
		getContentPane().add(lblNewLabel_1_1_1_1);
		
	
	}
}
