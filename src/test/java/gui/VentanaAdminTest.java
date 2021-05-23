package gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextField;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import categories.GuiTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.Usuario;
import sa02.Main;

@Category(GuiTest.class)
public class VentanaAdminTest {

	
	private HttpServer server;
	private WebTarget appTarget;
	WebTarget productTarget;
	private Usuario usuario = Mockito.mock(Usuario.class);
	private JTextField textField = Mockito.mock(JTextField.class);
	private JTextField textField_1 = Mockito.mock(JTextField.class);
	private JTextField textField_2 = Mockito.mock(JTextField.class);
	private JTextField textField_3 = Mockito.mock(JTextField.class);
	private JTextField codigo = Mockito.mock(JTextField.class);
	private JTextField nombre = Mockito.mock(JTextField.class);
	private JTextField descripcion = Mockito.mock(JTextField.class);
	private JTextField precio = Mockito.mock(JTextField.class);
	
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
	        productTarget = appTarget.path("productos");
	    }

	    @After
	    public void tearDown() throws Exception {
	    	
	    	List<String> prod = Arrays.asList("Pera");
	    	WebTarget productElimTarget = productTarget.path("elim");
	    	productElimTarget.request().post(Entity.entity(prod, MediaType.APPLICATION_JSON));
	    	
	        server.stop();
	    }
	    
	@Test
	public void testAñadirProducto() {
		List<String> productoL = new ArrayList<>();
		when(textField.getText()).thenReturn("Pera");
		productoL.add(textField.getText());
		when(textField_1.getText()).thenReturn("Rica");
		productoL.add(textField_1.getText());
		when(textField_2.getText()).thenReturn("3");
		productoL.add(textField_2.getText());
		when(usuario.getUsername()).thenReturn("unai");
		productoL.add(usuario.getUsername());
		when(textField_3.getText()).thenReturn("20");
		productoL.add(textField_3.getText());
		WebTarget productInsTarget = productTarget.path("ins");
		productInsTarget.request().post(Entity.entity(productoL, MediaType.APPLICATION_JSON));
		
		WebTarget productNomTarget = productTarget.path("nom").queryParam("nombre", "Pera");
		GenericType<List<Producto>> genericType = new GenericType<List<Producto>>() {
		};
		List<Producto> productos = productNomTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		
		assertEquals("Pera", productos.get(0).getNombre());
	}
	
	@Test
	public void testModificarProducto() {
		Producto prod= new Producto();
		when(codigo.getText()).thenReturn("1");
		prod.setCodigo(codigo.getText());
		when(nombre.getText()).thenReturn("Pera");
		prod.setNombre(nombre.getText());
		when(descripcion.getText()).thenReturn("Producto prueba");
		prod.setCodigo(descripcion.getText());
		when(precio.getText()).thenReturn("12.2");
		prod.setPrecio(Double.parseDouble(precio.getText()));
		
		assertEquals("Pera", prod.getNombre());
	}
	
	@Test
	public void testModificarUsuario() {
		Usuario prod= new Usuario();
		when(codigo.getText()).thenReturn("Jon");
		prod.setUsername(codigo.getText());
		when(nombre.getText()).thenReturn("1234");
		prod.setPassword(nombre.getText());
		when(descripcion.getText()).thenReturn("jonander.medina@opendeusto.es");
		prod.setEmail(descripcion.getText());
		
		assertEquals("jonander.medina@opendeusto.es", prod.getEmail());
	}
	
	@Test
	public void testActualizarProducto() {
		
		Producto prod= new Producto();
		when(codigo.getText()).thenReturn("1");
		prod.setCodigo(codigo.getText());
		when(nombre.getText()).thenReturn("Pan");
		prod.setNombre(nombre.getText());
		when(descripcion.getText()).thenReturn("Recien horneado");
		prod.setCodigo(descripcion.getText());
		when(precio.getText()).thenReturn("12.2");
		prod.setPrecio(Double.parseDouble(precio.getText()));
		
		
		assertEquals("Pan", prod.getNombre());
	}

}
