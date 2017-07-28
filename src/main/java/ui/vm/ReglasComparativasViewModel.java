package ui.vm;

import java.util.List;

import org.uqbar.commons.model.UserException;

import com.google.common.collect.Lists;

import model.Criterio;
import model.repositories.RepositorioIndicadores;

public class ReglasComparativasViewModel {

	private String indicador ="";
	private Criterio criterio;
	private List<String> indicadores = RepositorioIndicadores.getInstance().getNombresDeIndicadores();
	private List<String> criterios = Lists.newArrayList("MENOR","MAYOR");
	
	public void agregarRegla() {

		if (indicador.isEmpty()) {
			throw new UserException("Debe seleccionar un indicador");
		}else {
		//guardar regla
		}
	}
	
	// GETTERS

	public String getIndicador() {
		return indicador;
	}
	
	public List<String> getIndicadores() {
		return indicadores;
	}
	
	public List<String> getCriterios() {
		return criterios;
	}
	
	public Criterio getCriterio() {
		return criterio;
	}
	
	// SETTERS

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	
	public void setIndicadores(List<String> indicadores) {
		this.indicadores = indicadores;
	}
	
	public void setCriterios(List<String> criterios) {
		this.criterios = criterios;
	}
	
	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}
	
}
