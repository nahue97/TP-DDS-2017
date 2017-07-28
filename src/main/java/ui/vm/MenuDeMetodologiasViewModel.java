package ui.vm;

import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;
import model.Metodologia;
import model.Regla;
import model.repositories.RepositorioDeMetodologias;

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
		if (nombre.isEmpty()) {
			throw new UserException("Debe proveer un nombre para la metodologia");
		}
		else {
			List<Regla> reglas = null;
			Metodologia nuevaMetodologia = new Metodologia(this.nombre, reglas);
			RepositorioDeMetodologias.getInstance().guardarMetodologia(nuevaMetodologia);
		}
	}
}
