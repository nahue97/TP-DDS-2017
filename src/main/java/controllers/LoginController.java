package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;

public class LoginController implements WithGlobalEntityManager, TransactionalOps{
	/*
	public ModelAndView mostrar(Request req, Response res){
		req.queryParams();
		
		Map<String, Proyecto> model = new HashMap<>();
		String id = req.params("id");
		
		Proyecto proyecto = RepositorioProyectos.instancia.buscar(Long.parseLong(id));
		model.put("proyecto", proyecto);
		return new ModelAndView(model, "proyectos/show.hbs");
	}*/
	
	public static ModelAndView login(Request req, Response res){

		return new ModelAndView(null, "login/login.hbs");
	}
}