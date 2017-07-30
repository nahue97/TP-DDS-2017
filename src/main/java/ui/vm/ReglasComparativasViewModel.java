package ui.vm;

import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import com.google.common.collect.Lists;

import model.Criterio;
import model.Indicador;
import model.ReglaComparativa;
import model.repositories.RepositorioDeMetodologias;
import model.repositories.RepositorioIndicadores;

@Observable
public class ReglasComparativasViewModel {

	private String nombreRegla = "", indicador ="", criterio;
	private List<String> indicadores = RepositorioIndicadores.getInstance().getNombresDeIndicadores();
	private List<String> criterios = Lists.newArrayList("MENOR","MAYOR");
	
	public void agregarRegla() {
		if (nombreRegla.isEmpty()) {
			throw new UserException("Debe ingresar un nombre");
		}
		if (indicador.isEmpty()) {
			throw new UserException("Debe seleccionar un indicador");
		}
		if (criterio == null){
			throw new UserException("Debe seleccionar un criterio");
		}else {
			Criterio _criterio;
			if (criterio == "MAYOR") {
				_criterio = Criterio.MAYOR;
			}
			else{
				_criterio = Criterio.MENOR;
			}			
			Indicador _indicador = RepositorioIndicadores.getInstance().getIndicadorPorNombre(indicador);
			ReglaComparativa regla = new ReglaComparativa(nombreRegla,_indicador, _criterio);
			RepositorioDeMetodologias.getInstance().agregarReglaTemporal(regla);
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
	
	public String getCriterio() {
		return criterio;
	}
	
	public String getNombreRegla(){
		return nombreRegla;
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
	
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	
	public void setNombreRegla(String nombreRegla) {
		this.nombreRegla = nombreRegla;
	}
}
