package ui.vm;

import java.util.ArrayList;
import java.util.List;
import org.uqbar.commons.utils.Observable;

import dtos.PathFileTxtJson;
import model.Cuenta;
import utils.AppData;

@Observable
public class CargaDeCuentasViewModel {

	private String pathFile="";
	private String estado = "";
	private List<Cuenta> cuentas = new ArrayList<>();

//Setters
	
	public void setPathFile(String location) {
		pathFile = location;
		estado = "";
	}
	
	public void setCuentas(List<Cuenta> otrasCuentas) {
		cuentas = otrasCuentas;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

//Getters
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public String getPathFile() {
		return pathFile;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void cargarCuentas() {
		PathFileTxtJson datosDeCarga = new PathFileTxtJson(pathFile);
		AppData.getInstance().cargarCuentas(datosDeCarga);
		estado = "Cuentas cargadas correctamente";
	}
}
