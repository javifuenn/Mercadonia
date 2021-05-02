package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import jdo.Usuario;
import jdo.VentaProducto;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;

public class VentanaNumVentas extends JFrame{



    /**
     * Create the application.
     */
    public VentanaNumVentas(VentaProducto vp) {
        initialize(vp);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(final VentaProducto vp) {
        setBounds(100, 100, 534, 296);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JLabel lblProducto = new JLabel(vp.getProducto());
        lblProducto.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblProducto.setBounds(145, 23, 172, 52);
        getContentPane().add(lblProducto);
        
        JLabel lblCantidad = new JLabel(String.valueOf(vp.getCantidad()));
        lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCantidad.setBounds(145, 116, 172, 52);
        getContentPane().add(lblCantidad);
        
        JLabel lblNewLabel = new JLabel("Producto");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(31, 31, 74, 40);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Cantidad");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(31, 116, 81, 19);
        getContentPane().add(lblNewLabel_1);
    }
}
