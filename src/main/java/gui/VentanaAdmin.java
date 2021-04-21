package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Font;

public class VentanaAdmin extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdmin frame = new VentanaAdmin();
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
	public VentanaAdmin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList listUsuarios = new JList();
		listUsuarios.setBounds(54, 38, 300, 305);
		contentPane.add(listUsuarios);
		
		JList listProductos = new JList();
		listProductos.setBounds(494, 38, 300, 305);
		contentPane.add(listProductos);
		
		JButton btnAñadir = new JButton("Añadir producto/usuario");
		btnAñadir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAñadir.setBounds(118, 373, 185, 55);
		contentPane.add(btnAñadir);
		
		JButton btnBorrar = new JButton("borrar producto/usuario");
		btnBorrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBorrar.setBounds(558, 373, 185, 55);
		contentPane.add(btnBorrar);
	}
}
