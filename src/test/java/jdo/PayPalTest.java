package jdo;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PayPalTest {
private Paypal p;
	
	@Before
	public void crearProducto() {
		p = new Paypal("unai@gmail.com", "1234");
	}
	
	@Test
	public void testGetCorreo() {
		assertEquals("unai@gmail.com", p.getCorreo());
	}
	
	@Test
	public void testGetContrasena() {
		assertEquals("1234", p.getContrasena());
	}
	
	@Test
	public void testSetCorreo() {
		p.setCorreo("unai@gmail.es");
		assertEquals("unai@gmail.es", p.getCorreo());
	}
	
	@Test
	public void testSetContrasena() {
		p.setContrasena("4321");
		assertEquals("4321", p.getContrasena());
	}	
	

}
