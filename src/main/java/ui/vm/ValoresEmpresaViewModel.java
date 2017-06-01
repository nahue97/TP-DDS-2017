package ui.vm;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import ExceptionsPackage.EmpresaNotFoundException;
import ExceptionsPackage.PeriodoNotFoundException;
import model.Cuenta;
import model.Indicador;
import model.repositories.RepositorioCarpeta;

@Observable
public class ValoresEmpresaViewModel {

	private String empresa="", periodo="";
	private RepositorioCarpeta repositorio = RepositorioCarpeta.getInstance();
	private List<Cuenta> cuentas = repositorio.getCuentas();
	private List<Indicador> indicadores = repositorio.getIndicadores();
	

//Setters
	
	public void setEmpresa(String empresa){
		this.empresa = empresa;
	}
	
	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	
	public void setPeriodo(String periodo){
		this.periodo = periodo;
	}


//Getters
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public List<Indicador> getindicadores() {
		return indicadores;
	}
	
	public String getEmpresa(){
		return empresa;
	}
	
	public String getPeriodo(){
		return periodo;
	}
	
	public void consultarValores() throws EmpresaNotFoundException,PeriodoNotFoundException {
		if(empresa.isEmpty())
			throw new UserException("El campo Empresa está vacío");
		if(periodo.isEmpty())
			throw new UserException("El campo Período está vacío");
		else 
		cuentas = repositorio.filtrarCuentasPorEmpresa(empresa,cuentas);
		cuentas = repositorio.filtrarCuentasPorPeriodo(periodo, cuentas);
		indicadores = repositorio.filtrarIndicadoresPorEmpresa(empresa, indicadores);
		indicadores = repositorio.filtarIndicadoresPorPeriodo(periodo, indicadores);
	}
}
