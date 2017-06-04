package ui.vm;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioCuentas;

@Observable
public class ConsultaDeCuentasViewModel {

	private String empresa = "", tipoCuenta = "", periodo = "", valor = "";
	private List<Cuenta> cuentas;

	public void setUp() {
		BasicConfigurator.configure();
		cuentas = RepositorioCuentas.getInstance().getCuentas();
	}

	public void consultarCuenta() {
		cuentas = RepositorioCuentas.getInstance().filtrarCuentas(tipoCuenta, empresa, periodo, valor);
	}

	public void ordenarCuentasPorTipo() {
		cuentas = RepositorioCuentas.getInstance().getCuentasPorTipo();
	}

	public void ordenarCuentasPorEmpresa() {
		cuentas = RepositorioCuentas.getInstance().getCuentasPorEmpresa();
	}

	public void ordenarCuentasPorPeriodo() {
		cuentas = RepositorioCuentas.getInstance().getCuentasPorPeriodo();
	}

	public void ordenarCuentasPorValor() {
		cuentas = RepositorioCuentas.getInstance().getCuentasPorValor();
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}

	// Setters
	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

}
