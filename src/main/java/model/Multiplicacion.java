package model;

import java.math.BigDecimal;

public class Multiplicacion extends OperacionBinaria {

	public Multiplicacion(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal valor() {
		return getComponente1().valor().multiply(getComponente2().valor());
	}
	
	

}
