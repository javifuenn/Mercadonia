package gui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import categories.GuiTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;
import sa02.Main;

@Category(GuiTest.class)
public class VentanaVerificarCodigoTest {

	private HttpServer server;
	private WebTarget appTarget;
	private WebTarget usuarioTarget;
	
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
	        usuarioTarget = appTarget.path("usuarios");
	    }

	    @After
	    public void tearDown() throws Exception {
	    	
	    	List<String> user = Arrays.asList("pepe");
	    	WebTarget userElimTarget = usuarioTarget.path("elim");
	    	userElimTarget.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));
	    	
	        server.stop();
	    }
		@Test
		public void testCrearCuenta() {
			
			VentanaVerificarCodigo v = new VentanaVerificarCodigo();
			v.crearCuenta("pepe", "123456", "pepe@gmail.com");
			
			 
			WebTarget usuarioNomTarget = usuarioTarget.path("nom").queryParam("nick", "pepe");
			GenericType<Usuario> genericType = new GenericType<Usuario>() {
			};
			Usuario u = usuarioNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
			
			assertEquals("pepe", u.getUsername());

		}

}
