package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Paypal;
import jdo.Pedido;
import jdo.Producto;
import jdo.Usuario;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaCompras extends JFrame {

	private JPanel contentPane;
	private static Usuario usuario;
	private static int cantidad;

	Client cliente = ClientBuilder.newClient();
	final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget pagoTarget = appTarget.path("pagos");
	final WebTarget cestaTarget = appTarget.path("cesta");
	final WebTarget cuentaTarget = appTarget.path("contar");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCompras frame = new VentanaCompras(usuario);
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
	public VentanaCompras(Usuario usuario1) {
		usuario = usuario1;

		cuentaTarget.path("contar").queryParam("Usuario", usuario.getUsername());
		GenericType<Integer> genericType2 = new GenericType<Integer>() {
		};
		cantidad = cuentaTarget.request(MediaType.APPLICATION_JSON).get(genericType2);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JList listProductos = new JList();
		listProductos.setBounds(355, 23, 475, 398);
		DefaultListModel<Pedido> DLM = new DefaultListModel<>();
		WebTarget pagoPedidoTarget = pagoTarget.path("pedidos").queryParam("nombre", usuario.getUsername());
		GenericType<List<Pedido>> genericType = new GenericType<List<Pedido>>() {
		};
		List<Pedido> pedidos = pagoPedidoTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		for (Pedido p : pedidos) {
			DLM.addElement(p);
		}
		listProductos.setModel(DLM);
		contentPane.add(listProductos);

		JButton btnBorrar = new JButton("Cerrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VentanaCesta cesta = new VentanaCesta(usuario);
				cesta.setVisible(true);
				dispose();

			}
		});
		btnBorrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBorrar.setBounds(55, 406, 87, 34);
		contentPane.add(btnBorrar);

		JLabel lblNewLabel = new JLabel("Compras realizadas:");
		lblNewLabel.setBounds(39, 23, 135, 52);
		contentPane.add(lblNewLabel);
	}
}
