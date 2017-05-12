package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;
import utils.AppData;

@Observable
public class CargaDeCuentasViewModel {

	private String pathFile;
	private String estado;
	private RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	private List<Cuenta> cuentas = new ArrayList<>();

	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public void setCuentas(List<Cuenta> otrasCuentas) {
		cuentas = otrasCuentas;
	}
	
	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
		estado = "";
	}

	public void cargarCuenta() {
		try{
		AppData.cargarCuentasDeArchivo(pathFile);
		}
		catch(Exception e){
			throw e;
		}
		cuentas = repositorio.getCuentas();
		estado = "Cargado";
	}

	public String getEstado() {
		return estado;
	}
	
	
}
