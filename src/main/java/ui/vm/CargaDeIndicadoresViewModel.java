package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;
import model.Indicador;

@Observable
public class CargaDeIndicadoresViewModel {
	
	public String nombre = "";
	public String formulaIngresada = "";
	List<Indicador> indicadores = new ArrayList<Indicador>();
	
	//Setters
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setFormula(String formulaIngresada){
		this.formulaIngresada = formulaIngresada;
	}
	
	public void setIndicadores(List<Indicador> indicadores) {
				this.indicadores = indicadores;
			}
	
	//Getters
	
	public String getNombre(){
		return nombre;	
	}
	
	public String getformulaIngresada(){
		return formulaIngresada;	
	}
	
	public List<Indicador> getIndicadores() {
				return indicadores;
			}
	
	// Carga de indicador
	public void cargarIndicador() {
		/*if(nombre.isEmpty())
			-			throw new UserException("Debe proveer un nombre para el indicador");
		if(formulaIngresada.isEmpty())
			-			throw new UserException("La formula está vacía");
			-		//Definir método para crear indicador, en algún lado del proceso debe retornar los errores del tipo UserException con el mensaje correspondiente
			-	}*/
		
	}
}
