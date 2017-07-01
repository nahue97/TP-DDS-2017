package utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Cuenta;
import model.Indicador;

public class JsonReader {

	public static List<Cuenta> obtenerCuentas(String jsonString) {
		List<Cuenta> cuentas = new ArrayList<>();
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Cuenta>>() {
		}.getType();

		try {
			cuentas = gson.fromJson(jsonString, listType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new UserException("Error Sintáctico en el JSON");
		}

		return cuentas;
	}

	public static List<Indicador> obtenerIndicadores(String jsonString) {
		List<Indicador> indicadores = new ArrayList<>();
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Indicador>>() {
		}.getType();
		if (!jsonString.isEmpty()) {
			try {
				indicadores = gson.fromJson(jsonString, listType);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				throw new UserException("Error Sintáctico en el JSON");
			}
		}

		return indicadores;
	}
}