package ui.vm;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.IndicadorCalculado;
import model.repositories.RepositorioIndicadores;

@Observable
public class ConsultaDeIndicadoresViewModel {
	private String empresa = "", nombre = "", periodo = "", valor = "";
	private List<IndicadorCalculado> indicadores;

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
