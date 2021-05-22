package sa02;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jdo.Cupon;



@Path("cupones")
public class CuponesResource {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@POST
	@Path("anadir")
	@Produces(MediaType.APPLICATION_JSON)
	public static void insertarCupon(Cupon cupon) {
		String nombre = cupon.getTextoCupon();
		int porce = cupon.getPorcentajeDescuento();
		String usuario = cupon.getUsuario();
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Cupon c = new Cupon(nombre, porce, usuario);
			pm.makePersistent(c);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@POST
	@Path("borrar")
	@Consumes(MediaType.APPLICATION_JSON)
	public static void borrarCupon(List<Cupon> cupon) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		System.out.println("5");
		try {

			for (Cupon c : cupon) {
				String nombrecupon = c.getTextoCupon();
				System.out.println(nombrecupon);
				try (Query<Cupon> q = pm.newQuery(
						"SELECT FROM " + Cupon.class.getName() + " WHERE textocupon == '" + nombrecupon + "'")) {
					List<Cupon> cuponn = q.executeList();

					pm.deletePersistentAll(cuponn);
					System.out.println("6");
				} catch (Exception e) {
					LOGGER.severe(e.getMessage());
				}

			}

		} finally {
			pm.close();
		}
	}

	@GET
	@Path("buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Cupon> verCupones(@QueryParam("Usuario") String usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		ArrayList<Cupon> cupones = new ArrayList<>();

		try {
			Query<Cupon> cupon = pm
					.newQuery("SELECT FROM " + Cupon.class.getName() + " WHERE usuario == '" + usuario + "'");

			List<Cupon> cup = cupon.executeList();

			for (Cupon c : cup) {

				cupones.add(c);

			}
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		} finally {
			pm.close();
		}
		return cupones;
	}

	@GET
	@Path("buscar1")
	@Produces(MediaType.APPLICATION_JSON)
	public static Cupon verCupon(@QueryParam("nombrecupon") String usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Cupon cupones = null;
		try {
			Query<Cupon> cupon = pm
					.newQuery("SELECT FROM " + Cupon.class.getName() + " WHERE textocupon == '" + usuario + "'");

			List<Cupon> cup = cupon.executeList();

			if (!cup.isEmpty())
				cupones = cup.get(0);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		} finally {
			pm.close();
		}
		return cupones;
	}

}
