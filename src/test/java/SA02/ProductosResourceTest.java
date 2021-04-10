package SA02;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jdo.Producto;

public class ProductosResourceTest {
	
	 	private HttpServer server;
	    private WebTarget target;

		
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
	    	
	    	List<Producto> productos = ProductosResource.getProductos();
	    
	    	
	        assertEquals(listProd.get(0).getCodigo(), productos.get(0).getCodigo());
	        assertEquals(listProd.get(1).getCodigo(), productos.get(1).getCodigo());
	        assertEquals(listProd.get(2).getCodigo(), productos.get(2).getCodigo());
	    }
}


