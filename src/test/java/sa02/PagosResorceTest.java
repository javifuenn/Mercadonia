package sa02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
import jdo.Paypal;
import jdo.Pedido;
import jdo.Producto;
import jdo.Visa;

@Category(IntegrationTest.class)
public class PagosResorceTest {
	
	private HttpServer server;
    private WebTarget appTarget;
    private Client c;
    @Rule public ContiPerfRule rule = new ContiPerfRule();
    
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
    public void testgetUsuarioPaypal() {
    	WebTarget pagosTarget = appTarget.path("pagos");
    	WebTarget pagosPaypaliTarget = pagosTarget.path("paypali").queryParam("correo", "correo");
    	 List<Paypal> listPedidos = Arrays.asList(
	    			new Paypal("correo", "1234"));
    	 
    	 GenericType<Paypal> genericType = new GenericType<Paypal>() {};
		 Paypal paypal = pagosPaypaliTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		 
		 assertEquals(listPedidos.get(0).getCorreo(), paypal.getCorreo());
    }
    @Test
    public void testgetPedido() {
    	WebTarget pagosTarget = appTarget.path("pagos");
    	WebTarget pagosPedidosTarget = pagosTarget.path("pedidos").queryParam("nombre", "sergio");
    	List<String> listProd = Arrays.asList("Lechuga", "Muy sana", "2.4", "unai", "55" );
		Pedido pedido = new Pedido("sergio", new Date(121,3,4), listProd, "universidad");
		GenericType<List<Pedido>> genericType = new GenericType<List<Pedido>>() {};
   	 	List<Pedido> pedidos = pagosPedidosTarget.request(MediaType.APPLICATION_JSON).get(genericType);
   	 	
   	 	assertEquals(pedido.getNombre(), pedidos.get(0).getNombre());
    }
    
    @Test
    public void testgetUsuarioVisa() {
    	WebTarget pagosTarget = appTarget.path("pagos");
    	WebTarget pagosVisaiTarget = pagosTarget.path("visai").queryParam("titular", "Jon");
    	Visa visaObj = new Visa(123456789, "Jon", 123, "nunca");
    	GenericType<Visa> genericType = new GenericType<Visa>() {};
		Visa visa = pagosVisaiTarget.request(MediaType.APPLICATION_JSON).get(genericType);
		    
		assertEquals(visaObj.getnTarjeta(), visa.getnTarjeta());
    }
    
    @Test
    public void testAñadirPedido() {
    	WebTarget pagosTarget = appTarget.path("pagos");
    	WebTarget pagosAñadirTarget = pagosTarget.path("añadir");
    	Date fecha2 = new Date(121,3,4);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String fechaPedido = sdf.format(fecha2);
    	List<String> listProd = Arrays.asList("Lechuga", "Muy sana", "2.4", "unai", "55" );
    	List<String> pedidoL = Arrays.asList("juan", fechaPedido, listProd.get(0), listProd.get(1), listProd.get(2), listProd.get(3),listProd.get(4),"universidad");
    	pagosAñadirTarget.request().post(Entity.entity(pedidoL, MediaType.APPLICATION_JSON));
    	
    	
    	WebTarget pagosPedidosTarget = pagosTarget.path("pedidos").queryParam("nombre", "juan");
    	GenericType<List<Pedido>> genericType = new GenericType<List<Pedido>>() {};
   	 	List<Pedido> pedidos = pagosPedidosTarget.request(MediaType.APPLICATION_JSON).get(genericType);
   	 	
   	 	System.out.println(pedidos.get(0));
   	 	assertEquals(pedidoL.get(0), pedidos.get(0).getNombre());
    	
    }
    
    

}
