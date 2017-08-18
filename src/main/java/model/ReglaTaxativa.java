package model;

import java.math.BigDecimal;

import org.uqbar.commons.utils.Observable;

@Observable
public class ReglaTaxativa extends Regla {

	char comparador;
	BigDecimal valorAComparar;

	public ReglaTaxativa(String nombre, Indicador indicador, char comparador, BigDecimal valorAComparar) {
		this.nombre = nombre;
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
