package SA02;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import gui.VentanaBusqueda;
import jdo.Producto;

public class VentanaBusquedaTest {

	@Test
	public void test() {
		
		List<Producto> listProd = Arrays.asList(
    			new Producto("2A", "Manzana", "Deliciosa", 3));
		VentanaBusqueda vent = new VentanaBusqueda();
		List<Producto> productos = vent.busquedaProd("Manzana");
		
		assertEquals(listProd.get(0).getCodigo(), productos.get(0).getCodigo());
		
	}

}
