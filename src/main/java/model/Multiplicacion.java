package model;

import java.math.BigDecimal;

public class Multiplicacion extends OperacionBinaria {

	public Multiplicacion(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor(String periodo, String empresa) {
		return getComponente1().getValor(periodo, empresa).multiply(getComponente2().getValor(periodo, empresa));
	}
	
	

}
