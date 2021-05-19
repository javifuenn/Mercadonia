package sa02;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Resenya;

@Path("resenya")
public class ResenyaResource {

	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public static void insertarResenya(List<String> resenya) {
		
		String producto = resenya.get(0);
		String usuario = resenya.get(1);
		int calificacion = Integer.parseInt(resenya.get(2));
		String opinion = resenya.get(3);

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Resenya r = new Resenya(producto, usuario, calificacion, opinion);
			pm.makePersistent(r);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	@GET
	@Path("nom")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Resenya> getResenya(@QueryParam("producto") String producto) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Resenya> resenyas = null;

		try {
			Query<Resenya> q = pm.newQuery("SELECT FROM " + Resenya.class.getName() + " WHERE producto== '" + producto + "'");

			resenyas = q.executeList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return resenyas;
	}
}
