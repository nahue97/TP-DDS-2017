package model.componentes;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Division extends OperacionBinaria {

	public Division(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor() {
		BigDecimal valor1 = getComponente1().getValor();
		BigDecimal valor2 = getComponente2().getValor();
		if ((valor1.compareTo(BigDecimal.ZERO) == 0) || (valor2.compareTo(BigDecimal.ZERO) == 0))
			return BigDecimal.ZERO;
		return valor1.divide(valor2, 2, RoundingMode.HALF_UP);
		//return getComponente1().getValor().divide(getComponente2().getValor(), 2, RoundingMode.HALF_UP);
	}

}
