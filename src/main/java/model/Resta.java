package model;

import java.math.BigDecimal;

public class Resta extends OperacionBinaria {

	public Resta(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal valor() {
		return getComponente1().valor().subtract(getComponente2().valor());
	}
	
	

}
