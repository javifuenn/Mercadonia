package jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Usuarios {
	
	

	@PrimaryKey
	private String username;
	private String password;
	
	public Usuarios(String Usuario, String password) {
		this.username = Usuario;
		this.password = password;
	}

	public Usuarios() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Usuarios [username=" + username + ", password=" + password + "]";
	}

}
