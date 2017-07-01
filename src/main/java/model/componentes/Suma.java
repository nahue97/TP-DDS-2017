package model.componentes;

import java.math.BigDecimal;

public class Suma extends OperacionBinaria {

	public Suma(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor() {
		return getComponente1().getValor().add(getComponente2().getValor());
	}

}
