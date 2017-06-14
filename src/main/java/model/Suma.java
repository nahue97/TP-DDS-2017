package model;

import java.math.BigDecimal;

public class Suma extends OperacionBinaria {

	public Suma(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal valor() {
		return getComponente1().valor().add(getComponente2().valor());
	}
	
	

}
