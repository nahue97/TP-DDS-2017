package model;

import java.math.BigDecimal;

import ExceptionsPackage.CuentaNotFoundException;
import model.repositories.RepositorioCuentas;
import utils.CalculadorDeIndicadores;

public class IndicadorCalculado extends Indicador {
	
	private BigDecimal valor;
	private String cuentas;

	public IndicadorCalculado(Indicador indicador, String empresa, String periodo) {
		super(indicador.getNombre(), indicador.getFormula());
		cuentas = CalculadorDeIndicadores.getInstance().obtenerCuentasSeparadasPorComa(indicador);
		valor = CalculadorDeIndicadores.getInstance().calcularIndicador(indicador, empresa, periodo);
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
