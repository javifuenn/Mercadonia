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

import javax.swing.JLabel;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAnadirProducto window = new VentanaAnadirProducto("javi");
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
	public VentanaAnadirProducto(String usuario) {
		initialize(usuario);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final String usuario) {
		
		setBounds(100, 100, 640, 523);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Añadir Producto");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel.setBounds(220, 28, 157, 48);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(224, 199, 186, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(224, 246, 186, 19);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(224, 306, 186, 19);
		getContentPane().add(textField_2);
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!(textField.getText().equals("") || textField_1.getText().equals("") || textField_2.getText().equals(""))) {
					List<String> productoL = new ArrayList<>();
					productoL.add(textField.getText());
					productoL.add(textField_1.getText());
					productoL.add(textField_2.getText());
					productoL.add(usuario);
					WebTarget productInsTarget = productTarget.path("ins");
					productInsTarget.request().post(Entity.entity(productoL, MediaType.APPLICATION_JSON));
					VentanaVentaProductos v = new VentanaVentaProductos(usuario);
					v.setVisible(true);
					dispose();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(457, 419, 108, 34);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(220, 170, 71, 19);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Descripcion:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(220, 222, 114, 19);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Precio:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(220, 277, 71, 19);
		getContentPane().add(lblNewLabel_1_1_1);
	}
}
