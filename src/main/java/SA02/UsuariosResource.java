package SA02;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.swing.JOptionPane;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jdo.Usuario;

@Path("usuarios")
public class UsuariosResource {
	  @GET
	  @Path("all")
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
	  @Path("nom")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static Usuario getUsuariosLogin(@QueryParam("nick") String nick) {
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
	  
	  @POST
	  @Path("reg")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public static void insertarUsuario(List<String> usuarioL) {
		  	String nick = usuarioL.get(0);
		  	String contraseña = usuarioL.get(1);
		  	PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Usuario usuario1 = new Usuario(nick, contraseña);
				pm.makePersistent(usuario1);
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
			
		} 
	  
	  @POST
	  @Path("elim")
	  @Produces(MediaType.APPLICATION_JSON)
	  public static void eliminarusuario(@QueryParam("nick") String nick) {
		  PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		  PersistenceManager pm = pmf.getPersistenceManager();
		  
		  Query<Usuario> q = pm.newQuery("DELETE FROM " + Usuario.class + " WHERE username== '" + nick + "'");
		  q.execute();
		  
	  }
	  
}
