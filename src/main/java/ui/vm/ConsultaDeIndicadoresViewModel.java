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
	private List<String> empresas = new ArrayList<String>();
	private List<String> periodos = new ArrayList<String>();

	public void setUp() {
		BasicConfigurator.configure();
		indicadores = RepositorioIndicadores.getInstance()
				      .filtrarIndicadores("", "", "", "");
	}

	public void consultarIndicador() {
		if (empresa.isEmpty()) {
			throw new UserException("El campo Empresa esta vacio");
		}
		if (periodo.isEmpty()) {
			throw new UserException("El campo Periodo esta vacio");
		} else {
		indicadores.addAll(RepositorioIndicadores.getInstance()
					.filtrarIndicadores(empresa, "", periodo, ""));
		}
	}

	// GETTERS
	
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
		return this.indicadores;
	}

	public void setIndicadores(List<IndicadorCalculado> indicadores) {
		this.indicadores = indicadores;
	}
}
