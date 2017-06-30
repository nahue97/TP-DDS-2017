package model.componentes;

import java.math.BigDecimal;

public class Expresion {

	private Componente formula;

	public Expresion(Componente formula) {
		super();
		this.formula = formula;
	}

	public BigDecimal evaluar() {
		return formula.getValor();
	}
	
	public Componente getFormula(){
		return formula;
	}
	
}