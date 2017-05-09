package utils;

import java.util.List;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;

public class PrepararRepositorio {
	private static RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	
	public static void cargarCuentasDeArchivo(String filePath){
		String json = FilesReader.leerArchivo(filePath);
		
		List <Cuenta> cuentas;
		cuentas = JsonReader.obtenerCuentas(json);
		
		repositorio.agregarCuentasConIdAutogenerado(cuentas);
	}
}
