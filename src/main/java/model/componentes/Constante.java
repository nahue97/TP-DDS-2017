package model.componentes;

import java.math.BigDecimal;

public class Constante implements Componente{
	
	private BigDecimal valor;

	@Override
	public BigDecimal getValor(String periodo, String empresa) {
		return valor;
	}

	public Constante(BigDecimal valor) {
		super();
		this.valor = valor;
	}

	@Override
	public void preparar(String periodo, String empresa) {		
	}
}
