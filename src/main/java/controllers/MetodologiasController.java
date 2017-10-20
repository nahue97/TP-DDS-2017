package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.EmpresaEvaluadaPorMetodologia;
import model.Metodologia;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioMetodologias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.CalculadorDeMetodologias;
import utils.RequestUtil.getString;

public class MetodologiasController {
	private static List<EmpresaEvaluadaPorMetodologia> empresasEvaluadasPorMetodologias = new ArrayList<EmpresaEvaluadaPorMetodologia>();
	
	public static ModelAndView listar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model);
		return new ModelAndView(model, "metodologias/consulta.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model);
		String metodologia = getString.get(req,"metodologia");
		String periodoDesde = getString.get(req,"periodoDesde");
		String periodoHasta = getString.get(req,"periodoHasta");
		
		if (Integer.parseInt(periodoDesde) > Integer.parseInt(periodoHasta)){
			model.put("errorPeriodos", true);
		}else{
			try{
				Metodologia metodologiaACalcular = RepositorioMetodologias.getInstance().getMetodologiaPorNombre(metodologia);
				setEmpresasEvaluadasPorMetodologias(CalculadorDeMetodologias.getInstance().calcularMetodologia(metodologiaACalcular,
						Integer.parseInt(periodoDesde), Integer.parseInt(periodoHasta)));
		
				model.put("metodologiasCalculadas",empresasEvaluadasPorMetodologias);
				return new ModelAndView(model, "metodologias/consulta.hbs");
			}catch(RuntimeException e){
				model.put("errorCalculo", true);
			}	
		}
		return new ModelAndView(model, "metodologias/consulta.hbs");
	}


	public static ModelAndView nuevo(Request req, Response res){

		return new ModelAndView(null, "metodologias/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){

		return new ModelAndView(null, "metodologias/carga.hbs");
	}
	
	public static ModelAndView listarReglas(Request req, Response res){

		return new ModelAndView(null, "metodologias/reglas/listado.hbs");
	}
	
	public static ModelAndView nuevaTaxativa(Request req, Response res){

		return new ModelAndView(null, "metodologias/reglas/taxativa.hbs");
	}
	
	public static ModelAndView nuevaComparativa(Request req, Response res){

		return new ModelAndView(null, "metodologias/reglas/comparativa.hbs");
	}
	
	private static Map<String, Object> getDatosFiltros(Map<String, Object> model) {
		Set<String> tipoDeCuentas = new HashSet<String>();
		Set<String> periodosDesde = new HashSet<String>();
		Set<String> periodosHasta = new HashSet<String>();
		Set<String> metodologias = new HashSet<String>();
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> tipoDeCuentas.add(cuenta.getTipo()));
		model.put("tipos", tipoDeCuentas);
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodosDesde.add(cuenta.getPeriodo()));
		model.put("periodosDesde", periodosDesde);
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodosHasta.add(cuenta.getPeriodo()));
		model.put("periodosHasta", periodosHasta);
		RepositorioMetodologias.getInstance().getAll().forEach(metodologia -> metodologias.add(metodologia.getNombre()));
		model.put("metodologias", metodologias );
		
		return model;
	}

	public static List<EmpresaEvaluadaPorMetodologia> getEmpresasEvaluadasPorMetodologias() {
		return empresasEvaluadasPorMetodologias;
	}

	public static void setEmpresasEvaluadasPorMetodologias(List<EmpresaEvaluadaPorMetodologia> empresasEvaluadasPorMetodologias) {
		MetodologiasController.empresasEvaluadasPorMetodologias = empresasEvaluadasPorMetodologias;
	}
	

}
