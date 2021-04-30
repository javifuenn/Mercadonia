package sa02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import jakarta.ws.rs.client.Entity;
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
    
    @Test
    @PerfTest(invocations = 1000, threads = 40)
    public void testgetUsuariosLogin() {
    	WebTarget userTarget = appTarget.path("usuarios");
    	WebTarget userNomTarget = userTarget.path("nom").queryParam("nick", "sergio");
    	
    	List<Usuario> listUsuarios = Arrays.asList(
    			new Usuario("sergio", "1234"),
    			new Usuario("unai", "1234"),
    			new Usuario("javi", "4321"));
    	GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
    	List<Usuario> usuario = userNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
    	assertEquals(listUsuarios.get(0).getUsername(), usuario.get(0).getUsername());
    	
    }
    @Test
    @PerfTest(invocations = 1000, threads = 40)
    public void testInsertarUsuario() {
    	WebTarget userTarget = appTarget.path("usuarios");
    	WebTarget userRegTarget = userTarget.path("reg");
    	List<String> listuser = Arrays.asList("juan", "1111");
    	userRegTarget.request().post(Entity.entity(listuser, MediaType.APPLICATION_JSON));
    	
    	WebTarget userNomTarget = userTarget.path("nom").queryParam("nick", "juan");
    	GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
    	List<Usuario> usuario = userNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
    	assertTrue(!usuario.isEmpty());
    }
    @Test
    @PerfTest(invocations = 1000, threads = 40)
    public void testEliminarUsuario() {
    	List<String> listuser = Arrays.asList("sergio", "1234");
    	WebTarget userTarget = appTarget.path("usuarios");
    	WebTarget userElimTarget = userTarget.path("elim");
    	userElimTarget.request().post(Entity.entity(listuser, MediaType.APPLICATION_JSON));
    	
    	WebTarget userNomTarget = userTarget.path("nom").queryParam("nick", "sergio");
    	GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
    	List<Usuario> usuario = userNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
    	
    	assertTrue(usuario.isEmpty());
    	
    }
    @Test
    @PerfTest(invocations = 1000, threads = 40)
    public void testNomCheck() {
    	WebTarget userTarget = appTarget.path("usuarios");
    	WebTarget userNomCheckTarget = userTarget.path("nomcheck").queryParam("nick", "unai");
    	
    	GenericType<List<Usuario>> genericType = new GenericType<List<Usuario>>() {};
    	boolean usuario = userNomCheckTarget.request(MediaType.APPLICATION_JSON).get(new GenericType<Boolean>(){});
    	
    	assertEquals(true, usuario);
    }
    

}
