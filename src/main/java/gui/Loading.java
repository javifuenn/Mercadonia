package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loading extends JFrame {

	private JProgressBar bar = new JProgressBar();
	private JLabel enviado;
	private JLabel compruebaCorreo;
	private JLabel enviando;
	private JButton aceptar;
	private VentanaVerificarCodigo cod = new VentanaVerificarCodigo();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loading window = new Loading();
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
	public Loading() {
		setTitle("ENVIANDO CODIGO VERIFICACION");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		// INICIALIZAMOS LA BARRA QUE SIMULARA EL ENVIO DEL CODIGO AL CORREO
		bar.setBounds(53, 134, 309, 14);
		getContentPane().add(bar);
		bar.setStringPainted(true);
		new Thread(new Hilo()).start();

		// HAREMOS MAS VISUAL LA VENTANA MEDIANTE ESTOS JLABEL
		enviando = new JLabel("Enviando codigo...");
		enviando.setBounds(155, 109, 125, 14);
		getContentPane().add(enviando);

		enviado = new JLabel("Codigo enviado \u2713");
		enviado.setBounds(155, 109, 110, 14);
		getContentPane().add(enviado);
		enviado.setVisible(false);

		compruebaCorreo = new JLabel("Compruebe en su correo electronico");
		compruebaCorreo.setHorizontalAlignment(SwingConstants.CENTER);
		compruebaCorreo.setBounds(53, 159, 309, 14);
		getContentPane().add(compruebaCorreo);

		/**
		 * BOTON QUE APARECERA UNA VEZ SE HAYA LLEGADO AL 100% DE LA BARRA Este boton
		 * muestra la siguiente ventana para poder introducir codigo
		 */
		aceptar = new JButton("Introducir codigo");
		aceptar.setBackground(SystemColor.textHighlight);
		aceptar.setBounds(124, 200, 156, 34);
		getContentPane().add(aceptar);
		compruebaCorreo.setVisible(false);
		aceptar.setVisible(false);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				cod.setVisible(true);
			}
		});

	}

	/**
	 * Comprueba que el valor de la barra esta en 100 y si es asi muestra y esconde
	 * los diferentes jlabels
	 */
	public void valor100() {
		// Comprueba el valor de la progressbar para mostrar unos labels u otros
		if (bar.getValue() == 100) {
			enviado.setVisible(true);
			compruebaCorreo.setVisible(true);
			enviando.setVisible(false);
			aceptar.setVisible(true);

		}
	}

	/**
	 * Mediante un hilo incrementamos el valor de la progessbar y a su vez
	 * implementamos el metodo valor100 explicado anteriormente
	 */
	public class Hilo implements Runnable {
		public void run() {
			for (int i = 0; i <= 100; i++) {
				bar.setValue(i);
				bar.repaint();
				valor100();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}

			}

		}
	}

	{

	}
}
