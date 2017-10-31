package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.IndicadorCalculado;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import useCases.IndicadoresUseCases;

public class IndicadoresController {
	private static String nombreEmpresaHBS = "empresa";
	private static String periodoHBS = "periodo";
	private static String indicadorHBS = "indicador";
	
	final static String indicadoresHBS = "indicadores";
	
	final static String filtroTodasHBS = "Todas";
	final static String filtroTodosHBS = "Todos";
	final static String filtroIndicadorHBS = "nombreIndicadores";
	final static String filtroEmpresasHBS = "empresas";
	final static String filtroPeriodosHBS = "periodos";
	
	final static String cargaExitosaHBS = "cargaExitosa";
	final static String cargaErroneaHBS = "cargaErronea";
	final static String mensajeDeErrorHBS = "mensajeDeError";
	
	final static String nombreIndicadorCargaHBS = "nombre";
	final static String formulaIndicadorCargaHBS = "formula";
	
	final static String consultaIndicadoresHBS = "indicadores/consulta.hbs";
	final static String cargaDeIndicadoresHBS = "indicadores/carga.hbs";

	public static ModelAndView listar(Request req, Response res) {
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getEmpresasYPeriodos(model, filtroTodosHBS, filtroTodasHBS, filtroTodosHBS);
		return new ModelAndView(model, consultaIndicadoresHBS);
	}

	public static ModelAndView mostrar(Request req, Response res) {
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		
		List<IndicadorCalculado> indicadores = new ArrayList<IndicadorCalculado>();
		
		String indicador = req.queryParams(indicadorHBS);
		String empresa = req.queryParams(nombreEmpresaHBS);
		String periodo = req.queryParams(periodoHBS);

		indicadores = IndicadoresUseCases.obtenerIndicadoresPor(indicador, empresa, periodo);

		model = getEmpresasYPeriodos(model, indicador, empresa, periodo);
		model.put(indicadoresHBS, indicadores);
		return new ModelAndView(model, consultaIndicadoresHBS);
	}

	public static ModelAndView nuevo(Request req, Response res) {
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, cargaDeIndicadoresHBS);
	}

	
	public static ModelAndView crear(Request req, Response res) {
		
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		String nombre = req.queryParams( nombreIndicadorCargaHBS);
		String formula = req.queryParams( formulaIndicadorCargaHBS);
		
		try {
			
			IndicadoresUseCases.crearIndicador(nombre, formula);
			model.put(cargaExitosaHBS, true);
			
		} catch (Exception e) {
			
			model.put(cargaErroneaHBS, true);
			model.put(mensajeDeErrorHBS, e.getMessage());
		}
		
		return new ModelAndView(model, cargaDeIndicadoresHBS);
	}

	private static Map<String, Object> getEmpresasYPeriodos(Map<String, Object> model, String fstFiltroIndicador, String fstFiltroEmpresa, String fstFiltroPeriodo) {
		
		Set<String> indicadores = new LinkedHashSet<String>();
		Set<String> empresas = new LinkedHashSet<String>();
		Set<String> periodos = new LinkedHashSet<String>();
		
		if (!fstFiltroIndicador.equalsIgnoreCase(filtroTodosHBS))
			indicadores.add(fstFiltroIndicador);
		if (!fstFiltroEmpresa.equalsIgnoreCase(filtroTodasHBS))
			empresas.add(fstFiltroEmpresa);
		if (!fstFiltroPeriodo.equalsIgnoreCase(filtroTodosHBS))
			periodos.add(fstFiltroPeriodo);
		
		indicadores.add(filtroTodosHBS);
		empresas.add(filtroTodasHBS);
		periodos.add(filtroTodosHBS);
		
		
		RepositorioCuentas.getInstance().getEmpresasConCuenta().forEach(empresa -> empresas.add(empresa.getNombre()));
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodos.add(cuenta.getPeriodo()));
		RepositorioIndicadores.getInstance().getAll().forEach(indicador -> indicadores.add(indicador.getNombre()));
		
		model.put(filtroPeriodosHBS, periodos);
		model.put(filtroEmpresasHBS, empresas);
		model.put(filtroIndicadorHBS, indicadores);
		
		return model;
	}
}
