package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EmpresasController {
	
	public static ModelAndView nuevo(Request req, Response res){

		return new ModelAndView(null, "empresas/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){

		return new ModelAndView(null, "login/login.hbs");
	}
	
}
