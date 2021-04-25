package SA02;

import java.util.List;

import javax.jdo.Extent;
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
import jdo.Cesta;
import jdo.Paypal;
import jdo.Pedido;
import jdo.Producto;
import jdo.Usuario;
import jdo.Visa;

@Path("pagos")
public class PagosResource {

	  @GET
	  @Path("paypali")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static Paypal getUsuarioPaypal(@QueryParam("correo") String correo) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Paypal paypal = new Paypal();
		
		Query<Paypal> q = pm.newQuery("SELECT FROM " + Paypal.class.getName() + " WHERE correo== '" + correo + "'");
		
		List<Paypal> paypal1 = q.executeList();
		
		paypal = paypal1.get(0);
		
		
		pm.close();
	
		return paypal;
	  }
	  
		public static boolean anadirPedido(Pedido pedido) {
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			boolean respuesta = false;

			try {
				tx.begin();
				System.out.println("  * Storing an object: " + pedido);
				pm.makePersistent(pedido);
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
		
		  @GET
		  @Path("visai")
		  @Produces(MediaType.APPLICATION_JSON)
		  public static Visa getUsuarioVisa(int numTarjeta) {
		   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();

			Visa visa = null;
			
			Query<Visa> q = pm.newQuery("SELECT FROM " + Visa.class.getName() + " WHERE nTarjeta== '" + numTarjeta + "'");
			
			List<Visa> visa1 = q.executeList();
			
			visa = visa1.get(0);
			
			
			pm.close();
		
			return visa;
		  }
		  
		  
	  
	 
	  
}
