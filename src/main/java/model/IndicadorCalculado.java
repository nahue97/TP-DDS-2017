package model;

import java.math.BigDecimal;

public class IndicadorCalculado extends Indicador {

	private String empresa;
	private String periodo;
	private BigDecimal valor;

	public IndicadorCalculado(Indicador indicador, String empresa, String periodo) {
		super(indicador.getNombre(), indicador.getFormula());
		calcular();
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void calcular() {
		// Calcular valor
		valor = new BigDecimal(0);
	}

}
