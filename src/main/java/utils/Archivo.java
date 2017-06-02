package utils;

import java.io.File;
import java.util.List;

public class Archivo {
	
	/* Se le envía un objeto y lo añade al final de un archvo en formato json dejando el archivo
	   en el formato correcto para consultarlo después  */
	public static <T> void archivarObjetos(List<T> objetos, String path){
		String jsonAGuardar = JsonCreator.getJson(objetos);
		
		ManejoDeArchivos.sobreescribirArchivo(path,jsonAGuardar);
	}
}
