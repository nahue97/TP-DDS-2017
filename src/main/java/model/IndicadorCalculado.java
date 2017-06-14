package model;

import java.math.BigDecimal;

import ExceptionsPackage.CuentaNotFoundException;
import model.repositories.RepositorioCuentas;

public class IndicadorCalculado extends Indicador {

	private String empresa;
	private String periodo;
	private BigDecimal valor;
	private String cuentas;

	public IndicadorCalculado(Indicador indicador, String empresa, String periodo) {
		super(indicador.getNombre(), indicador.getFormula());
		cuentas = obtenerCuentasSeparadasPorComa(indicador);
		calcular();
	}

	public String getCuentas() {
		return cuentas;
	}

	public void setCuentas(String cuentas) {
		this.cuentas = cuentas;
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

	public boolean isCuenta(String posibleCuenta) {
		return RepositorioCuentas.getInstance().getTiposDeCuenta().contains(posibleCuenta);
	}

	public String obtenerCuentasSeparadasPorComa(Indicador indicador) {
		String cuentas = "";

		String formula = indicador.getFormula();
		int contadorLetras = 0;
		for (int i = 0; i < formula.length(); i++) {

			if (Character.isLetter(formula.charAt(i))) {
				contadorLetras += 1;
				while (((i + contadorLetras + 1) <= formula.length())
						&& Character.isLetter(formula.charAt(i + contadorLetras))) {
					contadorLetras += 1;
				}

				String nombreCuenta = formula.substring(i, i + contadorLetras);

				if (!isCuenta(nombreCuenta)) {
					throw new CuentaNotFoundException("Cuenta no encontrada: " + nombreCuenta);
				} else {
					if (cuentas.isEmpty()) {
						cuentas += nombreCuenta;
					} else {
						cuentas += ", " + nombreCuenta;
					}
				}

				i = i + contadorLetras;
				// Todo procesado, se reinicia el contador y se settea el i en
				// el valor deseado
				contadorLetras = 0;
			}
		}

		return cuentas;
	}
}
