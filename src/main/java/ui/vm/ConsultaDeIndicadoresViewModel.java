package ui.vm;


import java.util.List;

import org.uqbar.commons.utils.Observable;

import model.Indicador;
import model.repositories.RepositorioDeCuentas;

@Observable
public class ConsultaDeIndicadoresViewModel{
	private String empresa ="", nombre="", periodo="", valor="";
	private RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	//private List<Indicador> indicadores = repositorio.getIndicadores();
	
	public void consultarIndicador(){
		//va al repo carpeta y lo trae
	}
	
	//GETTERS
	
	public String getEmpresa() {
		return empresa;
	}
	
	public String getNombre() {
		return nombre;
	}	

	public String getPeriodo() {
		return periodo;
	}	
	
	public String getValor() {
		return valor;
	}	
	//SETTERS
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
