package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Cesta;
import jdo.Paypal;
import jdo.Pedido;
import jdo.Producto;
import jdo.Usuario;
import jdo.Visa;
import sa02.CestaResource;
import sa02.PagosResource;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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

	private static List<Producto> productos;
	private static Usuario usuario;

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

	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget pagoTarget = appTarget.path("pagos");
	final WebTarget cestaTarget = appTarget.path("cesta");
	final WebTarget productosTarget = appTarget.path("productos");

	private JTextField textDireccion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPago frame = new VentanaPago(usuario, productos);
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
	public VentanaPago(Usuario usuarioVerificado, List<Producto> productosSeleccionados) {

		usuario = usuarioVerificado;
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
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "PayPal", "Visa" }));
		comboBox.setToolTipText("");
		comboBox.setMaximumRowCount(2);
		comboBox.setBounds(275, 43, 96, 21);
		contentPane.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectionPago = comboBox.getSelectedIndex();
				switch (selectionPago) {
				case 0:
					panelVisa.setVisible(true);
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
					textDireccion.setText("");

					break;
				case 1:
					panelVisa.setVisible(true);
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
					textDireccion.setText("");
					break;
				}
			}
		});

		list = new JList<Producto>();
		list.setBounds(461, 46, 200, 294);
		contentPane.add(list);
		list.setModel(modelProducto);

		lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(461, 366, 59, 21);
		contentPane.add(lblPrecio);

		textPrecio = new JTextField();
		textPrecio.setColumns(10);
		textPrecio.setBounds(524, 369, 137, 19);
		contentPane.add(textPrecio);

		JButton btnPagar = new JButton("PAGAR");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBox.getSelectedItem() == "PayPal") {
					String correo = textNumeroTarjeta.getText();

					WebTarget pagoPaypalTarget = pagoTarget.path("paypali").queryParam("correo", correo);
					GenericType<Paypal> genericType = new GenericType<Paypal>() {
					};
					Paypal paypal = pagoPaypalTarget.request(MediaType.APPLICATION_JSON).get(genericType);

					System.out.println("Llamamos a paypal" + paypal.getContrasena());
					if (paypal.getContrasena().equals(textTitular.getText())) {

						List<String> productosCesta = new ArrayList<String>();

						WebTarget buscarTarget = cestaTarget.path("buscar").queryParam("Usuario",
								usuario.getUsername());
						GenericType<List<Producto>> genericType7 = new GenericType<List<Producto>>() {
						};
						productos = buscarTarget.request(MediaType.APPLICATION_JSON).get(genericType7);

						for (Producto producto : productos) {

							productosCesta.add(producto.getNombre());
							System.out.println("Llamamos a paypal" + producto.getNombre());

							
						}
						
						WebTarget compraTarget = productosTarget.path("comprar");
						compraTarget.request().post(Entity.entity(usuario, MediaType.APPLICATION_JSON));

						System.out.println("Llamamos a paypal" + paypal.getCorreo());
						Pedido pedido = new Pedido(usuario.getUsername(), null, productosCesta,
								textDireccion.getText());
						System.out.println("Nos hacemos el pedido y lo vamos a mandar");

						WebTarget pedidoAñadirTarget = pagoTarget.path("añadir");
						List<String> pedidoL = new ArrayList<>();
						pedidoL.add(usuario.getUsername());
						Calendar fecha = new GregorianCalendar();
						int año = fecha.get(Calendar.YEAR);
						int mes = fecha.get(Calendar.MONTH);
						int dia = fecha.get(Calendar.DAY_OF_MONTH);
						Date fecha2 = new Date(año, mes, dia);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String fechaPedido = sdf.format(fecha2);
						pedidoL.add(fechaPedido);
						for (Producto p : productos) {
							pedidoL.add(p.getNombre());
						}
						
						
						pedidoL.add(textDireccion.getText());
						pedidoAñadirTarget.request().post(Entity.entity(pedidoL, MediaType.APPLICATION_JSON));

					}

				} else {
					if (comboBox.getSelectedItem() == "Visa") {

						WebTarget pagoVisaTarget = pagoTarget.path("visai").queryParam("numTarjeta",
								textNumeroTarjeta.getText());
						GenericType<Visa> genericType = new GenericType<Visa>() {
						};
						Visa visa = pagoVisaTarget.request(MediaType.APPLICATION_JSON).get(genericType);

						if (visa.getnTarjeta() == Integer.parseInt(textNumeroTarjeta.getText())) {
							if (visa.getCv() == Integer.parseInt(textCV.getText())) {

								List<String> productosCesta = new ArrayList<String>();

								WebTarget buscarTarget = cestaTarget.path("buscar").queryParam("Usuario",
										usuario.getUsername());
								GenericType<List<Producto>> genericType7 = new GenericType<List<Producto>>() {
								};
								productos = buscarTarget.request(MediaType.APPLICATION_JSON).get(genericType7);

								for (Producto producto : productos) {

									productosCesta.add(producto.getNombre());
								}

								Pedido pedido = new Pedido(usuario.getUsername(), null, productosCesta,
										textDireccion.getText());

								WebTarget pedidoAñadirTarget = pagoTarget.path("añadir");
								List<String> pedidoL = new ArrayList<>();
								pedidoL.add(usuario.getUsername());
								Calendar fecha = new GregorianCalendar();
								int año = fecha.get(Calendar.YEAR);
								int mes = fecha.get(Calendar.MONTH);
								int dia = fecha.get(Calendar.DAY_OF_MONTH);
								Date fecha2 = new Date(año, mes, dia);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								String fechaPedido = sdf.format(fecha2);
								pedidoL.add(fechaPedido);
								for (Producto p : productos) {
									pedidoL.add(p.getNombre());
								}
								
								
								WebTarget compraTarget = productosTarget.path("comprar");
								compraTarget.request().post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
								pedidoL.add(textDireccion.getText());
								pedidoAñadirTarget.request().post(Entity.entity(pedidoL, MediaType.APPLICATION_JSON));
							}

						}
					}

				}
			}
		});
		btnPagar.setBounds(298, 410, 85, 21);
		contentPane.add(btnPagar);

		panelVisa = new Panel();
		panelVisa.setBounds(56, 89, 344, 298);
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

		JLabel lblDireccion = new JLabel("Dirección: ");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDireccion.setBounds(10, 230, 137, 21);
		panelVisa.add(lblDireccion);

		textDireccion = new JTextField();
		textDireccion.setColumns(10);
		textDireccion.setBounds(162, 233, 137, 19);
		panelVisa.add(textDireccion);
	}
}
