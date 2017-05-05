package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;
import model.repositories.Repositorios;

@Observable
public class ConsultaDeCuentasViewModel{
	
	private String empresa;
	private String tipoCuenta;
	private String periodo;
	private List<Cuenta> cuentas;
	private RepositorioDeCuentas repositorio = Repositorios.getInstanceRepositorioDeCuentas();

	
	public void setUp(){
		BasicConfigurator.configure();
	}
	
	public void consultarCuenta() {
		cuentas = repositorio.filtrarCuentasPorPeriodoEmpresaValor(periodo, empresa, tipoCuenta);
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
