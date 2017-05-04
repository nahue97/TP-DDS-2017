package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;
import model.repositories.Repositorios;
import utils.PrepararRepositorio;

@Observable
public class CargaDeCuentasViewModel {

	private String pathFile;
	private String estado;
	private RepositorioDeCuentas repositorio = Repositorios.getInstanceRepositorioDeCuentas();
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
		estado = "Cargado";
		
		PrepararRepositorio.cargarCuentasDeArchivo(pathFile);
		cuentas = repositorio.getCuentas();
	}

	public String getEstado() {
		return estado;
	}
	
	
}
