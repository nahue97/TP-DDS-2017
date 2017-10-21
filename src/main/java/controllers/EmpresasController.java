package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.TransactionalException;

import model.Empresa;
import model.repositories.RepositorioEmpresas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EmpresasController {
	
	private static String nombreEmpresaHBS = "nombre";
	private static String empresaExistenteHBS = "empresaExistente";
	private static String cargaExitosaHBS = "cargaExitosa";
	private static String cargaErroneaHBS = "cargaErronea";
	
	public static ModelAndView nuevo(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, "empresas/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		
		Empresa emp = new Empresa(null,req.queryParams(nombreEmpresaHBS));
		
		if (RepositorioEmpresas.getInstance().existeEmpresa(emp)){
			model.put(empresaExistenteHBS, true);
			return new ModelAndView(model, "empresas/carga.hbs");
		}			
		
		try{
			RepositorioEmpresas.getInstance().add(emp);
			model.put(cargaExitosaHBS, true);
		} catch (TransactionalException e){
			model.put(cargaErroneaHBS, true);			
		}
		return new ModelAndView(model, "empresas/carga.hbs");
	}
	
}
