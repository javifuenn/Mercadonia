package sa02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import gui.VentanaBusqueda;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;

@Category(IntegrationTest.class)
public class ProductosResourceTest {
	
	 	private HttpServer server;
	    private WebTarget appTarget;
	    private Client c;

		
	    @Before
	    public void setUp() throws Exception {
	    	server = Main.startServer();
	        c = ClientBuilder.newClient();

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


	    /**
	     * Test to see that the message "Got it!" is sent in the response.
	     */
	    @Test
	    public void testGetIt() {
	    	WebTarget productTarget = appTarget.path("productos");
		    WebTarget productAllTarget = productTarget.path("all");
	    	
	    	List<Producto> listProd = Arrays.asList(
	    			new Producto("Lechuga", "Muy sana", 2.4, "unai",55),
	    			new Producto("Manzana", "Deliciosa", 3, "sergio",55),
	    			new Producto("Pan", "Recien horneado", 0.6, "javi",55));

	    	GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
	    	List<Producto> productos = productAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
	    	
	        assertEquals(listProd.get(0).getNombre(), productos.get(0).getNombre());
	        assertEquals(listProd.get(1).getNombre(), productos.get(1).getNombre());
	        assertEquals(listProd.get(2).getNombre(), productos.get(2).getNombre());
	    }
	    @Test
	    public void testgetNombreProductos() {
	    	WebTarget productTarget = appTarget.path("productos");
		    WebTarget productNomTarget = productTarget.path("nom").queryParam("nombre", "Lechuga");
		    
		    List<Producto> listProd = Arrays.asList(
	    			new Producto("Lechuga", "Muy sana", 2.4, "unai",55),
	    			new Producto("Manzana", "Deliciosa", 3, "sergio",55),
	    			new Producto("Pan", "Recien horneado", 0.6, "javi",55));
		    
		    GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
		    List<Producto> producto = productNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		    
		    assertEquals(listProd.get(0).getNombre(), producto.get(0).getNombre());
	    }
	    @Test
	    public void testgetProductosUser() {
	    	WebTarget productTarget = appTarget.path("productos");
	    	WebTarget productUserTarget = productTarget.path("user").queryParam("usuario", "unai");
	    	
	    	List<Producto> listProd = Arrays.asList(
	    			new Producto("Lechuga", "Muy sana", 2.4, "unai",55),
	    			new Producto("Manzana", "Deliciosa", 3, "sergio",55),
	    			new Producto("Pan", "Recien horneado", 0.6, "javi",55));
	    	
	    	 GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
	    	 List<Producto> productos = productUserTarget.request(MediaType.APPLICATION_JSON).get(genericType);
	    	 
	    	 assertEquals(listProd.get(0).getNombre(), productos.get(0).getNombre());
	    }
	    @Test
	    public void testEliminarProducto() {
	    	List<String> listProd = Arrays.asList("Lechuga", "Muy sana", "2.4", "unai", "55" );
	    	WebTarget productTarget = appTarget.path("productos");
	    	WebTarget productElimTarget = productTarget.path("elim");
	    	productElimTarget.request().post(Entity.entity(listProd, MediaType.APPLICATION_JSON));
	    	
	    	WebTarget productNomTarget = productTarget.path("nom").queryParam("nombre", "Lechuga");
	    	GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
	    	List<Producto> producto = productNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
	    	
	    	assertTrue(producto.isEmpty());
	    }
	    @Test
	    public void testInsertarProducto() {
	    	WebTarget productTarget = appTarget.path("productos");
	    	WebTarget productInsTarget = productTarget.path("ins");
	    	List<String> listProd = Arrays.asList("Zanahoria", "Muy sana", "2.4", "unai", "55" );
	    	productInsTarget.request().post(Entity.entity(listProd, MediaType.APPLICATION_JSON));
	    	
	    	WebTarget productNomTarget = productTarget.path("nom").queryParam("nombre", "Zanahoria");
	    	GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {};
	    	List<Producto> producto = productNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
	    	
	    	assertTrue(!producto.isEmpty());
	    	
	    }
}


