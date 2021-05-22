package sa02;

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
import jdo.Cupon;
import jdo.Producto;
import jdo.Usuario;

@Path("cupones")
public class CuponesResource {

	@POST
	@Path("anadir")
	@Produces(MediaType.APPLICATION_JSON)
	public static void insertarProducto(Cupon cupon) {
		System.out.println("cupones");
		String nombre = cupon.getTextoCupon();
		int porce =cupon.getPorcentajeDescuento();
		String usuario = cupon.getUsuario();
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Cupon c = new Cupon(nombre,porce,usuario);
			pm.makePersistent(c);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("cupones");
	}
	
	@POST
	@Path("borrar")
	@Consumes(MediaType.APPLICATION_JSON)
	public static void borrarCupon(Cupon cupon) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		try {
			Query<Cupon>cupo = pm.newQuery(
					"SELECT FROM " + Cupon.class.getName() + " WHERE textocupon == '" + cupon.getTextoCupon() + "'");

			List<Cupon> c = cupo.executeList();

			pm.deletePersistentAll(c);
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
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return cupones;
	}
	
	

	

}
