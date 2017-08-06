package model.componentes;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Division extends OperacionBinaria {

	public Division(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor() {
		return getComponente1().getValor().divide(getComponente2().getValor(), 2, RoundingMode.HALF_UP);
	}

}
