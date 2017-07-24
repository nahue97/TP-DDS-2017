package model;

import java.util.List;

public class Metodologia {
	
	private String nombre;
	private List<Regla> reglas;

	public Metodologia(String nombre, List<Regla> reglas) {
		super();
		this.reglas = reglas;
		this.nombre = nombre;
	}

	public String getNombre(){
		return nombre;
	}
	
	public List<Regla> getReglas() {
		return reglas;
	}	
}
