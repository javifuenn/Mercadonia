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
import jdo.Cesta;
import jdo.Producto;
import jdo.Usuario;
import jdo.Paypal;

@Category(IntegrationTest.class)
public class CestaResourceTest {

	private HttpServer server;
	private WebTarget appTarget;
	private Client c;
	@Rule
	public ContiPerfRule rule = new ContiPerfRule();

	@Before
	public void setUp() throws Exception {
		server = Main.startServer();
		c = ClientBuilder.newClient();
		appTarget = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	@Test
	public void testverProdcutosCesta() {
		WebTarget cestaTarget = appTarget.path("cesta");
		WebTarget abuscarTarget = cestaTarget.path("buscar").queryParam("Usuario", "unai");

		List<Producto> listCesta = Arrays.asList(new Producto("Manzana", "Deliciosa", 3, "sergio", 90));

		GenericType<ArrayList<Producto>> genericType = new GenericType<ArrayList<Producto>>() {
		};
		ArrayList<Producto> cesta = abuscarTarget.request(MediaType.APPLICATION_JSON).get(genericType);

		assertEquals(listCesta.get(0).getNombre(), cesta.get(0).getNombre());
		assertEquals(listCesta.get(0).getCantidad(), cesta.get(0).getCantidad());
		assertEquals(listCesta.get(0).getDescripcion(), cesta.get(0).getDescripcion());
		assertEquals(listCesta.get(0).getUsuario(), cesta.get(0).getUsuario());
	}

	@Test
	public void testContador() throws InterruptedException {
		Thread.sleep(2000);
		WebTarget cestaTarget = appTarget.path("cesta");
		WebTarget contarTarget = cestaTarget.path("contar").queryParam("Usuario", "unai");

		int contado = 1;

		GenericType<Integer> genericType = new GenericType<Integer>() {
		};
		int contar = contarTarget.request(MediaType.APPLICATION_JSON).get(genericType);

		assertEquals(contado, contar);

	}
	
	

}
