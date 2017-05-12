package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;

@Observable
public class ConsultaDeCuentasViewModel{
	
	private String empresa ="", tipoCuenta="", periodo="", valor="";
	private RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	private List<Cuenta> cuentas = repositorio.getCuentas();

	
	public void setUp(){
		BasicConfigurator.configure();
	}
	
	public void consultarCuenta() {
		cuentas = repositorio.filtrarCuentas(tipoCuenta, empresa, periodo, valor);
	}
	
	public void ordenarCuentasPorTipo() {
		cuentas = repositorio.getCuentasPorTipo();
	}
	
	public void ordenarCuentasPorEmpresa() {
		cuentas = repositorio.getCuentasPorEmpresa();
	}
	
	public void ordenarCuentasPorPeriodo() {
		cuentas = repositorio.getCuentasPorPeriodo();
	}
	
	public void ordenarCuentasPorValor() {
		cuentas = repositorio.getCuentasPorValor();
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
	
	public List<Cuenta> getCuentas(){
		return this.cuentas;
	}
	//Setters
	public void setCuentas(List <Cuenta> cuentas){
		this.cuentas = cuentas;	
	}
	
}
