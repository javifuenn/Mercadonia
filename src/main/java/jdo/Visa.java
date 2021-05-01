package jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Visa {
	
	private int nTarjeta;
	private String titular;
	private int cv;
	private String fCaducidad;
	
	public Visa(int nTarjeta, String titular, int cv, String fCaducidad) {
		super();
		this.nTarjeta = nTarjeta;
		this.titular = titular;
		this.cv = cv;
		this.fCaducidad = fCaducidad;
	}
public Visa() {
		
	}

	public int getnTarjeta() {
		return nTarjeta;
	}

	public void setnTarjeta(int nTarjeta) {
		this.nTarjeta = nTarjeta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public int getCv() {
		return cv;
	}

	public void setCv(int cv) {
		this.cv = cv;
	}

	public String getfCaducidad() {
		return fCaducidad;
	}

	public void setfCaducidad(String fCaducidad) {
		this.fCaducidad = fCaducidad;
	}

	@Override
	public String toString() {
		return "Visa [nTarjeta=" + nTarjeta + ", titular=" + titular + ", cv=" + cv + ", fCaducidad=" + fCaducidad
				+ "]";
	}
	

	
	
}
