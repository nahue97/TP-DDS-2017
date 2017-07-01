package model.componentes;

import java.math.BigDecimal;

public class Constante implements Componente{
	
	private BigDecimal valor;

	@Override
	public BigDecimal getValor() {
		return valor;
	}

	public Constante(BigDecimal valor) {
		super();
		this.valor = valor;
	}

}
