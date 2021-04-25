package SA02;

import java.util.List;

import javax.jdo.Extent;
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
import jdo.Cesta;
import jdo.Producto;
import jdo.Usuario;

@Path("productos")
public class ProductosResource {

	  @GET
	  @Path("all")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static List<Producto> getProductos() {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Query<Producto> q = pm.newQuery(Producto.class);
		
		List<Producto> productos = q.executeList();
		
		pm.close();
	
		return productos;
    }
	  
	  @GET
	  @Path("nom")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static List<Producto> getProductosNom(@QueryParam("nombre") String nombre) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Query<Producto> q = pm.newQuery("SELECT FROM " + Producto.class.getName() + " WHERE nombre== '" + nombre + "'");
		
		List<Producto> productos = q.executeList();
		
		pm.close();
	
		return productos;
	  }
	  
	  @GET
	  @Path("user")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static List<Producto> getProductosUser(@QueryParam("usuario") String usuario) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Query<Producto> q = pm.newQuery("SELECT FROM " + Producto.class.getName() + " WHERE usuario== '" + usuario + "'");
		
		List<Producto> productos = q.executeList();
		
		pm.close();
	
		return productos;
	  }
	  
	  @POST
	  @Path("elim")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static void eliminarProducto(List<String> productoL) {
		  String nombre = productoL.get(0);
		  PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		  PersistenceManager pm = pmf.getPersistenceManager();
		  Query<Producto> q = pm.newQuery("DELETE FROM " + Producto.class.getName() + " WHERE nombre== '" + nombre + "'");
		  q.execute();
	  }
	  
	  @POST
	  @Path("ins")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static void insertarProducto(List<String> productoL) {
		  String nombre = productoL.get(0);
		  String descripcion = productoL.get(1);
		  String precio = productoL.get(2);
		  String usuario = productoL.get(3);
		  
		  PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		  PersistenceManager pm = pmf.getPersistenceManager();
		  Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Producto p = new Producto(nombre, descripcion, Double.parseDouble(precio), usuario);
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
	  @Produces(MediaType.APPLICATION_JSON)
	  public static List<Producto> getProductosCesta(List<Cesta> cesta) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Producto> productos;
		
		
		for(Cesta cestav: cesta) {
			
			Query<Producto> q = pm.newQuery("SELECT FROM " + Producto.class.getName() + " WHERE nombre== '" + cestav.getNombreproducto() + "'");
			
			List<Producto> productosv = q.executeList();
			
			
		}
		
		pm.close();
	
		return null;
	  }
	  
}
