package model;

import utils.ModeladorDeExpresiones;

public class Indicador {
	private int id;
	private String nombre;
	private String formula; //Formula String para obtener las cuentas que usa y mostrarlas en la tabla.
	private Expresion expresion;

	public Indicador(String nombre, String formula) {
		this.nombre = nombre;
		this.formula = formula;
		this.expresion = ModeladorDeExpresiones.getInstance().modelarFormula(formula);
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
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
	
	
}