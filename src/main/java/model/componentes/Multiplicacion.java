package model.componentes;

import java.math.BigDecimal;

public class Multiplicacion extends OperacionBinaria {

	public Multiplicacion(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor() {
		return getComponente1().getValor().multiply(getComponente2().getValor());
	}

}
