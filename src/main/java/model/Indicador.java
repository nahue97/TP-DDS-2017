package model;

import model.componentes.Expresion;
import utils.ModeladorDeExpresiones;

public class Indicador {
	private int id;
	private String nombre;
	private String formula; //Formula String para obtener las cuentas que usa y mostrarlas en la tabla.

	public Indicador(String nombre, String formula) {
		this.nombre = nombre;
		this.formula = formula;
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

	public Expresion getExpresion() {
		return ModeladorDeExpresiones.getInstance().modelarFormula(formula);
	}
	
}