package jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CuponTest {
	
	private Cupon c;
	
	@Before
	public void crearCupon() {
       c =  new Cupon("descuento20", 20, "sergio");
    }
	
	@Test
	public void testGetTextoCupon() {
		assertEquals("descuento20", c.getTextoCupon());
	}
	
	@Test
	public void testGetporcentajeDescuento() {
		assertEquals(20, c.getPorcentajeDescuento());
	}
	@Test 
	public void testGetUsuario() {
		assertEquals("sergio", c.getUsuario());
	}
	
	@Test
	public void testSetTextoCupon() {
		c.setTextoCupon("descuento30");
		assertEquals("descuento30", c.getTextoCupon());
	}
	
	@Test
	public void testSetporcentajeDescuento() {
		c.setPorcentajeDescuento(30);
		assertEquals(30, c.getPorcentajeDescuento());
	}
	@Test
	public void testSetUsuario() {
		c.setUsuario("unai");
		assertEquals("unai", c.getUsuario());
	}

}
