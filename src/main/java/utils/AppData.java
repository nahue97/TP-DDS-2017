package utils;

import model.repositories.RepositorioDeCuentas;

public class AppData {
	private static RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	
	public static void cargarCuentas(){
		repositorio.agregarCuentas(FilesReader.getCuentas());
	}
}
