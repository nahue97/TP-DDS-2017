package ui.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;

@Observable
public class ConsultaDeCuentasViewModel{
	
	private String empresa ="", tipoCuenta="", periodo="", valor="";
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	private RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();

	
	public void setUp(){
		BasicConfigurator.configure();
	}
	
	public void consultarCuenta() {
		cuentas = repositorio.filtrarCuentas(tipoCuenta, empresa, periodo, valor);
	}
	
	public void ordenarCuentasPorTipo() {
		cuentas = repositorio.getCuentasOrdenadasPorTipo();
	}
	
	public void ordenarCuentasPorEmpresa() {
		cuentas = repositorio.getCuentasOrdenadasPorEmpresa();
	}
	
	public void ordenarCuentasPorPeriodo() {
		cuentas = repositorio.getCuentasOrdenadasPorPeriodo();
	}
	
	public void ordenarCuentasPorValor() {
		cuentas = repositorio.getCuentasOrdenadasPorValor();
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
