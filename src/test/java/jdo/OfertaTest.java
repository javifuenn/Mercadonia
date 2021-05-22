package jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class OfertaTest {

    private Oferta o;

    @Before
    public void crearOferta() {
        o =  new Oferta("Pan", 1, new Date(2021, 5, 23));
    }

    @Test
    public void testGetProducto() {
        assertEquals("Pan", o.getProducto());
    }

    @Test
    public void testGetPrecio() {
        assertEquals(1, o.getPrecio(), 0.001);
    }
    @Test
    public void testGetFecha() {
        assertEquals(new Date(2021,5,23), o.getFecha());
    }

    @Test
    public void testSetProducto() {
        o.setProducto("Zanahoria");
        assertEquals("Zanahoria", o.getProducto());
    }

    @Test
    public void testSetPrecio() {
        o.setPrecio(230.3);
        assertEquals(230.3, o.getPrecio(), 0.001);
    }
    @Test
    public void testSetFecha() {
        o.setFecha(new Date(2021, 5, 23));
        assertEquals(new Date(2021, 5, 23),o.getFecha());
    }

}
