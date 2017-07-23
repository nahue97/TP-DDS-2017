package ui.vm;

import java.util.List;

import org.uqbar.commons.model.UserException;
import model.IndicadorCalculado;

public class ReglasTaxativasViewModel {
	
	private String valorAComparar ="", indicador ="", comparador = "";
	private List<IndicadorCalculado> indicadores;
	private List<String> comparadores;	
	
	public void agregarRegla() {
		
		if (valorAComparar.isEmpty()) {
			throw new UserException("El valor a comparar esta vacio");
		}
		if (indicador.isEmpty()) {
			throw new UserException("Debe seleccionar un indicador");
		}
		if (comparador.isEmpty()) {
			throw new UserException("Debe seleccionar un comparador");
		}else {
		//guardar regla
		}
	}

	// GETTERS
	
	public List<String> getComparadores() {
		return comparadores;
	}
	
	public String getIndicador() {
		return indicador;
	}

	public String getComparador() {
		return comparador;
	}

	public String getValorAComparar() {
		return valorAComparar;
	}
	
	public List<IndicadorCalculado> getIndicadores() {
		return indicadores;
	}

	// SETTERS
	
	public void setComparadores(List<String> comparadores){
		comparadores.add(">");
		comparadores.add("<");
		comparadores.add("=");
		comparadores.add("!=");
	}
	
	public void setValorAComparar(String valorAComparar){
		this.valorAComparar = valorAComparar;
	}
	
	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public void setComparador(String comparador) {
		this.comparador = comparador;
	}

	public void setIndicadores(List<IndicadorCalculado> indicadores) {
		this.indicadores = indicadores;
	}
}
