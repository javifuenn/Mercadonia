package SA02;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.junit.Test;

import gui.VentanaLogin;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Usuario;

public class VentanaLoginTest {
	
	Client cliente = ClientBuilder.newClient();
    final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget userTarget = appTarget.path("usuarios");
    final WebTarget userAllTarget = userTarget.path("all");
	
	@Test
	public void testLogin() {
		
		List<Usuario> usuario1 = Arrays.asList(
    			new Usuario("sergio", "1234"));
		VentanaLogin vent = new VentanaLogin();
		boolean result = vent.login("sergio", "1234");
		boolean comp = false;
		GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
		//Usuario usuarios = UsuariosResource.getUsuariosLogin(usuario1.get(0).getUsername());
		Usuario usuarios = userTarget.request(MediaType.APPLICATION_JSON).get(genericType).get(0);
		if(usuarios.getPassword().equals(usuario1.get(0).getPassword())) {
			comp = true;
		}
		
		assertEquals(result, comp);
		
	}
	
	@Test
	public void testRegistro() {
		
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Usuario usuario1 = new Usuario("pepe", "1234");
			pm.makePersistent(usuario1);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		
		boolean comp = false;
		GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
		Usuario usuarios = userTarget.request(MediaType.APPLICATION_JSON).get(genericType).get(0);
		//Usuario usuarios = UsuariosResource.getUsuariosLogin("pepe");
		if(usuarios.getPassword().equals("1234")) {
			comp = true;
		}
		
		assertEquals(true, comp);
		
	}

}
