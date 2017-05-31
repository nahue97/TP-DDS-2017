package ui.vm;

import java.util.List;

import org.uqbar.commons.utils.Observable;
import model.Indicador;

@Observable
public class CargaDeIndicadoresViewModel {
	
	public String nombre = "";
	public String formulaIngresada = "";
	
	//Setters
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setFormula(String formulaIngresada){
		this.formulaIngresada = formulaIngresada;
	}
	
	//Getters
	
	public String getNombre(){
		return nombre;	
	}
	
	public String getformulaIngresada(){
		return formulaIngresada;	
	}
	
	// Carga de indicador
	public void cargarIndicador() {
		// TODO Auto-generated method stub
		
	}
}
