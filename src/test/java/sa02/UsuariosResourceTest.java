package sa02;

import static org.junit.Assert.assertEquals;

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
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;

@Category(IntegrationTest.class)
public class UsuariosResourceTest {
	
	private HttpServer server;
    private WebTarget target;
    Client cliente = ClientBuilder.newClient();
    final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
	final WebTarget userTarget = appTarget.path("usuarios");
    final WebTarget userAllTarget = userTarget.path("all");
    
    @Before
    public void setUp() throws Exception {

        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());
	   	
    }
    
    
    @Test
    public void testGetIt() {
    	
    	List<Usuario> listUsuarios = Arrays.asList(
    			new Usuario("sergio", "1234"),
    			new Usuario("unai", "1234"),
    			new Usuario("javi", "4321"));
    			
    	
    	//List<Usuario> usuarios = UsuariosResource.getUsuarios();
    	GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
    	List<Usuario> usuarios = userAllTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
        assertEquals(listUsuarios.get(0).getUsername(), usuarios.get(0).getUsername());
        assertEquals(listUsuarios.get(1).getUsername(), usuarios.get(1).getUsername());
        assertEquals(listUsuarios.get(2).getUsername(), usuarios.get(2).getUsername());
    }

}
