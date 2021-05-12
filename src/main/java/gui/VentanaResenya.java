package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jdo.Resenya;

import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaResenya extends JFrame{
	
	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget resenyaTarget = appTarget.path("resenya");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaResenya window = new VentanaResenya("Manzana", "unai");
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
	public VentanaResenya(String producto, String usuario) {
		initialize(producto, usuario);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final String producto, final String usuario) {
		setBounds(100, 100, 634, 420);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Añadir Reseña");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(241, 10, 131, 39);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(producto);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(263, 59, 75, 13);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Calificación del producto\r\n");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(38, 93, 154, 45);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("(1 Muy Mal / 5 Muy Bien)\r\n");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(38, 124, 167, 31);
		getContentPane().add(lblNewLabel_1_1_1);
		
		final JRadioButton rdbtnNewRadioButton = new JRadioButton("1");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnNewRadioButton.setBounds(38, 172, 103, 21);
		getContentPane().add(rdbtnNewRadioButton);
		
		final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("2");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnNewRadioButton_1.setBounds(38, 194, 103, 21);
		getContentPane().add(rdbtnNewRadioButton_1);
		
		final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("3");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnNewRadioButton_2.setBounds(38, 215, 103, 21);
		getContentPane().add(rdbtnNewRadioButton_2);
		
		final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("4");
		rdbtnNewRadioButton_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnNewRadioButton_3.setBounds(38, 238, 103, 21);
		getContentPane().add(rdbtnNewRadioButton_3);
		
		final JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("5");
		rdbtnNewRadioButton_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnNewRadioButton_4.setBounds(38, 261, 103, 21);
		getContentPane().add(rdbtnNewRadioButton_4);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton_1);
		group.add(rdbtnNewRadioButton_2);
		group.add(rdbtnNewRadioButton_3);
		group.add(rdbtnNewRadioButton_4);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(337, 152, 220, 171);
		getContentPane().add(textArea);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Escriba su opinión del producto:");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_2.setBounds(337, 93, 220, 45);
		getContentPane().add(lblNewLabel_1_1_2);
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textArea.getText().isEmpty() || (!rdbtnNewRadioButton.isSelected()&&!rdbtnNewRadioButton_1.isSelected()&&!rdbtnNewRadioButton_2.isSelected()&&!rdbtnNewRadioButton_3.isSelected()&&!rdbtnNewRadioButton_4.isSelected())) {
					JOptionPane.showMessageDialog(null, "Faltan Campos por Rellenar");
				}else {
					int cal;
					if (rdbtnNewRadioButton.isSelected()) {
						cal = 1;
					} else if (rdbtnNewRadioButton_1.isSelected()) {
						cal = 2;
					}else if (rdbtnNewRadioButton_2.isSelected()) {
						cal = 3;
					}else if (rdbtnNewRadioButton_3.isSelected()) {
						cal = 4;
					}else {
						cal = 5;
					}
					
					Resenya res = new Resenya(producto, usuario, cal, textArea.getText());
					List<String> resenya = new ArrayList<>();
					resenya.add(res.getProducto());
					resenya.add(res.getUsuario());
					resenya.add(String.valueOf(res.getCalificacion()));
					resenya.add(res.getOpinion());
					WebTarget productInsTarget = resenyaTarget.path("add");
					productInsTarget.request().post(Entity.entity(resenya, MediaType.APPLICATION_JSON));
					JOptionPane.showMessageDialog(null, "Reseña Añadida Correctamente");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(253, 341, 85, 21);
		getContentPane().add(btnNewButton);
	}
}
