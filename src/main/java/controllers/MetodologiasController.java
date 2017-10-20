package controllers;

import java.util.HashMap;
import java.util.Map;

import model.repositories.RepositorioMetodologias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MetodologiasController {

	public static ModelAndView listar(Request req, Response res){

		return new ModelAndView(null, "metodologias/consulta.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		model.put("metodologias", RepositorioMetodologias.getInstance().getAll());

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
}
