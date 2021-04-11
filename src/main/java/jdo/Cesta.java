package jdo;

import javax.jdo.annotations.*;


import java.util.Date;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Cesta {

  
	@Persistent(defaultFetchGroup = "true", dependentElement = "true")
    private Usuarios usuario;
	@Persistent(defaultFetchGroup = "true", dependentElement = "true")
    private Producto producto;
    private Date fechaExpiracion;
    
	public Cesta(Usuarios usuario, Producto producto, Date fechaExpiracion) {
		super();
		this.usuario = usuario;
		this.producto = producto;
		this.fechaExpiracion = fechaExpiracion;
	}
	
	public Cesta() {
		super();
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	@Override
	public String toString() {
		return "Cesta [usuario=" + usuario + ", producto=" + producto + ", fechaExpiracion="
				+ fechaExpiracion + "]\n";
	}
    
	

   
}