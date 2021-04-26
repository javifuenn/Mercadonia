package SA02;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jdo.Cesta;
import jdo.Producto;
import jdo.Usuario;

@Path("cesta")
public class CestaResource {

	@GET
	@Path("anadir")
	@Produces(MediaType.APPLICATION_JSON)
	public static boolean anadirProductoCesta(@QueryParam("Producto") String producto,
			@QueryParam("Usuario") String usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean respuesta = false;

		Cesta cesta = new Cesta(producto, null, usuario);
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

	@POST
	@Path("borrar")
	@Consumes(MediaType.APPLICATION_JSON)
	public static void vaciarCesta(Usuario usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Query<Cesta> q = pm.newQuery(
				"SELECT FROM " + Cesta.class.getName() + " WHERE NombreUsuario == '" + usuario.getUsername() + "'");

		List<Cesta> cestav = q.executeList();

		pm.deletePersistentAll(cestav);

		pm.close();

	}

	@GET
	@Path("buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Producto> verProductosCesta(@QueryParam("Usuario") String usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		ArrayList<Producto> productos = new ArrayList<Producto>();

		Query<Cesta> q = pm
				.newQuery("SELECT FROM " + Cesta.class.getName() + " WHERE NombreUsuario == '" + usuario + "'");

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

	@GET
	@Path("contar")
	@Produces(MediaType.APPLICATION_JSON)
	public static int contarPoductos(@QueryParam("Usuario") String usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Query<Cesta> q = pm
				.newQuery("SELECT FROM " + Cesta.class.getName() + " WHERE NombreUsuario == '" + usuario + "'");

		List<Cesta> cestav = q.executeList();

		int contador = 0;
		for (Cesta cesta : cestav) {

			contador = contador + 1;

		}

		pm.close();
		return contador;
	}

}
