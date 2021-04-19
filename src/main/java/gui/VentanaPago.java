package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdo.Producto;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Panel;

public class VentanaPago extends JFrame {

	private JPanel contentPane;
	public JTextField textPrecio;
	
	private JComboBox<String> comboBox;
	private JTextField textCV;
	private JTextField textFechaCaducidad;
	private JTextField textTitular;
	private JTextField textNumeroTarjeta;
	
	private Panel panelVisa;
	private JLabel lblNumerotarjeta;
	private JLabel lblPrecio;
	private JLabel lblTitular;
	private JLabel lblFechaCaducidad;
	private JLabel lblCV;
	
	private JList<Producto> list;
	DefaultListModel<Producto> modelProducto = new DefaultListModel<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPago frame = new VentanaPago();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSeleccion = new JLabel("Seleccionar metodo de pago: ");
		lblSeleccion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSeleccion.setBounds(56, 34, 248, 35);
		contentPane.add(lblSeleccion);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"PayPal", "Visa"}));
		comboBox.setToolTipText("");
		comboBox.setMaximumRowCount(2);
		comboBox.setBounds(275, 43, 96, 21);
		contentPane.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectionPago = comboBox.getSelectedIndex();
				switch (selectionPago){
				case 0:panelVisa.setVisible(true);
					lblNumerotarjeta.setText("Email: ");
					lblTitular.setText("contraseña: ");
					lblFechaCaducidad.setVisible(false);
					lblCV.setVisible(false);
					textCV.setVisible(false);
					textFechaCaducidad.setVisible(false);
					textCV.setText("");
					textFechaCaducidad.setText("");
					textNumeroTarjeta.setText("");
					textTitular.setText("");
					
				break;
				case 1:panelVisa.setVisible(true);
					lblNumerotarjeta.setText("Numero de tarjeta: ");
					lblTitular.setText("Titular: ");
					lblFechaCaducidad.setVisible(true);
					lblCV.setVisible(true);
					textCV.setVisible(true);
					textFechaCaducidad.setVisible(true);
					textCV.setText("");
					textFechaCaducidad.setText("");
					textNumeroTarjeta.setText("");
					textTitular.setText("");
				break;
				}
			}
		});
		
		list = new JList<Producto>();
		list.setBounds(461, 46, 200, 294);
		contentPane.add(list);
		
		lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(461, 366, 59, 21);
		contentPane.add(lblPrecio);
		
		textPrecio = new JTextField();
		textPrecio.setColumns(10);
		textPrecio.setBounds(524, 369, 137, 19);
		contentPane.add(textPrecio);
		
		JButton btnPagar = new JButton("PAGAR");
		btnPagar.setBounds(298, 410, 85, 21);
		contentPane.add(btnPagar);
		
		panelVisa = new Panel();
		panelVisa.setBounds(56, 118, 344, 250);
		contentPane.add(panelVisa);
		panelVisa.setLayout(null);
		panelVisa.setVisible(false);
		
		lblNumerotarjeta = new JLabel("Numero de tarjeta:");
		lblNumerotarjeta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumerotarjeta.setBounds(10, 38, 142, 21);
		panelVisa.add(lblNumerotarjeta);
		
		lblTitular = new JLabel("Titular: ");
		lblTitular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitular.setBounds(10, 86, 137, 21);
		panelVisa.add(lblTitular);
		
		lblFechaCaducidad = new JLabel("Fecha caducidad: ");
		lblFechaCaducidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFechaCaducidad.setBounds(10, 133, 137, 21);
		panelVisa.add(lblFechaCaducidad);
		
		lblCV = new JLabel("CV: ");
		lblCV.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCV.setBounds(10, 178, 137, 21);
		panelVisa.add(lblCV);
		
		textCV = new JTextField();
		textCV.setColumns(10);
		textCV.setBounds(162, 181, 137, 19);
		panelVisa.add(textCV);
		
		textFechaCaducidad = new JTextField();
		textFechaCaducidad.setColumns(10);
		textFechaCaducidad.setBounds(162, 136, 137, 19);
		panelVisa.add(textFechaCaducidad);
		
		textTitular = new JTextField();
		textTitular.setColumns(10);
		textTitular.setBounds(162, 89, 137, 19);
		panelVisa.add(textTitular);
		
		textNumeroTarjeta = new JTextField();
		textNumeroTarjeta.setColumns(10);
		textNumeroTarjeta.setBounds(162, 41, 137, 19);
		panelVisa.add(textNumeroTarjeta);
	}
}
