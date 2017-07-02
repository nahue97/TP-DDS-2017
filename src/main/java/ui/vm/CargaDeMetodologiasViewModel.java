package ui.vm;

import org.uqbar.commons.utils.Observable;

@Observable
public class CargaDeMetodologiasViewModel {
	
	public String nombre = "";
	
	// Setters
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// Getters

	public String getNombre() {
		return nombre;
	}
}
