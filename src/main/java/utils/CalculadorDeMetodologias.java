package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import model.Criterio;
import model.Empresa;
import model.EmpresaEvaluadaPorMetodologia;
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;

public class CalculadorDeMetodologias {

	private static CalculadorDeMetodologias instance;

	public static synchronized CalculadorDeMetodologias getInstance() {
		if (instance == null)
			instance = new CalculadorDeMetodologias();
		return instance;
	}

	public List<EmpresaEvaluadaPorMetodologia> calcularMetodologia(Metodologia metodologia, int anioInicial,
			int anioFinal) {
		// Aca guardamos las posta.
		LinkedHashMap<String, Integer> empresasConPuntajesFinal = new LinkedHashMap<>();
		// Empresas descalificadas por no aplicar (No tienen un indicador
		// necesario).
		List<String> empresasQueNoAplican = new ArrayList<>();
		// Empresas descalificadas por no convenir (No pasan una taxativa).
		List<String> empresasQueNoConvienen = new ArrayList<>();
		// Traemos las empresas, se van a ir sacando a medida que sean
		// descalificadas
		List<String> empresas = new ArrayList<>();
		RepositorioEmpresas.getInstance().getAll().forEach(empresa -> empresas.add(empresa.getNombre()));
		
		// Traemos las empresas y las metemos en el hash asociadas a un valor
		// inicial 0.

		for (int i = 0; i < empresas.size(); i++) {
			empresasConPuntajesFinal.put(empresas.get(i), 0);
		}

		List<Regla> reglasDeMetodologia = metodologia.getReglas();

		for (int i = 0; i < reglasDeMetodologia.size(); i++) {
			Regla regla = reglasDeMetodologia.get(i);
			evaluarRegla(regla, empresasConPuntajesFinal, empresasQueNoAplican, empresasQueNoConvienen, empresas,
					anioInicial, anioFinal);
		}

		return transformarHashMapAListaConPorcentajeDeConveniencia(empresasConPuntajesFinal, empresasQueNoAplican,
				empresasQueNoConvienen);

	}

	public void evaluarRegla(Regla regla, LinkedHashMap<String, Integer> empresasConPuntajesFinal,
			List<String> empresasQueNoAplican, List<String> empresasQueNoConvienen, List<String> empresas,
			int anioInicial, int anioFinal) {

		LinkedHashMap<String, Integer> empresasEvaluadasConPuntajes = new LinkedHashMap<>();
		LinkedHashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores = new LinkedHashMap<>();

		for (int i = 0; i < empresas.size(); i++) {
			// en evaluarReglaParaEmpresa se tratan las que no aplican, no en
			// evaluarRegla
			evaluarReglaParaEmpresa(regla, empresas.get(i), empresasConPuntajesFinal, empresasQueNoAplican,
					empresasEvaluadasConValoresDeIndicadores, empresas, anioInicial, anioFinal);
		}
		// Hasta acá empresasEvaluadasConValoresDeIndicadores tiene los
		// resultados de
		// los indicadores nada más.
		// Hay que "ordenarlas" segun conveniencia. Con "ordenarlas" me refiero
		// a darles
		// un valor que comparado con el resto de los valores de las empresas
		// las
		// posicione como más o menos convenientes que otras. En las taxativas
		// no hace
		// nada porque ya tienen 1 si conviene y 0 si no conviene.

		// Esto va a "pisar" los valores de los indicadores con los valores que
		// se van a
		// sumar en el map final.
		empresasEvaluadasConPuntajes = ordenarSegunRegla(regla, empresasEvaluadasConValoresDeIndicadores);

		// Primero barremos los resultados de las taxativas
		Iterator<Entry<String, Integer>> it = empresasEvaluadasConPuntajes.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry empresaValor = (Map.Entry) it.next();
			// empresaValor: registro del hashmap auxiliar. Hay que reflejarlo
			// en el Final.

			if (regla instanceof ReglaTaxativa) {
				// Entonces tiene valor 1 si conviene y se excluye si no
				// conviene
				if (empresaValor.getValue().equals(1)) {
					// Entonces conviene: No hacemos nada, queda en el hashmap y
					// no la excluimos
				} else {
					// Vale 0, no conviene: La excluimos de la lista de empresas
					// y el hash:
					// Buscamos la empresa en la lista de empresas y la
					// removemos
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
				// Les sumo el valor asociado en el auxiliar al HashMap de
				// empresas final.
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

	// Evalua la regla para la empresa. Si se descalifica la empresa, la saca de
	// la
	// lista de empresas (Para que no siga calculando con esa) y del hashmap
	// final y
	// se la mete en la otra lista correspondiente segun cada caso.
	public void evaluarReglaParaEmpresa(Regla regla, String empresa,
			LinkedHashMap<String, Integer> empresasConPuntajesFinal, List<String> empresasQueNoAplican,
			LinkedHashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores, List<String> empresas,
			int anioInicial, int anioFinal) {

		// Obtengo los períodos para la empresa

		Set<String> periodos = RepositorioCuentas.getInstance().getPeriodosParaEmpresa(empresa).stream()
				.collect(Collectors.toSet());
		List<String> periodosFiltrados = periodos.stream()
				.filter(p -> Integer.parseInt(p) >= anioInicial && Integer.parseInt(p) <= anioFinal)
				.collect(Collectors.toList());
		List<BigDecimal> valoresDelIndicador = new ArrayList<>();

		// Si la empresa no aplica para el indicador, se lo descalifica
		// Esto es: Si para la lista de periodos no existe ninguno que aplique
		// al
		// indicador.
		// Calculamos el indicador para los periodos y si el largo es 0 no
		// aplica.
		Empresa empresaParaCalcular = null;
		List<Empresa> empresaResult = RepositorioEmpresas.getInstance().searchByExample(new Empresa(null, empresa));
		if (empresaResult.size() > 0) {
			empresaParaCalcular = empresaResult.get(0);
		}
		for (int i = 0; i < periodosFiltrados.size(); i++) {
			String periodo = periodosFiltrados.get(i);
			valoresDelIndicador.add(
					CalculadorDeIndicadores.getInstance().calcularIndicador(regla.getIndicador(), empresaParaCalcular, periodo));
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
				acumulador = acumulador.add(valoresDelIndicador.get(i));
			}

			BigDecimal promedio = acumulador.divide(new BigDecimal(valoresDelIndicador.size()), 2,
					RoundingMode.HALF_UP);

			// Si la regla es Comparativa:
			// Metemos el valor en empresasConValoresDeCuentasAux
			// Si la regla es Taxativa:
			// Metemos directamente 1 si conviene y 0 si no conviene
			// En la view va a aparecer: Indicador 'COMPARADOR' valorAComparar
			// Ej: indicador1 < 10000
			if (regla instanceof ReglaComparativa) {

				HashMapUtils.insertarRegistro(empresasEvaluadasConValoresDeIndicadores, empresa, promedio);

			} else if (regla instanceof ReglaTaxativa) {

				BigDecimal valorAComparar = ((ReglaTaxativa) regla).getValorAComparar();
				if (((ReglaTaxativa) regla).getComparador() == '<' && valorAComparar.compareTo(promedio) == 1
						|| ((ReglaTaxativa) regla).getComparador() == '>' && promedio.compareTo(valorAComparar) == 1) {
					HashMapUtils.insertarRegistro(empresasEvaluadasConValoresDeIndicadores, empresa, new BigDecimal(1));
				} else {
					HashMapUtils.insertarRegistro(empresasEvaluadasConValoresDeIndicadores, empresa, new BigDecimal(0));
				}
			}
		}

	}

	// Debe ordenar de menor conveniencia (primer posicion) a mayor.
	public LinkedHashMap<String, Integer> ordenarSegunRegla(Regla regla,
			LinkedHashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores) {
		// Ordenamos de mas conveniente a menos conveniente segun el comparador
		// de la
		// regla si es comparativa. Si es taxativa pasamos el mismo valor.

		LinkedHashMap<String, Integer> empresasEvaluadasConPuntajes = new LinkedHashMap<String, Integer>();

		Iterator<Entry<String, BigDecimal>> it = empresasEvaluadasConValoresDeIndicadores.entrySet().iterator();

		if (regla instanceof ReglaTaxativa) {
			while (it.hasNext()) {
				Map.Entry empresaValor = (Map.Entry) it.next();
				String empresa = (String) empresaValor.getKey();
				Integer valor = ((BigDecimal) empresaValor.getValue()).intValueExact();

				HashMapUtils.insertarRegistro(empresasEvaluadasConPuntajes, empresa, valor);
			}
		} else if (regla instanceof ReglaComparativa) {
			if (((ReglaComparativa) regla).getCriterio().equals(Criterio.MENOR)) {
				it = empresasEvaluadasConValoresDeIndicadores.entrySet().stream()
						.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).iterator();
				// Tengo la lista ordenada de menor a mayor VALOR
			} else {
				it = empresasEvaluadasConValoresDeIndicadores.entrySet().stream().sorted(Map.Entry.comparingByValue())
						.iterator();
				// Tengo la lista ordenada de mayor a menor VALOR
			}

			// Ahora tengo el iterator que recorre la lista ordenada de MENOR a
			// MAYOR
			// CONVENIENCIA. A cada empresa le asigno un valor que es su
			// posicion en el
			// hashmap
			int i = 1;
			while (it.hasNext()) {
				Map.Entry empresaValor = (Map.Entry) it.next();
				String empresa = (String) empresaValor.getKey();
				// Obtengo la empresa (key) y le asocio el valor relativo al
				// resto de las
				// empresas segun la regla (i)
				// Meto esa entry en el nuevo hashmap a retornar

				HashMapUtils.insertarRegistro(empresasEvaluadasConPuntajes, empresa, i);
				i++;
			}

			// Hasta aca tenemos el hashmap empresasEvaluadasConPuntajes con los
			// puntajes de
			// menor a mayor conveniencia.
		}

		return empresasEvaluadasConPuntajes;

	}

	// Fusiona las listas con el hashmap y reemplaza todos los valores por:
	// "No Aplica", "No Conviene" y, si conviene, porcentaje de conveniencia.
	public List<EmpresaEvaluadaPorMetodologia> transformarHashMapAListaConPorcentajeDeConveniencia(
			LinkedHashMap<String, Integer> empresasConPuntajesFinal, List<String> empresasQueNoAplican,
			List<String> empresasQueNoConvienen) {

		List<EmpresaEvaluadaPorMetodologia> empresasEvaluadasPorMetodologia = new ArrayList<>();

		// Aca si los valores de TODAS las empresas que quedaron en el hashmap
		// son CERO,
		// quiere decir que son todas reglas taxativas, y si quedaron en el
		// hashmap es
		// porque las pasaron asi que todas convienen por igual y se les asigna
		// el texto
		// "Conviene". Primero checkeamos ese caso.

		List<Integer> valores = new ArrayList<>();
		valores.addAll(empresasConPuntajesFinal.values());

		if (valores.stream().allMatch(valor -> valor == 0)) {
			// Entonces son todos 0, todas convienen.
			Iterator iterator = empresasConPuntajesFinal.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Integer> registro = (Map.Entry<String, Integer>) iterator.next();
				empresasEvaluadasPorMetodologia
						.add(new EmpresaEvaluadaPorMetodologia((String) registro.getKey(), "Conviene"));
			}
		} else {
			// Sino, no son todas taxativas, por lo cual ya estamos hablando de
			// porcentajes
			// de conveniencia.
			Integer mayorPuntaje = 0;
			Iterator iterator = empresasConPuntajesFinal.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Integer> registro = (Map.Entry<String, Integer>) iterator.next();
				Integer puntajeEmpresa = (Integer) registro.getValue();
				if (puntajeEmpresa > mayorPuntaje) {
					mayorPuntaje = puntajeEmpresa;
				}

			}

			// Ahora tenemos el mayor valor de todos y podemos sacar el
			// porcentaje de
			// conveniencia del resto en base a este. Este va a ser el 100%.

			iterator = empresasConPuntajesFinal.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry empresaValor = (Map.Entry) iterator.next();
				Integer puntajeEmpresa = (Integer) empresaValor.getValue();

				// Dividimos el puntaje de la empresa por el mayor puntaje de
				// todos * 100
				double porcentajeDeConveniencia = ((double) puntajeEmpresa) / ((double) mayorPuntaje) * 100;

				empresasEvaluadasPorMetodologia.add(new EmpresaEvaluadaPorMetodologia((String) empresaValor.getKey(),
						Double.toString(porcentajeDeConveniencia)));
			}

			Collections.sort(empresasEvaluadasPorMetodologia, new Comparator<EmpresaEvaluadaPorMetodologia>() {
				@Override
				public int compare(EmpresaEvaluadaPorMetodologia e1, EmpresaEvaluadaPorMetodologia e2) {
					return (int) (Double.parseDouble(e2.getConveniencia()) - Double.parseDouble(e1.getConveniencia()));
				}
			});

			DecimalFormat df = new DecimalFormat("###.## '%'");
			df.setRoundingMode(RoundingMode.HALF_UP);
			for (int i = 0; i < empresasEvaluadasPorMetodologia.size(); i++) {

				String valor = df.format(Double.parseDouble(empresasEvaluadasPorMetodologia.get(i).getConveniencia()));
				empresasEvaluadasPorMetodologia.get(i).setConveniencia(valor);
			}

		}

		// Hasta aca tenemos agregadas las empresas que convienen, en su
		// totalidad si
		// son todas reglas taxativas, o en cierto porcentaje si hay reglas
		// comparativas. Ahora lo que falta es agregar a esta lista las empresas
		// que no
		// convienen y las que no aplican.

		empresasQueNoConvienen.forEach(empresa -> empresasEvaluadasPorMetodologia
				.add(new EmpresaEvaluadaPorMetodologia(empresa, "No conviene")));

		empresasQueNoAplican.forEach(empresa -> empresasEvaluadasPorMetodologia
				.add(new EmpresaEvaluadaPorMetodologia(empresa, "No aplica")));

		return empresasEvaluadasPorMetodologia;
	}
}
