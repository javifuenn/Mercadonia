package SA02;

import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jdo.Cesta;
import jdo.Producto;

@Path("productos")
public class ProductosResource {

	  @GET
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
	  @Produces(MediaType.APPLICATION_JSON)
	  public static List<Producto> getProductosNom(String nombre) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Query<Producto> q = pm.newQuery("SELECT FROM " + Producto.class.getName() + " WHERE nombre== '" + nombre + "'");
		
		List<Producto> productos = q.executeList();
		
		pm.close();
	
		return productos;
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
