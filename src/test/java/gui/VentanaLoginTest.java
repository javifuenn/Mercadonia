package gui;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.GuiTest;
import categories.IntegrationTest;
import gui.VentanaLogin;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Usuario;
import sa02.Main;

@Category(GuiTest.class)
public class VentanaLoginTest {
	
	private HttpServer server;
	private WebTarget appTarget;

	
    
    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        appTarget = c.target(Main.BASE_URI);

    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
    
	@Test
	public void testLogin() {
		
		WebTarget userTarget = appTarget.path("usuarios");
	    WebTarget userAllTarget = userTarget.path("all");
		
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
	

}
