package util;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import jdo.Cesta;
import jdo.Cupon;
import jdo.Paypal;
import jdo.Pedido;
import jdo.Producto;
import jdo.Usuario;
import jdo.VentaProducto;
import jdo.Visa;



public class PreparaDatos {

	public static void main(String[] args) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Producto prod1 = new Producto("Lechuga", "Muy sana", 2.4, "unai", 6);
			pm.makePersistent(prod1);
			Producto prod2 = new Producto("Manzana", "Deliciosa", 3, "sergio", 100);
			pm.makePersistent(prod2);
			Producto prod3 = new Producto("Pan", "Recien horneado", 0.6, "javi", 75);
			pm.makePersistent(prod3);
			Producto prod4 = new Producto("Tomate", "Recien cocinado", 0.9, "javi", 75);
			pm.makePersistent(prod4);
			Producto prod5 = new Producto("Carne", "Recien cortada", 13, "unai", 75, true);
			pm.makePersistent(prod5);
			Producto prod6 = new Producto("Patatas", "Para freir", 1.1, "javi", 75, true);
			pm.makePersistent(prod6);

			Usuario usuario1 = new Usuario("sergio", "1234","sergiosanchezprieto@opendeusto.es");
			pm.makePersistent(usuario1);
			Usuario usuario2 = new Usuario("unai", "1234","null");
			pm.makePersistent(usuario2);
			Usuario usuario3 = new Usuario("javi", "4321","null");
			pm.makePersistent(usuario3);
			
			Paypal paypal = new Paypal("correo", "1234");
			pm.makePersistent(paypal);
			
			Visa visa = new Visa(123456789, "Jon", 123, "nunca");
			pm.makePersistent(visa);
			
			Visa visa2 = new Visa(987654321, "Sergio", 333, "hoy");
			pm.makePersistent(visa2);
			
			List<String> listProd = Arrays.asList("Lechuga", "Muy sana", "2.4", "unai", "55" );
			Pedido pedido = new Pedido("sergio", new Date(121,3,4), listProd, "universidad");
			pm.makePersistent(pedido);
			
			VentaProducto vp1 = new VentaProducto("Manzana", "unai", 2);
			pm.makePersistent(vp1);
			
			Cupon cupon1 = new Cupon("descuento10", 10, "sergio");
			pm.makePersistent(cupon1);
			
			Cupon cupon2 = new Cupon("descuento40", 40, "unai");
			pm.makePersistent(cupon2);
			
			
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
}