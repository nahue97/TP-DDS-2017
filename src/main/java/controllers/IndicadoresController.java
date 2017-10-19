package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndicadoresController {

	public static ModelAndView listar(Request req, Response res){

		return new ModelAndView(null, "login/login.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res){

		return new ModelAndView(null, "login/login.hbs");
	}
	
	public static ModelAndView nuevo(Request req, Response res){

		return new ModelAndView(null, "login/login.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){

		return new ModelAndView(null, "login/login.hbs");
	}
	
}
