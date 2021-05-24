package gui;

import categories.GuiTest;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jdo.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import sa02.Main;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Category(GuiTest.class)
public class VentanaComprasTest {
    private HttpServer server;
    private WebTarget appTarget;
    private Usuario usuario = Mockito.mock(Usuario.class);

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        appTarget = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }


    @Test
    public void testVentaProductoPayPal() {
        Paypal ppal = new Paypal("correo", "1234");
        when(usuario.getEmail()).thenReturn("correo");
        final WebTarget pagoTarget = appTarget.path("pagos");
        WebTarget pagoPaypalTarget = pagoTarget.path("paypali").queryParam("correo", usuario.getEmail());
        GenericType<Paypal> genericType = new GenericType<Paypal>() {};
        Paypal paypal = pagoPaypalTarget.request(MediaType.APPLICATION_JSON).get(genericType);
        assertEquals(ppal.getContrasena(), paypal.getContrasena());
    }

    @Test
    public void testVentaProductoVisa() {
        Visa visa = new Visa(123456789, "Jon", 123, "nunca");

        final WebTarget pagoTarget = appTarget.path("pagos");
        WebTarget pagoVisaTarget = pagoTarget.path("visai").queryParam("numTarjeta",
                visa.getnTarjeta());
        GenericType<Visa> genericType = new GenericType<Visa>() {};
        Visa visa_server = pagoVisaTarget.request(MediaType.APPLICATION_JSON).get(genericType);

        assertEquals(visa.getnTarjeta(), visa_server.getnTarjeta());
    }

    @Test
    public void testVentaProductoVentaProducto() {
        final WebTarget ventaProductoTarget = appTarget.path("ventasproductos");
        WebTarget insVentaProducto = ventaProductoTarget.path("ins");
        WebTarget allVentaProducto = ventaProductoTarget.path("all");
        Producto p = new Producto("Champinyon", "Fresco", 3, "javi", 34);
        when(usuario.getUsername()).thenReturn("javi");

        List vps = new ArrayList();
        vps.add(p.getNombre());
        vps.add(usuario.getUsername());
        vps.add(p.getCantidad());
        VentaProducto vp = new VentaProducto(p.getNombre(), usuario.getUsername(), 34);
        System.out.println(vp);
        insVentaProducto.request().post(Entity.entity(vps, MediaType.APPLICATION_JSON));

        GenericType<List<VentaProducto>> gType = new GenericType<List<VentaProducto>>() {};
        List<VentaProducto> ventasProductos = allVentaProducto.request(MediaType.APPLICATION_JSON).get(gType);
        assertEquals(vp.getUsuario(), ventasProductos.get(ventasProductos.size()-1).getUsuario());
        assertEquals(vp.getCantidad(), ventasProductos.get(ventasProductos.size()-1).getCantidad());
    }
}
