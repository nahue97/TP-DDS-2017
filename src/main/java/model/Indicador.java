package model;

import java.util.List;

public class Indicador {
	private int id;
	private String nombre;
	private String formula;
	
	public Double obtenerValor(String empresa, String periodo){
		return 0.0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
}