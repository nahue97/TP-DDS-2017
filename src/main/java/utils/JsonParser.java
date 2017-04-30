package utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Cuenta;

public class JsonParser {
	
	public static List<Cuenta> obtenerCuentas(String jsonString){
		List<Cuenta> cuentas = new ArrayList<>();
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Cuenta>>(){}.getType();
		
		try {
			cuentas = gson.fromJson(jsonString, listType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new Error("Error Sintactico en el JSON");
			}
		
		return cuentas;
	}
}
