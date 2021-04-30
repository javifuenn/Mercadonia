package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import jdo.Usuario;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class VentanaOpcion extends JFrame{



	/**
	 * Create the application.
	 */
	public VentanaOpcion(Usuario usuario) {
		initialize(usuario);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Usuario usuario) {
		setBounds(100, 100, 534, 296);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Comprar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaBusqueda2 window = new VentanaBusqueda2(usuario);
				window.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(69, 101, 163, 51);
		getContentPane().add(btnNewButton);
		
		JButton btnVender = new JButton("Vender\r\n");
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaVentaProductos v2 = new VentanaVentaProductos(usuario);;
				v2.setVisible(true);
				dispose();
			}
		});
		btnVender.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVender.setBounds(293, 101, 163, 51);
		getContentPane().add(btnVender);
	}
}
