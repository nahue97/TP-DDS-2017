package utils;

import java.util.List;

public class Archivo {

	public static <T> void archivarObjetos(List<T> objetos, String path) {
		String jsonAGuardar = JsonCreator.getJson(objetos);

		ManejoDeArchivos.sobreescribirArchivo(path, jsonAGuardar);
	}
}
