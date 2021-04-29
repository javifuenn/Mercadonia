package gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import categories.IntegrationTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;
import sa02.Main;

@Category(IntegrationTest.class)
public class VentanaVentaProductosTest {
	
	private HttpServer server;
	private WebTarget appTarget;
	private Usuario usuario = Mockito.mock(Usuario.class);
	
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
	public void testVentaProducto() {
		
		List<Producto> listProd = Arrays.asList(
    			new Producto("Lechuga", "Muy sana", 2.4, "unai",6));
		
		WebTarget productTarget = appTarget.path("productos");
		when(usuario.getUsername()).thenReturn("unai");
		WebTarget productUserTarget = productTarget.path("user").queryParam("usuario", usuario.getUsername());
		GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
		List<Producto> productos = productUserTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		
		assertEquals(listProd.get(0).getNombre(), productos.get(0).getNombre());
	}

}
