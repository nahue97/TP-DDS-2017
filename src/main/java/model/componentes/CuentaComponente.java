package model.componentes;

import java.math.BigDecimal;

public class CuentaComponente implements Componente{
	
	private BigDecimal valor;

	public CuentaComponente(BigDecimal valor) {
		super();
		this.valor = valor;
	}

	@Override
	public BigDecimal getValor() {		
		return valor;
	}

}
