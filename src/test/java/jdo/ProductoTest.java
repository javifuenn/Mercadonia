package jdo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProductoTest {

	private Producto p;
	
	@Before
	public void crearProducto() {
		p = new Producto("Lechuga", "Rica", 2.3, "javi", 20);
	}
	
	@Test
	public void testGetNombre() {
		assertEquals("Lechuga", p.getNombre());
	}
	
	@Test
	public void testGetDescipcion() {
		assertEquals("Rica", p.getDescripcion());
	}
	
	@Test
	public void testGetPrecio() {
		assertEquals(2.3, p.getPrecio(), 0.001);
	}
	
	@Test
	public void testGetUsuario() {
		assertEquals("javi", p.getUsuario());
	}
	
	@Test
	public void testGetCantidad() {
		assertEquals(20, p.getCantidad());
	}
	
	@Test
	public void testSetNombre() {
		p.setNombre("Manzana");
		assertEquals("Manzana", p.getNombre());
	}
	
	@Test
	public void testSetDescipcion() {
		p.setDescripcion("Muy rica");
		assertEquals("Muy rica", p.getDescripcion());
	}
	
	@Test
	public void testSetPrecio() {
		p.setPrecio(2.6);
		assertEquals(2.6, p.getPrecio(), 0.001);
	}
	
	@Test
	public void testSetUsuario() {
		p.setUsuario("unai");
		assertEquals("unai", p.getUsuario());
	}
	
	@Test
	public void testSetCantidad() {
		p.setCantidad(10);
		assertEquals(10, p.getCantidad());
	}
	
}
