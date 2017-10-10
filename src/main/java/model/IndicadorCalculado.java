package model;

import java.math.BigDecimal;

import org.uqbar.commons.utils.Observable;

import utils.CalculadorDeIndicadores;

@Observable
public class IndicadorCalculado extends Indicador {

	private BigDecimal valor;
	private String cuentas;
	private Empresa empresa;
	private String periodo;

	public IndicadorCalculado(Indicador indicador, Empresa empresa, String periodo) {
		super(indicador.getNombre(), indicador.getFormula());
		this.empresa = empresa;
		this.periodo = periodo;
		cuentas = CalculadorDeIndicadores.getInstance().obtenerCuentasSeparadasPorComa(indicador);
		valor = CalculadorDeIndicadores.getInstance().calcularIndicador(indicador, empresa, periodo);
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getCuentas() {
		return cuentas;
	}

	public void setCuentas(String cuentas) {
		this.cuentas = cuentas;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
