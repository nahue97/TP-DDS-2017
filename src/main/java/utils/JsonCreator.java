package utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonCreator {
	
	//Permite obtener el json de un objeto o una lista de objetos
	public static String getJson(Object objetoOListaDeObjetos){
		Gson gson = new Gson();
		return gson.toJson(objetoOListaDeObjetos);
	}
}
