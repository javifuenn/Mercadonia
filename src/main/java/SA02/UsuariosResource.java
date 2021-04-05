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
import jdo.Usuarios;

@Path("usuarios")
public class UsuariosResource {
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public static List<Usuarios> getUsuarios() {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Query<Usuarios> q = pm.newQuery(Usuarios.class);
		
		List<Usuarios> usuarios = q.executeList();
		
		pm.close();
	
		return usuarios;
  }

}
