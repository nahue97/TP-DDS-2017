package model.componentes;

import java.math.BigDecimal;

public interface Componente {
	
	public BigDecimal getValor(String periodo, String empresa);

	public void preparar(String periodo, String empresa);

}
