package controllers;

import java.util.HashMap;
import java.util.Map;

import model.repositories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

	public static ModelAndView home(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model.put("cantEmpresas", RepositorioEmpresas.getInstance().count());
		model.put("cantIndicadores", RepositorioIndicadores.getInstance().count());
		model.put("cantCuentas", RepositorioCuentas.getInstance().count());
		model.put("cantMetodologias", RepositorioMetodologias.getInstance().count());
		
		return new ModelAndView(model, "home/home.hbs");
	}
	
}
