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
		WebTarget userNomTarget = userTarget.path("nom").queryParam("nick", usuario1.get(0).getUsername());
		GenericType<Usuario> genericType = new GenericType<Usuario>() {};
		Usuario usuarios = userNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		if(usuarios.getPassword().equals(usuario1.get(0).getPassword())) {
			comp = true;
		}
		
		assertEquals(result, comp);
		
	}
	
	@Test
	public void testRegistro() {
		
		List<Usuario> usuario1 = Arrays.asList(
    			new Usuario("pepe", "1234"));
		
		WebTarget userRegTarget = userTarget.path("reg").queryParam("nick",usuario1.get(0).getUsername()).queryParam("contaseña", usuario1.get(0).getPassword());
		userRegTarget.request(MediaType.APPLICATION_JSON);
		
		WebTarget userNomTarget = userTarget.path("nom").queryParam("nick", "pepe");
		GenericType<Usuario> genericType = new GenericType<Usuario>() {};
		Usuario usuarios = userNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		
		assertEquals("pepe", usuarios.getUsername());
		
	}

}
