package model;

import java.util.List;

public class Metodologia {

	private List<Regla> reglas;

	public Metodologia(List<Regla> reglas) {
		super();
		this.reglas = reglas;
	}

	public List<Regla> getReglas() {
		return reglas;
	}	
}
