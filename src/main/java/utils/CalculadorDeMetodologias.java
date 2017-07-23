package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Indicador;
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioCuentas;

public class CalculadorDeMetodologias {

	private static CalculadorDeMetodologias instance;

	public static synchronized CalculadorDeMetodologias getInstance() {
		if (instance == null)
			instance = new CalculadorDeMetodologias();
		return instance;
	}

	public HashMap<String, String> calcularMetodologia(Metodologia metodologia) {
		// Aca guardamos las posta.
		HashMap<String, Integer> empresasConPuntajesFinal = new HashMap<>();
		// Empresas descalificadas por no aplicar (No tienen un indicador necesario).
		List<String> empresasQueNoAplican = new ArrayList<>();
		// Empresas descalificadas por no convenir (No pasan una taxativa).
		List<String> empresasQueNoConvienen = new ArrayList<>();
		// Traemos las empresas, se van a ir sacando a medida que sean descalificadas
		List<String> empresas = RepositorioCuentas.getInstance().getEmpresasDeCuentas();
		// Traemos las empresas y las metemos en el hash asociadas a un valor inicial 0.

		for (int i = 0; i < empresas.size(); i++) {
			empresasConPuntajesFinal.put(empresas.get(i), 0);
		}

		List<Regla> reglasDeMetodologia = metodologia.getReglas();

		for (int i = 0; i < reglasDeMetodologia.size(); i++) {
			Regla regla = reglasDeMetodologia.get(i);
			evaluarRegla(regla, empresasConPuntajesFinal, empresasQueNoAplican, empresasQueNoConvienen, empresas);
		}

		return transformarHashMapAListaConPorcentajeDeConveniencia(empresasConPuntajesFinal, empresasQueNoAplican,
				empresasQueNoConvienen);

	}

	private void evaluarRegla(Regla regla, HashMap<String, Integer> empresasConPuntajesFinal,
			List<String> empresasQueNoAplican, List<String> empresasQueNoConvienen, List<String> empresas) {

		HashMap<String, Integer> empresasEvaluadasConPuntajes = new HashMap<>();
		HashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores = new HashMap<>();

		for (int i = 0; i < empresas.size(); i++) {
			// en evaluarReglaParaEmpresa se tratan las que no aplican, no en evaluarRegla
			evaluarReglaParaEmpresa(regla, empresas.get(i), empresasConPuntajesFinal, empresasQueNoAplican,
					empresasEvaluadasConValoresDeIndicadores, empresas);
		}
		// Hasta acá empresasEvaluadasConValoresDeIndicadores tiene los resultados de
		// los
		// indicadores nada más.
		// Hay que "ordenarlas" segun conveniencia. Con "ordenarlas" me refiero a darles
		// un valor que comparado con el resto de los valores de las empresas las
		// posicione como más o menos convenientes que otras. En las taxativas no hace
		// nada.

		// Esto va a "pisar" los valores de los indicadores con los valores que se van a
		// sumar en el map final.
		empresasEvaluadasConPuntajes = ordenarSegunRegla(regla, empresasEvaluadasConValoresDeIndicadores);

		
		// Primero barremos los resultados de las taxativas
		Iterator<Entry<String, Integer>> it = empresasEvaluadasConPuntajes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry empresaValor = (Map.Entry) it.next();
			// empresaValor: registro del hashmap auxiliar. Hay que reflejarlo en el Final.

			if (regla instanceof ReglaTaxativa) {
				// Entonces tiene valor 1 si conviene y se excluye si no conviene
				if (empresaValor.getValue().equals(1)) {
					// Entonces conviene: No hacemos nada, queda en el hashmap y no la excluimos
				} else {
					// Vale 0, no conviene: La excluimos de la lista de empresas y el hash:
					// Buscamos la empresa en la lista de empresas y la removemos
					for (int i = 0; i < empresas.size(); i++) {
						if (empresas.get(i).equals((String) empresaValor.getKey())) {
							empresas.remove(i);
						}
					}

					// Ahora lo removemos del HashMap final
					HashMapUtils.eliminarRegistro(empresasConPuntajesFinal, (String) empresaValor.getKey());

					// Lo agregamos a la lista de las que no convienen.
					empresasQueNoConvienen.add(empresaValor.getKey().toString());
					
				}
			} else if (regla instanceof ReglaComparativa) {
				// Les sumo el valor asociado en el auxiliar al HashMap de empresas final.
				Integer valorFinal = HashMapUtils.obtenerValorPorClave(empresasConPuntajesFinal,
						(String) empresaValor.getKey());
				if (valorFinal != null) {
					HashMapUtils.insertarRegistro(empresasConPuntajesFinal, (String) empresaValor.getKey(),
							valorFinal + ((Integer) empresaValor.getValue()));
				}

			}
		}

		// Listo =)

	}

	// Evalua la regla para la empresa. Si se descalifica la empresa, la saca de la
	// lista de empresas (Para que no siga calculando con esa) y del hashmap final y
	// se la mete en la otra lista correspondiente segun cada caso.
	private void evaluarReglaParaEmpresa(Regla regla, String empresa, HashMap<String, Integer> empresasConPuntajesFinal,
			List<String> empresasQueNoAplican, HashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores,
			List<String> empresas) {

		// Obtengo los períodos para la empresa

		List<String> periodos = RepositorioCuentas.getInstance().getPeriodosParaEmpresa(empresa);
		List<BigDecimal> valoresDelIndicador = new ArrayList<>();

		// Si la empresa no aplica para el indicador, se lo descalifica
		// Esto es: Si para la lista de periodos no existe ninguno que aplique al
		// indicador.
		// Calculamos el indicador para los periodos y si el largo es 0 no aplica.

		for (int i = 0; i < periodos.size(); i++) {
			String periodo = periodos.get(i);
			valoresDelIndicador.add(
					CalculadorDeIndicadores.getInstance().calcularIndicador(regla.getIndicador(), empresa, periodo));
		}

		if (valoresDelIndicador.size() == 0) {
			// No aplica, la descalificamos
			HashMapUtils.eliminarRegistro(empresasConPuntajesFinal, empresa);

			HashMapUtils.eliminarRegistro(empresasEvaluadasConValoresDeIndicadores, empresa);

			for (int i = 0; i < empresas.size(); i++) {
				String _empresa = empresas.get(i);
				if (_empresa.equals(empresa)) {
					empresas.remove(i);
					empresasQueNoAplican.add(empresa);
				}
			}
		} else {
			// Sacamos el promedio del valor del indicador para la empresa
			BigDecimal acumulador = new BigDecimal(0);
			for (int i = 0; i < valoresDelIndicador.size(); i++) {
				acumulador.add(valoresDelIndicador.get(i));
			}

			BigDecimal promedio = acumulador.divide(new BigDecimal(valoresDelIndicador.size()));

			// Si la regla es Comparativa:
			// Metemos el valor en empresasConValoresDeCuentasAux
			// Si la regla es Taxativa:
			// Metemos directamente 1 si conviene y 0 si no conviene
			// En la view va a aparecer: Indicador 'COMPARADOR' valorAComparar
			// Ej: indicador1 < 10000
			if (regla instanceof ReglaComparativa) {
				HashMapUtils.insertarRegistro(empresasEvaluadasConValoresDeIndicadores, empresa, promedio);
			} else if (regla instanceof ReglaTaxativa) {
				if (((ReglaTaxativa) regla).getComparador() == '<'
						&& promedio.compareTo(((ReglaTaxativa) regla).getValorAComparar()) == 1
						|| ((ReglaTaxativa) regla).getComparador() == '>'
								&& ((ReglaTaxativa) regla).getValorAComparar().compareTo(promedio) == 1) {
					HashMapUtils.insertarRegistro(empresasEvaluadasConValoresDeIndicadores, empresa, new BigDecimal(1));
				} else {
					HashMapUtils.insertarRegistro(empresasEvaluadasConValoresDeIndicadores, empresa, new BigDecimal(0));
				}
			}
		}

	}

	// Debe ordenar de menor conveniencia (primer posicion) a mayor.
	private HashMap<String, Integer> ordenarSegunRegla(Regla regla,
			HashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores) {
		// Ordenamos de mas conveniente a menos conveniente segun el comparador de la
		// regla si es comparativa. Si es taxativa pasamos el mismo valor casteado a
		// integer.

		HashMap<String, Integer> empresasEvaluadasConPuntajes = new HashMap<String, Integer>();

		Iterator<Entry<String, BigDecimal>> it = empresasEvaluadasConValoresDeIndicadores.entrySet().iterator();
		while (it.hasNext()) {
			// empresasConPuntajesFinal tiene un valor para cada empresa, entonces sumo la
			// posicion de la empresa a ese valor.
			Map.Entry empresaValor = (Map.Entry) it.next();

			if (regla instanceof ReglaTaxativa) {
				HashMapUtils.insertarRegistro(empresasEvaluadasConPuntajes, (String) empresaValor.getKey(),
						((BigDecimal) empresaValor.getValue()).intValueExact());
			} else if (regla instanceof ReglaComparativa) {
				// Hay que checkear conveniencia segun valor del indicador

			}

		}

		return empresasEvaluadasConPuntajes;
		// TODO Auto-generated method stub

	}

	// Fusiona las listas con el hashmap y reemplaza todos los valores por:
	// "No Aplica", "No Conviene" y, si conviene, porcentaje de conveniencia.
	private HashMap<String, String> transformarHashMapAListaConPorcentajeDeConveniencia(
			HashMap<String, Integer> empresasConPuntajesFinal, List<String> empresasQueNoAplican,
			List<String> empresasQueNoConvienen) {
		// TODO Auto-generated method stub
		return null;
	}
}
