package sa02;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.databene.contiperf.junit.ContiPerfRule;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Usuario;
import jdo.VentaProducto;

@Category(IntegrationTest.class)
public class ventaProductosResource {
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	private HttpServer server;
    private WebTarget appTarget;
    private Client c;
    
    @Before
    public void setUp() throws Exception {
    	
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
    public void testgetVentaProducto() {
    	WebTarget ventaProductosTarget = appTarget.path("ventasproductos");
    	WebTarget ventaProductosAllTarget = ventaProductosTarget.path("all");
    	
    	List<VentaProducto> listVen = Arrays.asList(new VentaProducto("Manzana", "unai", 2));
    	
    	GenericType<List<VentaProducto>> genericType = new GenericType<List<VentaProducto>>() {};
    	List<VentaProducto> venta = ventaProductosAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
    	assertEquals(listVen.get(0).getProducto(), venta.get(0).getProducto());
    }

}
