package ui.vm;

import java.util.ArrayList;
import java.util.List;
import org.uqbar.commons.utils.Observable;

import dtos.CargaDeCuentasDTO;
import model.Cuenta;
import model.Indicador;
import utils.AppData;

@Observable
public class ValoresEmpresaViewModel {

	private String empresa="", periodo="";
	private List<Cuenta> cuentas = new ArrayList<>();
	private List<Indicador> indicadores = new ArrayList<>();

//Setters
	
	public void setEmpresa(String empresa){
		empresa = empresa;
	}
	
	public void setCuentas(List<Cuenta> otrasCuentas) {
		cuentas = otrasCuentas;
	}
	
	public void setIndicadores(List<Indicador> otrosIndicadores) {
		indicadores = otrosIndicadores;
	}
	
	public void setPeriodo(String periodo){
		periodo = periodo;
	}


//Getters
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public List<Indicador> getindicadores() {
		return indicadores;
	}
	
	public void consultarValores() {

	}
}
