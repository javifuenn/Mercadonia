/*
 * package jdo;
 * 
 * import static org.junit.Assert.*;
 * 
 * import java.util.Date;
 * 
 * import javax.mail.Address;
 * 
 * import org.junit.Before; import org.junit.Test;
 * 
 * public class MailTest {
 * 
 * private Mail c;
 * 
 * 
 * @Before public void crearProducto() { Address a = null; c = new Mail(new
 * Date(121, 10, 1),a , "jon", "Contenido","Mensaje prueba"); }
 * 
 * 
 * @Test public void testGetDate() { assertEquals(new Date(121, 10, 1),
 * c.getDate()); }
 * 
 * 
 * @Test public void testSetDate() { c.setDate(new Date(121, 10, 1));
 * assertEquals(new Date(121, 10, 1), c.getDate()); }
 * 
 * @Test public void testGetFrom() { assertEquals(null, c.getFrom()); }
 * 
 * @Test public void testSetFrom() { c.setFrom(null); assertEquals(null,
 * c.getFrom()); }
 * 
 * @Test public void testGetSubject() { assertEquals("jon", c.getSubject()); }
 * 
 * @Test public void testSetSubject() { c.setSubject("jon"); assertEquals("jon",
 * c.getSubject()); }
 * 
 * @Test public void testGetContent() { assertEquals("Contenido",
 * c.getContent()); }
 * 
 * @Test public void testSetContent() { c.setContent("Contenido");
 * assertEquals("Contenido", c.getContent()); }
 * 
 * @Test public void testGetMessage() { assertEquals("Mensaje prueba",
 * c.getMessage()); }
 * 
 * @Test public void testSetMessage() { c.setMessage("Mensaje prueba");
 * assertEquals("Mensaje prueba", c.getMessage()); }
 * 
 * }
 */