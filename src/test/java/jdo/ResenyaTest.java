package jdo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ResenyaTest {
	private Resenya r;
	
	@Before
	public void crearCupon() {
       r =  new Resenya("Manzana", "unai", 1, "Muy rica");
    }
	
	@Test
	public void testGetProducto() {
		assertEquals("Manzana", r.getProducto());;
	}
	
	@Test
	public void testGetUsuario() {
		assertEquals("unai", r.getUsuario());;
	}
	
	@Test
	public void testGetCalificacion() {
		assertEquals(1, r.getCalificacion());;
	}
	
	@Test
	public void testGetOpinion() {
		assertEquals("Muy rica", r.getOpinion());;
	}
	
	@Test
	public void testSetProducto() {
		r.setProducto("Lechuga");
		assertEquals("Lechuga", r.getProducto());;
	}
	
	@Test
	public void testSetUsuario() {
		r.setUsuario("sergio");
		assertEquals("sergio", r.getUsuario());;
	}
	
	@Test
	public void testSetCalificacion() {
		r.setCalificacion(5);
		assertEquals(5, r.getCalificacion());;
	}
	
	@Test
	public void testSetOpinion() {
		r.setOpinion("Muy mala");
		assertEquals("Muy mala", r.getOpinion());;
	}

}
