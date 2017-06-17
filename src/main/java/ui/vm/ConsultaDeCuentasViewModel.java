package ui.vm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioCuentas;

@Observable
public class ConsultaDeCuentasViewModel {

	private String empresa = "", tipoCuenta = "", periodo = "", valor = "";
	private List<Cuenta> cuentas;
	private List<String> empresas = new ArrayList<String>();
	private List<String> periodos = new ArrayList<String>();

	public void setUp() {
		BasicConfigurator.configure();
		cuentas = RepositorioCuentas.getInstance().getCuentas();
	}

	public void consultarCuenta() {
		cuentas = RepositorioCuentas.getInstance().filtrarCuentas(tipoCuenta, empresa, periodo, new BigDecimal(valor));
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

	//GETTERS
	public List<String> getEmpresas() {
		empresas = RepositorioCuentas.getInstance().getEmpresasDeCuentas();
		return empresas;
	}

	public List<String> getPeriodos() {
		periodos = RepositorioCuentas.getInstance().getPeriodosDeCuenta();
		return periodos;
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public String getValor() {
		return valor;
	}

	public String getPeriodo() {
		return periodo;
	}


	// Setters
	public void setEmpresas(List<String> empresas) {
		this.empresas = empresas;
	}
	
	public void setPeriodos(List<String> periodos) {
		this.periodos = periodos;
	}
	
	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
}
