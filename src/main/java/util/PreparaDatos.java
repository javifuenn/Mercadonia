package util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import jdo.Producto;
import jdo.Usuarios;
import jdo.Cesta;

import java.util.Date;


public class PreparaDatos {

	public static void main(String[] args) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Producto prod1 = new Producto("1A", "Lechuga", "Muy sana", 2.4);
			pm.makePersistent(prod1);
			Producto prod2 = new Producto("2A", "Manzana", "Deliciosa", 3);
			pm.makePersistent(prod2);
			Producto prod3 = new Producto("3A", "Pan", "Recien horneado", 0.6);
			pm.makePersistent(prod3);
			
			Usuarios usuario1 = new Usuarios("sergio", "1234");
			pm.makePersistent(usuario1);
			Usuarios usuario2 = new Usuarios("unai", "1234");
			pm.makePersistent(usuario2);
			Usuarios usuario3 = new Usuarios("javi", "4321");
			pm.makePersistent(usuario3);

			Cesta cesta1 = new Cesta(usuario1, prod1, new Date(2022, 10, 20));
			pm.makePersistent(cesta1);
			Cesta cesta2 = new Cesta(usuario2, prod2, new Date(2022, 10, 15));
			pm.makePersistent(cesta2);
			Cesta cesta3 = new Cesta(usuario3, prod1, new Date(2022, 8, 12));
			pm.makePersistent(cesta3);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
}