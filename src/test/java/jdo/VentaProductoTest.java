package jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VentaProductoTest {
	
	private VentaProducto v;
	
	@Before
	public void crearVisa() {
		v = new VentaProducto("Pan","Jon",5);
	}
	
	@Test
	public void getUsuario() {
		assertEquals("Jon", v.getUsuario());
	}
	@Test
	public void getProducto() {
		assertEquals("Pan", v.getProducto());
	}
	@Test
	public void getCantidad() {
		assertEquals(5, v.getCantidad());
	}
	@Test
	public void setUsuario() {
		v.setUsuario("Izotz");
		assertEquals("Izotz", v.getUsuario());
	}
	@Test
	public void setProducto() {
		v.setProducto("Manzana");
		assertEquals("Manzana", v.getProducto());
	}
	@Test
	public void setCantidad() {
		v.setCantidad(10);
		assertEquals(10, v.getCantidad());
	}

}
