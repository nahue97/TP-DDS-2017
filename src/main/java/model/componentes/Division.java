package model.componentes;

import java.math.BigDecimal;

public class Division extends OperacionBinaria {

	public Division(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor(String periodo, String empresa) {
		return getComponente1().getValor(periodo, empresa).divide(getComponente2().getValor(periodo, empresa));
	}
	
	

}
