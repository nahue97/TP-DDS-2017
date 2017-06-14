package model;

import java.math.BigDecimal;

public class Division extends OperacionBinaria {

	public Division(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal valor() {
		return getComponente1().valor().divide(getComponente2().valor());
	}
	
	

}
