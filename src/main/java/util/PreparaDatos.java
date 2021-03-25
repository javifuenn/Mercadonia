package util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import jdo.Producto;



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
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
}