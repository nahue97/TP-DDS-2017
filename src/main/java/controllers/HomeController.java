package controllers;

import java.util.HashMap;
import java.util.Map;

import model.repositories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

	public static ModelAndView home(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		model.put("cantEmpresas", RepositorioEmpresas.getInstance().getAll().size());
		model.put("cantIndicadores", RepositorioIndicadores.getInstance().getAll().size());
		model.put("cantCuentas", RepositorioCuentas.getInstance().getAll().size());
		model.put("cantMetodologias", RepositorioMetodologias.getInstance().getAll().size());
		
		return new ModelAndView(null, "home/home.hbs");
	}
	
}
