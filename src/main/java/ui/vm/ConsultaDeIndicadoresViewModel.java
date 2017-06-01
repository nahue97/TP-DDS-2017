package ui.vm;


import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.utils.Observable;

import model.Indicador;
import model.repositories.RepositorioCarpeta;

@Observable
public class ConsultaDeIndicadoresViewModel{
	private String empresa ="", nombre="", periodo="", valor="";
	private RepositorioCarpeta repositorio = RepositorioCarpeta.getInstance();
	private List<Indicador> indicadores = repositorio.getIndicadores();
	
	public void setUp(){
		BasicConfigurator.configure();
	}
	public void consultarIndicador(){
		repositorio.filtrarIndicadores(empresa, nombre, periodo, valor);
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

	public List<Indicador> getIndicadores() {
		return this.indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
}
