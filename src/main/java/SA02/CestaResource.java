package SA02;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jdo.Cesta;
import jdo.Producto;
import jdo.Usuarios;

@Path("cesta")
public class CestaResource {

	public static boolean anadirProductoCesta(Usuarios usuario, Producto producto) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean respuesta = false;
		Cesta cesta = new Cesta(producto.getNombre(), null, usuario.getUsername());

		try {
			tx.begin();
			System.out.println("  * Storing an object: " + cesta);
			pm.makePersistent(cesta);
			tx.commit();
			respuesta = true;

		} catch (Exception ex) {
			System.out.println("  $ Error storing an object: " + ex.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return respuesta;
	}

	public static List<Producto> vaciarCesta(Usuarios usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean respuesta = false;
		

		ArrayList<Producto> productos = new ArrayList<Producto>();

		Query<Cesta> q = pm.newQuery(
				"SELECT FROM " + Cesta.class.getName() + " WHERE NombreUsuario == '" + usuario.getUsername() + "'");

		List<Cesta> cestav = q.executeList();
		
		pm.deletePersistentAll(cestav);
		
		

		return productos;
	}

	public static List<Producto> verProductosCesta(Usuarios usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		ArrayList<Producto> productos = new ArrayList<Producto>();

		Query<Cesta> q = pm.newQuery(
				"SELECT FROM " + Cesta.class.getName() + " WHERE NombreUsuario == '" + usuario.getUsername() + "'");

		List<Cesta> cestav = q.executeList();

		for (Cesta cesta : cestav) {

			Query<Producto> qq = pm.newQuery(
					"SELECT FROM " + Producto.class.getName() + " WHERE nombre== '" + cesta.getNombreproducto() + "'");

			List<Producto> productosr = qq.executeList();

			productos.add(productosr.get(0));

		}

		pm.close();
		return productos;
	}
}
