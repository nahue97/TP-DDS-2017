package ui.vm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import utils.LectorDeArchivos;

@Observable
public class CargaDeCuentasViewModel {
	
	private String pathFile;
	private List<Cuenta> cuentas;

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
	public void cargarCuenta() {
		cuentas = LectorDeArchivos.obtenerCuentas(pathFile);

	}
	
	
	
}
