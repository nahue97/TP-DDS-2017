package utils;

import java.util.List;

import model.Cuenta;
import model.RepositorioDeCuentas;

public class PrepararRepositorio {
	private static RepositorioDeCuentas repositorio = new RepositorioDeCuentas();
	
	public static RepositorioDeCuentas getRepositorio(){
		return repositorio;
	}
	
	public static void cargarCuentasDeArchivo(String filePath){
		String json = FilesReader.leerArchivo(filePath);
		List <Cuenta> cuentas;
		
		try{
			cuentas = JsonReader.obtenerCuentas(json);
		}
		catch(Error e){
			throw new Error("No se puede parsear el archivo");
		}
		
		repositorio.agregarCuentasConIdAutogenerado(cuentas);
	}
}
