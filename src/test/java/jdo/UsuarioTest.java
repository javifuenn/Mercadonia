package jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import SA02.Main;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

public class UsuarioTest {
	
	private Usuario u;
	
	@Before
    public void crearusuario() {
        u = new Usuario("sergio", "1234");
    }
	
	@Test
	public void testGetUsername() {
		assertEquals("sergio", u.getUsername());
	}
	
	@Test
	public void testGetPassword() {
		assertEquals("1234", u.getPassword());
	}
	
	@Test
	public void testSetUsername() {
		u.setUsername("unai");
		assertEquals("unai", u.getUsername());
	}
	
	@Test
	public void testSetPassword() {
		u.setPassword("4321");
		assertEquals("4321", u.getPassword());
	}

}
