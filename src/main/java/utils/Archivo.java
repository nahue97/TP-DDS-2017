package utils;

import java.io.File;

public class Archivo {
	
	/* Se le envía un objeto y lo añade al final de un archvo en formato json dejando el archivo
	   en el formato correcto para consultarlo después  */
	public static void archivarObjeto(Object objeto, String path){
		String jsonDeUnSoloObjeto = JsonCreator.getJson(objeto);
		
		agregarJsonAlArchivo(path, jsonDeUnSoloObjeto);
	}
	
	//Modifica el final del archivo que se le pasa para añadir lo que queda del json
	//Y si el archivo no existe lo crea
	private static void agregarJsonAlArchivo(String path, String jsonDeUnSoloObjeto){
		File file = new File(path);
		String jsonAGuardar = "";
		
		if(!file.isFile())
			jsonAGuardar = "[" + jsonDeUnSoloObjeto + "]";
		else
			jsonAGuardar = obtenerJsonAGuardar(path, jsonDeUnSoloObjeto);
		
		ManejoDeArchivos.sobreescribirArchivo(path,jsonAGuardar);
	}
	
	//Devuelve un string listo para guardarlo en el archivo como un Json
	private static String obtenerJsonAGuardar(String path,String jsonDeUnSoloObjeto){
		String jsonAGuardar ="";
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(path);
		
		String substring = contenidoDelArchivo.substring(0, contenidoDelArchivo.length()-1);
		
		jsonAGuardar = substring + "," + jsonDeUnSoloObjeto + "]";
		
		return jsonAGuardar;
	}
}
