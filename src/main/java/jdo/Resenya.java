package jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Resenya {
	
	String producto;
	String usuario;
	int calificacion;
	String opinion;
	
	public Resenya(String producto, String usuario, int calificacion, String opinion) {
		this.producto = producto;
		this.usuario = usuario;
		this.calificacion = calificacion;
		this.opinion = opinion;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	@Override
	public String toString() {
		return "Reseña [producto=" + producto + ", usuario=" + usuario + "calificacion, =" + calificacion + 
				"opinion, =" + opinion + "]";
	}
	

}
