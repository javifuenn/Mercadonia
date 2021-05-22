package gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
import jdo.Resenya;
import jdo.Usuario;
import sa02.Main;

@Category(GuiTest.class)
public class VentanaResenyaTest {

	
	private HttpServer server;
	private WebTarget appTarget;
	WebTarget productTarget, resenyaTarget;
	private Usuario usuario = Mockito.mock(Usuario.class);
	private JTextField textField = Mockito.mock(JTextField.class);
	private JTextField textField_1 = Mockito.mock(JTextField.class);
	private JTextField textField_2 = Mockito.mock(JTextField.class);
	private JTextField textField_3 = Mockito.mock(JTextField.class);
	private JRadioButton RadioBtn = Mockito.mock(JRadioButton.class);
	
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
	        resenyaTarget = appTarget.path("resenya");
	    }

	    @After
	    public void tearDown() throws Exception {
	    	
	    	List<String> prod = Arrays.asList("Pera");
	    	WebTarget productElimTarget = productTarget.path("elim");
	    	productElimTarget.request().post(Entity.entity(prod, MediaType.APPLICATION_JSON));
	    	
	        server.stop();
	    }
	@Test
	public void testSubirResenya() {
		when(textField.getText()).thenReturn("Texto Comentado de prueba");
		Producto prod = new Producto("Patatas", "Para freir", 1.1, "javi", 75, true);
		Usuario usuario = new Usuario("Jon", "1234","jonander.medina@opendeusto.es");
		Resenya res = new Resenya("Pera", "Jon", 1, textField.getText());
		List<String> resenya = new ArrayList<>();
		resenya.add(res.getProducto());
		resenya.add(res.getUsuario());
		resenya.add(String.valueOf(res.getCalificacion()));
		resenya.add(res.getOpinion());
		WebTarget productInsTarget = resenyaTarget.path("add");
		productInsTarget.request().post(Entity.entity(resenya, MediaType.APPLICATION_JSON));
		assertEquals("Texto Comentado de prueba", res.getOpinion());
	}

}
