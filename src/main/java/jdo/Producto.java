package jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Producto {
	
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private String codigo;
	private String nombre;
	private String descripcion;
	private double precio;
	private String usuario;
	

	public Producto() {
		
	}
	
	public Producto(String nombre, String descripcion, double precio, String usuario) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.usuario = usuario;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String toString() {
		return "Producto: " + codigo + " Nombre: " + nombre + " [ " + descripcion + " ]" + " Precio: " + precio;
	}
}
