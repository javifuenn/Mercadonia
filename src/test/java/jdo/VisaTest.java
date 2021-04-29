package jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VisaTest {
	
	private Visa v;
	
	@Before
	public void crearVisa() {
		v = new Visa(12345, "sergio", 333, "1/01/2022");
	}
	
	@Test
	public void getnTarjeta() {
		assertEquals(12345, v.getnTarjeta());
	}
	@Test
	public void getTitular() {
		assertEquals("sergio", v.getTitular());
	}
	@Test
	public void getCV() {
		assertEquals(333, v.getCv());
	}
	@Test
	public void getfCaducidad() {
		assertEquals("1/01/2022", v.getfCaducidad());
	}
	@Test
	public void setnTarjeta() {
		v.setnTarjeta(54321);
		assertEquals(54321, v.getnTarjeta());
	}
	@Test
	public void setTitular() {
		v.setTitular("unai");
		assertEquals("unai", v.getTitular());
	}
	@Test
	public void setCV() {
		v.setCv(111);
		assertEquals(111, v.getCv());
	}
	@Test
	public void setfCaducidad() {
		v.setfCaducidad("2/01/2022");
		assertEquals("2/01/2022", v.getfCaducidad());
	}

}
