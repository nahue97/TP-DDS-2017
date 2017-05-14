package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;
import utils.AppData;

@Observable
public class CargaDeCuentasViewModel {

	private static String pathFile="";
	private String estado = "";
	private RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	private List<Cuenta> cuentas = new ArrayList<>();

	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public void setCuentas(List<Cuenta> otrasCuentas) {
		cuentas = otrasCuentas;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public static String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		CargaDeCuentasViewModel.pathFile = pathFile;
		estado = "";
	}

	public void cargarCuentas() {
		AppData.cargarCuentas();
		cuentas = repositorio.getCuentas();
		estado = "Cuentas cargadas correctamente";
	}
}
