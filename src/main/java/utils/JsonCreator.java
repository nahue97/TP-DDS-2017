package utils;

import com.google.gson.Gson;


public class JsonCreator {
	
	//Permite obtener el json de un objeto o una lista de objetos
	public static String getJson(Object objetoOListaDeObjetos){
		Gson gson = new Gson();
		return gson.toJson(objetoOListaDeObjetos);
	}
}
