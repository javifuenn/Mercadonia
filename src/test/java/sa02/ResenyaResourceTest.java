package sa02;

import static org.junit.Assert.*;

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
import jdo.Producto;
import jdo.Resenya;

@Category(IntegrationTest.class)
public class ResenyaResourceTest {

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
    
    @Test
    public void testGetResenya() {
    	WebTarget resenyaTarget = appTarget.path("resenya");
	    WebTarget resenyaNomTarget = resenyaTarget.path("nom").queryParam("producto", "Lechuga");
	    
	    List<Resenya> r = Arrays.asList(new Resenya("Lechuga", "unai", 4, "Estaba bien"));
	    
	    GenericType<List<Resenya>> genericType = new GenericType<List<Resenya>>() {};
   	 	List<Resenya> resenyas = resenyaNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
   	 	
   	 	assertEquals(r.get(0).getUsuario(), resenyas.get(0).getUsuario());
    }
    
    @Test
    public void testInsertarResenya() {
    	WebTarget resenyaTarget = appTarget.path("resenya");
    	WebTarget resenyaInsTarget = resenyaTarget.path("add");
    	List<String> listRes = Arrays.asList("Zanahoria", "sergio", String.valueOf(1), "Muy mala");
    	resenyaInsTarget.request().post(Entity.entity(listRes, MediaType.APPLICATION_JSON));
    	
    	WebTarget resenyaNomTarget = resenyaTarget.path("nom").queryParam("producto", "Zanahoria");
    	GenericType<List<Resenya>> genericType = new GenericType<List<Resenya>>() {};
    	List<Resenya> resenya = resenyaNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
    	assertEquals("Zanahoria", resenya.get(0).getProducto());
    }

}
