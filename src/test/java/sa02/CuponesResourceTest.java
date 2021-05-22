package sa02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Cupon;



public class CuponesResourceTest {
	
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
    public void testAnadirProducto() {
    	WebTarget cupontarget = appTarget.path("cupones");
    	Cupon cupon = new Cupon("Test", 5, "testeando");
    	WebTarget anadir = cupontarget.path("anadir");
		anadir.request().post(Entity.entity(cupon, MediaType.APPLICATION_JSON));
		
		WebTarget vertarget = cupontarget.path("buscar").queryParam("Usuario", "testeando");
		
		GenericType<ArrayList<Cupon>> genericType = new GenericType<ArrayList<Cupon>>() {
		};
		ArrayList<Cupon> cupones = vertarget.request(MediaType.APPLICATION_JSON).get(genericType);

		
		
		assertEquals(cupon.getTextoCupon(), cupones.get(0).getTextoCupon());
    	
    }
}
