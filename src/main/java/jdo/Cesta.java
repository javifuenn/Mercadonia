package jdo;

import javax.jdo.annotations.*;


import java.util.Date;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Cesta {

    private String Nombreproducto;
    private Date fechaExpiracion;
    private String NombreUsuario;
    
	public Cesta(String nombreproducto, Date fechaExpiracion, String nombreUsuario) {
		super();
		Nombreproducto = nombreproducto;
		this.fechaExpiracion = fechaExpiracion;
		NombreUsuario = nombreUsuario;
	}

	public String getNombreproducto() {
		return Nombreproducto;
	}

	public void setNombreproducto(String nombreproducto) {
		Nombreproducto = nombreproducto;
	}

	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String getNombreUsuario() {
		return NombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		NombreUsuario = nombreUsuario;
	}

	@Override
	public String toString() {
		return "Cesta [Nombreproducto=" + Nombreproducto + ", fechaExpiracion=" + fechaExpiracion + ", NombreUsuario="
				+ NombreUsuario + "]";
	}
    
	
    
	
    
	

   
}