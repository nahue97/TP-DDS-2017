package model;

import java.math.BigDecimal;

public class ReglaTaxativa extends Regla {

	char comparador;
	BigDecimal valorAComparar;

	public ReglaTaxativa(Indicador indicador, char comparador, BigDecimal valorAComparar) {
		this.indicador = indicador;
		this.comparador = comparador;
		this.valorAComparar = valorAComparar;
	}

	public char getComparador() {
		return comparador;
	}

	public BigDecimal getValorAComparar() {
		return valorAComparar;
	}

}
