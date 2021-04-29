package gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import categories.GuiTest;
import categories.IntegrationTest;
import gui.VentanaBusqueda;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;
import sa02.Main;

@Category(GuiTest.class)
public class VentanaBusquedaTest {
	
	private HttpServer server;
	private WebTarget appTarget;
	private Usuario usuario = Mockito.mock(Usuario.class);
	private Producto producto = Mockito.mock(Producto.class);
	private static int cantidad;

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

        appTarget = c.target(Main.BASE_URI);

    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

	@Test
	public void testBusquedaProd() {
		
		List<Producto> listProd = Arrays.asList(
    			new Producto("Manzana", "Deliciosa", 3, "sergio",555));
		Usuario us = new Usuario();
		VentanaBusqueda vent = new VentanaBusqueda(us);
		List<Producto> productos = vent.busquedaProd("Manzana");
		
		assertEquals(listProd.get(0).getNombre(), productos.get(0).getNombre());
		
	}
	
	@Test
	public void testAñadir() {
		
		WebTarget cestaTarget = appTarget.path("cesta");
		
		when(usuario.getUsername()).thenReturn("unai");
		String nombreusuario = usuario.getUsername();
		when(producto.getNombre()).thenReturn("Manzana");
		String nombreproducto = producto.getNombre();

		WebTarget anadirTarget = cestaTarget.path("anadir").queryParam("Producto", nombreproducto).queryParam("Usuario", nombreusuario);
		GenericType<Boolean> genericType5 = new GenericType<Boolean>() {
		};
		boolean respuesta = anadirTarget.request(MediaType.APPLICATION_JSON).get(genericType5);
		
		assertEquals(true, respuesta);
	}
	
	@Test
	public void testContar() {
		
		WebTarget cestaTarget = appTarget.path("cesta");
		WebTarget contarTarget = cestaTarget.path("contar").queryParam("Usuario", usuario.getUsername());
		GenericType<Integer> genericType7 = new GenericType<Integer>() {
		};
		cantidad = contarTarget.request(MediaType.APPLICATION_JSON).get(genericType7);
		
		assertEquals(0, cantidad);
	}

}
