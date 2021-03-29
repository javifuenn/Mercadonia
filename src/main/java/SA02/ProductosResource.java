package SA02;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
}
