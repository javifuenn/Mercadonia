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
import jdo.Usuario;

@Path("usuarios")
public class UsuariosResource {
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public static List<Usuario> getUsuarios() {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Query<Usuario> q = pm.newQuery(Usuario.class);
		
		List<Usuario> usuarios = q.executeList();
		
		pm.close();
	
		return usuarios;
  }
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public static Usuario getUsuariosLogin(String nick) {
	   	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Usuario usuarios = null;
		
		Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE username== '" + nick + "'");
		
		List<Usuario> usuariosl = q.executeList();
		
		usuarios = usuariosl.get(0);
		
		System.out.println(usuarios);
		
		pm.close();
	
		return usuarios;
	  }
}
