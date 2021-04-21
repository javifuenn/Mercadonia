package SA02;

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
import jdo.Paypal;
import jdo.Pedido;
import jdo.Producto;
import jdo.Usuario;

@Path("pagos")
public class PagosResource {

	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public static Paypal getUsuarioPaypal(String correo) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Paypal paypal = null;
		
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
	  
	 
	  
}
