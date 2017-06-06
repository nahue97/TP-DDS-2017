package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.Indicador;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;

@Observable
public class ValoresEmpresaViewModel {

	private String empresa = "", periodo = "";
	private List<Indicador> indicadores;
	private List<Cuenta> cuentas;

	// Setters

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	// Getters

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public List<Indicador> getindicadores() {
		return indicadores;
	}

	public String getEmpresa() {
		return empresa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void consultarValores() {
		if (empresa.isEmpty()) {
			throw new UserException("El campo Empresa esta vacío");
		}
		if (periodo.isEmpty()) {
			throw new UserException("El campo Período esta vacío");
		} else {

			cuentas = new ArrayList<>();
			cuentas.addAll(RepositorioCuentas.getInstance().filtrarCuentas("", empresa, periodo, ""));

			indicadores = new ArrayList<>();
			indicadores.addAll(RepositorioIndicadores.getInstance().filtrarIndicadores(empresa, "", periodo, 0.0));
		}
	}
}
