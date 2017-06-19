package model.componentes;

import java.math.BigDecimal;

public class Expresion {

	private Componente formula;

	public Expresion(Componente formula) {
		super();
		this.formula = formula;
	}

	public BigDecimal evaluar(String periodo, String empresa) {
		return formula.getValor(periodo, empresa);
	}

}
