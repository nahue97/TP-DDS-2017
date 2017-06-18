package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.IndicadorCalculado;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;

@Observable
public class ConsultaDeIndicadoresViewModel {
	private String empresa = "", nombre = "", periodo = "", valor = "";
	private List<IndicadorCalculado> indicadores;
	private List<String> empresas = RepositorioCuentas.getInstance().getEmpresasDeCuentas();
	private List<String> periodos = RepositorioCuentas.getInstance().getPeriodosDeCuenta();
	
	

	public void setUp() {
		BasicConfigurator.configure();		
	}

	public void consultarIndicador() {
		if (empresa.isEmpty()) {
			throw new UserException("El campo Empresa esta vacio");
		}
		if (periodo.isEmpty()) {
			throw new UserException("El campo Periodo esta vacio");
		} else {
		indicadores = RepositorioIndicadores.getInstance()
					.calcularIndicadores(empresa, periodo);
		}
	}

	// GETTERS
	
	public List<String> getEmpresas() {
		return empresas;
	}

	public List<String> getPeriodos() {
		return periodos;
	}

	public String getEmpresa() {
		return empresa;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPeriodo() {
		return periodo;
	}

	public String getValor() {
		return valor;
	}

	// SETTERS
	
	public void setEmpresas(List<String> empresas) {
		this.empresas = empresas;
	}
	
	public void setPeriodos(List<String> periodos) {
		this.periodos = periodos;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public List<IndicadorCalculado> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<IndicadorCalculado> indicadores) {
		this.indicadores = indicadores;
	}
}
