package ui.vm;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

@Observable
public class MenuDeMetodologiasViewModel {
	
	public String nombre = "";
	
	// Setters
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// Getters

	public String getNombre() {
		return nombre;
	}
	
	public void cargarMetodologia(){
		if (nombre.isEmpty())
			throw new UserException("Debe proveer un nombre para la metodologia");
		//if (formulaIngresada.isEmpty())
			//throw new UserException("Ingrese una formula");
		//else
			//TODO: guardar metodologia;
	}
}
