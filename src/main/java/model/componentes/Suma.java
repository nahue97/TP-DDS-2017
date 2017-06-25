package model.componentes;

import java.math.BigDecimal;

public class Suma extends OperacionBinaria {

	public Suma(Componente componente1, Componente componente2) {
		super(componente1, componente2);
	}

	@Override
	public BigDecimal getValor(String periodo, String empresa) {
		return getComponente1().getValor(periodo, empresa).add(getComponente2().getValor(periodo, empresa));
	}

	@Override
	public void preparar(String periodo, String empresa) {
		getComponente1().preparar(periodo, empresa);
		getComponente2().preparar(periodo, empresa);
	}
	
	

}
