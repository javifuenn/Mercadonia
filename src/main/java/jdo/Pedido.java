package jdo;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Pedido {
	
	
	private String nombre;
	private Date fechaPago;
	private List<String> productos;
	
	public Pedido(String nombre, Date fechaPago, List<String> productos) {
		super();
		this.nombre = nombre;
		this.fechaPago = fechaPago;
		this.productos = productos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public List<String> getProductos() {
		return productos;
	}

	public void setProductos(List<String> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "Pedido [nombre=" + nombre + ", fechaPago=" + fechaPago + ", productos=" + productos + "]";
	}
	
	
	
	
	
}
