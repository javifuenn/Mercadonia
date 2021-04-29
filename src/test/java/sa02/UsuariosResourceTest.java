package sa02;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import gui.VentanaBusqueda;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;

@Category(IntegrationTest.class)
public class UsuariosResourceTest {
	
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
    
    
    @Test
    @PerfTest(invocations = 1000, threads = 40)
    public void testGetUsuarios() {
    	
    	WebTarget userTarget = appTarget.path("usuarios");
    	WebTarget userAllTarget = userTarget.path("all");
    	
    	List<Usuario> listUsuarios = Arrays.asList(
    			new Usuario("sergio", "1234"),
    			new Usuario("unai", "1234"),
    			new Usuario("javi", "4321"));
    			
    	GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
    	List<Usuario> usuarios = userAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
        assertEquals(listUsuarios.get(0).getUsername(), usuarios.get(0).getUsername());
        assertEquals(listUsuarios.get(1).getUsername(), usuarios.get(1).getUsername());
        assertEquals(listUsuarios.get(2).getUsername(), usuarios.get(2).getUsername());
    }

}
