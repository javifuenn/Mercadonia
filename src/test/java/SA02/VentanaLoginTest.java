package SA02;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.junit.Test;

import gui.VentanaLogin;
import jdo.Usuarios;

public class VentanaLoginTest {
	
	@Test
	public void testLogin() {
		
		List<Usuarios> usuario1 = Arrays.asList(
    			new Usuarios("sergio", "1234"));
		VentanaLogin vent = new VentanaLogin();
		boolean result = vent.login("sergio", "1234");
		boolean comp = false;
		Usuarios usuarios = UsuariosResource.getUsuariosLogin(usuario1.get(0).getUsername());
		if(usuarios.getPassword().equals(usuario1.get(0).getPassword())) {
			comp = true;
		}
		
		assertEquals(result, comp);
		
	}
	
	@Test
	public void testRegistro() {
		
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Usuarios usuario1 = new Usuarios("pepe", "1234");
			pm.makePersistent(usuario1);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		
		boolean comp = false;
		Usuarios usuarios = UsuariosResource.getUsuariosLogin("pepe");
		if(usuarios.getPassword().equals("1234")) {
			comp = true;
		}
		
		assertEquals(true, comp);
		
	}

}
