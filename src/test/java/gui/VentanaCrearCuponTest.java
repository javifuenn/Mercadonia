package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.GuiTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.Cupon;
import sa02.Main;

@Category(GuiTest.class)
public class VentanaCrearCuponTest {
	
	private HttpServer server;
	private WebTarget appTarget;
	
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
	 public void testCrearCupon() {
		 WebTarget cuponTarget = appTarget.path("cupones");
		 
		 Cupon cuponn = new Cupon("descuento70", 70, "javi");
	     WebTarget anadirr = cuponTarget.path("anadir");
		 anadirr.request().post(Entity.entity(cuponn, MediaType.APPLICATION_JSON));
		 
		 WebTarget vertarget = cuponTarget.path("buscar").queryParam("Usuario", "javi");

		 GenericType<ArrayList<Cupon>> genericType = new GenericType<ArrayList<Cupon>>() {};
		 ArrayList<Cupon> cupones = vertarget.request(MediaType.APPLICATION_JSON).get(genericType);

		 assertEquals(cuponn.getTextoCupon(), cupones.get(0).getTextoCupon());
	 }
	 
	 @Test
	 public void testBorrarCupon() {
		 WebTarget cuponTarget = appTarget.path("cupones");
		 String cupon = "descuento10";
		 WebTarget buscarcupon = cuponTarget.path("buscar1").queryParam("nombrecupon", cupon);
		 GenericType<Cupon> genericType = new GenericType<Cupon>() {};
		 Cupon cup = new Cupon();
		 cup = buscarcupon.request(MediaType.APPLICATION_JSON).get(genericType);
		 
		 WebTarget productElimTarget = cuponTarget.path("borrar");
	     productElimTarget.request().post(Entity.entity(cup, MediaType.APPLICATION_JSON));
	     
	     WebTarget buscarcupon2 = cuponTarget.path("buscar1").queryParam("nombrecupon", cupon);
		 GenericType<Cupon> genericType2 = new GenericType<Cupon>() {};
		 Cupon cup2 = new Cupon();
		 cup2 = buscarcupon2.request(MediaType.APPLICATION_JSON).get(genericType2);
	     
		 assertTrue(cup2 == null);
	     
	 }
	    
	    

}
