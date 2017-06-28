package model.componentes;

import java.math.BigDecimal;

public class Resta extends OperacionBinaria {

	public Resta(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor(String periodo, String empresa) {
		return getComponente1().getValor(periodo, empresa).subtract(getComponente2().getValor(periodo, empresa));
	}

}
