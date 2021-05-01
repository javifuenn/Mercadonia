package sa02;

import java.util.ArrayList;
import java.util.Date;
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
	  	@POST
	  	@Path("añadir")
	  	@Produces(MediaType.APPLICATION_JSON)
		public static void anadirPedido(List<String> pedidoL) {
	  		String nombre = pedidoL.get(0);
	  		String fechaPago = pedidoL.get(1);
	  		List<String> productos = new ArrayList<String>();
	  		for(int i = 2; i <= pedidoL.size()-1; i++) {
	  			productos.add(pedidoL.get(i));
	  		}
	  		String direccion = pedidoL.get(pedidoL.size());
	  		Date fecha = new Date();
	  		fecha.setDate(Integer.parseInt(fechaPago));
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Pedido p = new Pedido(nombre, fecha, productos, direccion);
				pm.makePersistent(p);
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		}
		
		  @GET
		  @Path("pedidos")
		  @Produces(MediaType.APPLICATION_JSON)
		  public static List<Pedido> getPedidos(@QueryParam("nombre") String nombre) {
		   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();

			
			Query<Pedido> q = pm.newQuery("SELECT FROM " + Pedido.class.getName() + " WHERE nombre== '" + nombre + "'");
			
			List<Pedido> visa1 = q.executeList();
			
			
			pm.close();
		
			return visa1;
		  }
		
		  @GET
		  @Path("visai")
		  @Produces(MediaType.APPLICATION_JSON)
		  public static Visa getUsuarioVisa(@QueryParam("numTarjeta") int numTarjeta) {
		   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();

			Visa visa = new Visa();
			
			Query<Visa> q = pm.newQuery("SELECT FROM " + Visa.class.getName() + " WHERE nTarjeta== '" + numTarjeta + "'");
			
			List<Visa> visa1 = q.executeList();
			
			visa = visa1.get(0);
			
			
			pm.close();
		
			return visa;
		  }
		  
		  @POST
		  @Path("update")
		  @Produces(MediaType.APPLICATION_JSON)
		  public static void modificarProducto(Producto p) {
			  
			  String[] parts = p.getCodigo().split(",");
			  p.setCodigo(parts[0]);
			  PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			  PersistenceManager pm = pmf.getPersistenceManager();
			  Query<Producto> q = pm.newQuery("UPDATE " + Producto.class.getName() + "SET codigo== '"+ p.getCodigo() +"', nombre== '"+ p.getNombre() +"',descripcion== '"+ p.getDescripcion() +"',precio== '"+ p.getPrecio() +"',  WHERE codigo== '" + parts[1] + "'");
			  q.execute();
		  }
		  
	  
	 
	  
}
