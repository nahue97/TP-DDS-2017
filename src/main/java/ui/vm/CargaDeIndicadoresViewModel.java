package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.Indicador;
import utils.AnalizadorDeFormulas;
import utils.AppData;

@Observable
public class CargaDeIndicadoresViewModel {

	private String nombre = "";
	private String formulaIngresada = "";
	private List<Indicador> indicadores = new ArrayList<>();

	// Setters
	public void setNombre(String _nombre) {
		nombre = _nombre;
	}

	public void setFormulaIngresada(String _formulaIngresada) {
		formulaIngresada = _formulaIngresada;
	}

	public void setIndicadores(List<Indicador> _indicadores) {
		indicadores = _indicadores;
	}

	// Getters

	public String getNombre() {
		return nombre;
	}

	public String getformulaIngresada() {
		return formulaIngresada;
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	// Carga de indicador
	public void cargarIndicador() {
		if (nombre.isEmpty())
			throw new UserException("Debe proveer un nombre para el indicador");
		if (formulaIngresada.isEmpty())
			throw new UserException("Ingrese una formula");
		else
			this.analizarYCargarIndicador();

	}

	public void analizarYCargarIndicador() {
		AnalizadorDeFormulas analizador = new AnalizadorDeFormulas();
		String formula = analizador.analizarYSimplificarFormula(formulaIngresada);
		AppData.getInstance().guardarIndicador(formula, nombre);
	}

}
