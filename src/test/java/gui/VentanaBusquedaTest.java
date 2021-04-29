package gui;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import gui.VentanaBusqueda;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jdo.Producto;
import jdo.Usuario;

@Category(IntegrationTest.class)
public class VentanaBusquedaTest {

	@Test
	public void test() {
		
		
		List<Producto> listProd = Arrays.asList(
    			new Producto("Manzana", "Deliciosa", 3, "sergio",555));
		Usuario usuario = new Usuario();
		VentanaBusqueda vent = new VentanaBusqueda(usuario);
		List<Producto> productos = vent.busquedaProd("Manzana");
		
		assertEquals(listProd.get(0).getNombre(), productos.get(0).getNombre());
		
	}

}
