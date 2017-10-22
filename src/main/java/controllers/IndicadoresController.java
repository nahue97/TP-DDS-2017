package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.uqbar.commons.model.UserException;
import model.Empresa;
import model.IndicadorCalculado;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.AnalizadorDeFormulas;
import utils.AppData;
import utils.CalculadorDeIndicadores;

public class IndicadoresController {
	static List<IndicadorCalculado> indicadores = new ArrayList<IndicadorCalculado>();
	static List<Empresa> empresasACalcular = new ArrayList<>();
	private static String nombreEmpresaHBS = "empresa";
	private static String periodoHBS = "periodo";

	public static ModelAndView listar(Request req, Response res) {
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getEmpresasYPeriodos(model);
		return new ModelAndView(model, "indicadores/consulta.hbs");
	}

	public static ModelAndView mostrar(Request req, Response res) {
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();

		String empresa = req.queryParams(nombreEmpresaHBS);
		String periodo = req.queryParams(periodoHBS);

		if (empresa.equals("Todas") && !periodo.isEmpty()) {

			CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
			empresasACalcular = RepositorioCuentas.getInstance().getEmpresasConCuenta();
			empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, periodo)
					.forEach(indCalc -> indicadores.add(indCalc)));
			model.put("indicadores", indicadores);
		}
		if (periodo.isEmpty()) {
			throw new UserException("El campo Periodo esta vacio");
		}
		if (!empresa.equals("Todas") && !periodo.isEmpty()) {
			CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
			Empresa empresaParaCalcular = RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa);
			indicadores = calculadorDeIndicadores.calcularIndicadores(empresaParaCalcular, periodo);
			model.put("indicadores", indicadores);
		}
		model = getEmpresasYPeriodos(model);
		return new ModelAndView(model, "indicadores/consulta.hbs");
	}

	public static ModelAndView nuevo(Request req, Response res) {
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, "indicadores/carga.hbs");
	}

	public static ModelAndView crear(Request req, Response res) {
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		String nombre = req.queryParams( "nombre");
		String formula = req.queryParams( "formula");
		try {
			AnalizadorDeFormulas analizador = new AnalizadorDeFormulas();
			String formulaAnalizada = analizador.analizarYSimplificarFormula(formula);
			AppData.getInstance().guardarIndicador(formulaAnalizada, nombre);
			model.put("cargaExitosa", true);
		} catch (Exception e) {
			model.put("cargaErronea", true);
			model.put("mensajeDeError", e.getMessage());
		}
		return new ModelAndView(model, "indicadores/carga.hbs");
	}

	private static Map<String, Object> getEmpresasYPeriodos(Map<String, Object> model) {
		List<String> empresas = new ArrayList<String>();
		RepositorioEmpresas.getInstance().getAll().forEach(empresa -> empresas.add(empresa.getNombre()));
		model.put("empresas", empresas);
		Set<String> periodos = new HashSet<String>();
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodos.add(cuenta.getPeriodo()));
		model.put("periodos", periodos);
		return model;
	}
}
