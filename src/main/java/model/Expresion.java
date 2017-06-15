package model;

import java.math.BigDecimal;

public class Expresion {
	
	private Componente formula;
	
	public BigDecimal evaluar(String periodo, String empresa){
		return formula.getValor(periodo, empresa);
	}

}
