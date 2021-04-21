package SA02;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.VentanaBusqueda;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;

public class ProductosResourceTest {
	
	 	private HttpServer server;
	    private WebTarget target;
	    Client cliente = ClientBuilder.newClient();
	    final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
		final WebTarget productTarget = appTarget.path("productos");
	    final WebTarget productAllTarget = productTarget.path("all");
		
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

	        target = c.target(Main.BASE_URI);
		   
	       
	    	
	    }

	    @After
	    public void tearDown() throws Exception {
	        server.stop();
	    }

	    /**
	     * Test to see that the message "Got it!" is sent in the response.
	     */
	    @Test
	    public void testGetIt() {
	    	
	    	List<Producto> listProd = Arrays.asList(
	    			new Producto("1A", "Lechuga", "Muy sana", 2.4),
	    			new Producto("2A", "Manzana", "Deliciosa", 3),
	    			new Producto("3A", "Pan", "Recien horneado", 0.6));
	    	//List<Producto> productos = ProductosResource.getProductos();
	    	GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
	    	List<Producto> productos = productAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
	    	
	        assertEquals(listProd.get(0).getCodigo(), productos.get(0).getCodigo());
	        assertEquals(listProd.get(1).getCodigo(), productos.get(1).getCodigo());
	        assertEquals(listProd.get(2).getCodigo(), productos.get(2).getCodigo());
	    }
}


