package ui.vm;

import java.util.List;
import org.uqbar.commons.model.UserException;
import com.google.common.collect.Lists;
import model.repositories.RepositorioIndicadores;
public class ReglasTaxativasViewModel {
	
	
//	private String nombreRegla = "";
	private String valorAComparar ="", indicador ="", comparador = "";
	private List<String> indicadores = RepositorioIndicadores.getInstance().getNombresDeIndicadores();
	private List<String> comparadores = Lists.newArrayList(">","<","=","!=");
	
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
	
	public List<String> getIndicadores() {
		return indicadores;
	}
/*
	public String getNombreRegla() {
		return nombreRegla;
	}
*/

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

	public void setIndicadores(List<String> indicadores) {
		this.indicadores = indicadores;
	}
/*
	public void setNombreRegla(String nombreRegla) {
		this.nombreRegla = nombreRegla;
	}
*/
}
