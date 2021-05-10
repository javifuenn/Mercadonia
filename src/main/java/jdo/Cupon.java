package jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
public class Cupon {
	
	private String textoCupon;
	private int porcentajeDescuento;
	private String usuario;
	
	public Cupon() {
		
	}
	
	public Cupon(String textoCupon, int porcentajeDescuento, String usuario) {
		this.textoCupon = textoCupon;
		this.porcentajeDescuento = porcentajeDescuento;
		this.usuario = usuario;
	}

	public String getTextoCupon() {
		return textoCupon;
	}
	public void setTextoCupon(String textoCupon) {
		this.textoCupon = textoCupon;
	}
	public int getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(int porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		return "Cupon [textoCupon=" + textoCupon + ", porcentajeDescuento=" + porcentajeDescuento + ", usuario="
				+ usuario + "]";
	}

}
