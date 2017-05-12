package utils;

import java.util.List;
import model.Cuenta;
import model.repositories.RepositorioDeCuentas;

public class AppData {
	private static RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	private static String json;
	private static List <Cuenta> cuentas;
	
	public static void cargarCuentasDeArchivo(String filePath){

		json = FilesReader.leerArchivo(filePath);
		cuentas = JsonReader.obtenerCuentas(json);
		repositorio.agregarCuentas(cuentas);
	}
}
