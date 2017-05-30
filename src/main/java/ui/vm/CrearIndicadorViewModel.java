package ui.vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.Indicador;
import model.repositories.RepositorioDeCuentas;

@Observable
public class CrearIndicadorViewModel {
	RepositorioDeCuentas repositorioDeCuentas = RepositorioDeCuentas.getInstance();
	Collection<String> tiposDeCuenta = repositorioDeCuentas.getTiposDeCuenta();
	List<Indicador> indicadores = new ArrayList<Indicador>();
	String formula = "", nombreDelIndicador = "";
	
	public void crearIndicador(){
		if(nombreDelIndicador.isEmpty())
			throw new UserException("Debe proveer un nombre para el indicador");
		if(formula.isEmpty())
			throw new UserException("La formula está vacía");
		//Definir método para crear indicador, en algún lado del proceso debe retornar los errores del tipo UserException con el mensaje correspondiente
	}
	
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	public String getNombreDelIndicador() {
		return nombreDelIndicador;
	}
	public void setNombreDelIndicador(String nombreDelIndicador) {
		this.nombreDelIndicador = nombreDelIndicador;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public Collection<String> getTiposDeCuenta() {
		return tiposDeCuenta;
	}
	public void setTiposDeCuenta(Collection<String> tiposDeCuenta) {
		this.tiposDeCuenta = tiposDeCuenta;
	}
}
