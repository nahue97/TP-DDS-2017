package utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Criterio;
import model.Regla;
import model.ReglaComparativa;

public class HashMapUtils {

	public static <K> void insertarRegistro(LinkedHashMap<String, K> hashmap, String key, K value) {
		// Inserta un registro y si existe la key lo reemplaza.
		Iterator iterator = hashmap.entrySet().iterator();
		boolean existe = false;
		while (iterator.hasNext()) {
			Map.Entry<String, K> registro = (Map.Entry<String, K>) iterator.next();
			if (registro.getKey().equals(key)) {
				iterator.remove();
				hashmap.put(key, value);
				existe = true;
			}
		}

		if (!existe) {
			hashmap.put(key, value);
		}
	}

	public static <K> void eliminarRegistro(LinkedHashMap<String, K> hashmap, String key) {
		// Elimina un registro si existe.
		Iterator iterator = hashmap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, K> registro = (Map.Entry<String, K>) iterator.next();
			if (registro.getKey().equals(key)) {
				iterator.remove();
			}
		}
	}

	public static <K> K obtenerValorPorClave(LinkedHashMap<String, K> hashmap, String key) {
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

	public static HashMap<String, Integer> ordenarPorConveniencia(LinkedHashMap<String, BigDecimal> hashmap,
			ReglaComparativa regla) {
		LinkedHashMap<String, BigDecimal> hashmapOrdenado = new LinkedHashMap<>();
		LinkedHashMap<String, Integer> hashmapDefinitivoConPuntajes = new LinkedHashMap<>();

		// Esta funcion es un poco confusa porque adaptamos las cosas para que el
		// hashmap quede ordenado de menos conveniente a mas conveniente, como lo
		// charlamos con Julian. La idea es ir sacando los valores de la siguiente
		// forma: En caso de ser "Cuanto MAYOR mas conviene", vamos sacando los valores
		// de MENOR a mayor, y metiendolos en el hashmap a retornar, para tenerlo
		// ordenado de menor conveniencia a mayor conveniencia.
		// Por eso en las condiciones del if del recorrido van a notar que los
		// picoparentesis estan invertidos, y no coinciden.

		while (!hashmap.isEmpty()) {
			BigDecimal valorPico = null;

			Iterator iterator = hashmap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, BigDecimal> registro = (Map.Entry<String, BigDecimal>) iterator.next();
				BigDecimal valorDelRegistro = (BigDecimal) registro.getValue();
				if (valorDelRegistro == null) {
					valorPico = valorDelRegistro;
				} else if (((valorPico.compareTo(valorDelRegistro) == 1) && (regla.getCriterio().equals(Criterio.MAYOR)))
						|| ((valorDelRegistro.compareTo(valorPico) == 1) && (regla.getCriterio().equals(Criterio.MENOR)))) {
					// Primera condicion: Cuanto MAYOR mas conviene. Busco el menor.
					// Segunda condicion: Cuanto MENOR mas conviene. Busco el mayor.
					valorPico = valorDelRegistro;
				}
			}
			// Terminamos de recorrer el hashmap y tenemos el valor pico (Maximo o minimo).
			// Ahora volvemos a recorrer hasta encontrar el registro que tenga el valor.
			// Al encontrarlo, lo sacamos de hashmap y lo metemos en hashmapOrdenado
			Iterator iterator2 = hashmap.entrySet().iterator();
			while (iterator2.hasNext()) {
				Map.Entry<String, BigDecimal> registro = (Map.Entry<String, BigDecimal>) iterator2.next();
				BigDecimal valorDelRegistro = (BigDecimal) registro.getValue();
				if (valorDelRegistro.equals(valorPico)) {
					HashMapUtils.insertarRegistro(hashmapOrdenado, registro.getKey(), registro.getValue());
					iterator2.remove();
				}
			}
		}

		// Ahora, una vez que los tenemos ordenados de menor a mayor conveniencia,
		// podemos asociar la conveniencia de cada empresa segun el indicador calculado
		// con la posicion en la que se encuentra dentro del hashmapOrdenado. Lo que
		// vamos a hacer es meter de a una las empresas en hashmapDefinitivoConPuntajes
		// con su correspondiente posicion en el hashmapOrdenado

		Iterator iterator3 = hashmapOrdenado.entrySet().iterator();
		Integer posicion = 0;
		while (iterator3.hasNext()) {
			posicion++;
			Map.Entry<String, BigDecimal> registro = (Map.Entry<String, BigDecimal>) iterator3.next();
			HashMapUtils.insertarRegistro(hashmapDefinitivoConPuntajes, registro.getKey(), posicion);
		}

		// Y aca tenemos nuestro hermoso hashmap ordenado de menor a mayor conveniencia,
		// con el ultimo elemento con mayor puntaje (el mas conveniente) y el primer
		// elemento con menor puntaje (el menos conveniente).
		
		return hashmapDefinitivoConPuntajes;
	}
}
