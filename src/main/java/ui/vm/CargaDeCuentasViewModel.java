package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import utils.LectorDeArchivos;

@Observable
public class CargaDeCuentasViewModel {

	private String pathFile;
	private String estado;
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> otrasCuentas) {
		this.cuentas.addAll(otrasCuentas);
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
		estado = "";
	}

	public void cargarCuenta() {
		estado = "Cargado";
		cuentas.addAll(LectorDeArchivos.obtenerCuentas(pathFile));
	}

	public String getEstado() {
		return estado;
	}
	
	
}
