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
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public static Usuarios getUsuariosLogin(String nick) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Usuarios usuarios = null;
		
		Query<Usuarios> q = pm.newQuery("SELECT FROM " + Usuarios.class.getName() + " WHERE username== '" + nick + "'");
		
		List<Usuarios> usuariosl = q.executeList();
		
		usuarios = usuariosl.get(0);
		
		System.out.println(usuarios);
		
		pm.close();
	
		return usuarios;
	  }
}
