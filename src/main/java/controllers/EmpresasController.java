package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.TransactionalException;

import model.Empresa;
import model.repositories.RepositorioEmpresas;
import model.repositories.RepositorioMetodologias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.RequestUtil.getString;

public class EmpresasController {
	
	public static ModelAndView nuevo(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, "empresas/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		
		Empresa emp = new Empresa(null,getString.get(req,"nombre"));
		
		if (RepositorioEmpresas.getInstance().existeEmpresa(emp)){
			model.put("empresaExistente", true);
			return new ModelAndView(model, "empresas/carga.hbs");
		}			
		
		try{
			RepositorioEmpresas.getInstance().add(emp);
			model.put("cargaExitosa", true);
		} catch (TransactionalException e){
			model.put("cargaErronea", true);			
		}
		return new ModelAndView(model, "empresas/carga.hbs");
	}
	
}
