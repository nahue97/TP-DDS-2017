package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapUtils {

	public static <K> void insertarRegistro(HashMap<String, K> hashmap, String key, K value) {
		// Inserta un registro y si existe la key lo reemplaza.
		Iterator iterator = hashmap.entrySet().iterator();
		boolean flag = false;
		while (iterator.hasNext()) {
			Map.Entry<String, K> registro = (Map.Entry<String, K>) iterator.next();
			if (registro.getKey().equals(key)) {
				iterator.remove();
				hashmap.put(key, value);
				flag = true;
			}
		}

		if (!flag) {
			hashmap.put(key, value);
		}
	}

	public static <K> void eliminarRegistro(HashMap<String, K> hashmap, String key) {
		// Elimina un registro si existe.
		Iterator iterator = hashmap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, K> registro = (Map.Entry<String, K>) iterator.next();
			if (registro.getKey().equals(key)) {
				iterator.remove();
			}
		}
	}

	public static <K> K obtenerValorPorClave(HashMap<String, K> hashmap, String key) {
		// Obtiene el valor asociado a la clave indicada, si no existe devuelve null.
		Iterator iterator = hashmap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, K> registro = (Map.Entry<String, K>) iterator.next();
			if (((String) registro.getKey()).equals(key)) {
				return registro.getValue();
			}
		}
		return null;
	}
}
