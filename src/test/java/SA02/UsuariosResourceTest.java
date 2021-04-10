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
import jdo.Usuarios;

public class UsuariosResourceTest {
	
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
    
    @Test
    public void testGetIt() {
    	
    	List<Usuarios> listUsuarios = Arrays.asList(
    			new Usuarios("javi", "4321"),
    			new Usuarios("sergio", "1234"),
    			new Usuarios("unai", "1234"));
    			
    	
    	List<Usuarios> usuarios = UsuariosResource.getUsuarios();
    
    	
        assertEquals(listUsuarios.get(0).getUsername(), usuarios.get(0).getUsername());
        assertEquals(listUsuarios.get(1).getUsername(), usuarios.get(1).getUsername());
        assertEquals(listUsuarios.get(2).getUsername(), usuarios.get(2).getUsername());
    }

}
