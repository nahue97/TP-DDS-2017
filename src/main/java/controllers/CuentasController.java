package controllers;

import java.util.HashMap;
import java.util.Map;

import ExceptionsPackage.RutaDeArchivoInvalidaException;
import dtos.PathFileTxtJson;
import model.repositories.RepositorioCuentas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.AppData;
import utils.RequestUtil.getString;

public class CuentasController {

	public static ModelAndView listar(Request req, Response res){

		return new ModelAndView(null, "cuentas/consulta.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		model.put("cuentas", RepositorioCuentas.getInstance().getAll());

		return new ModelAndView(model, "cuentas/consulta.hbs");
	}
	
	public static ModelAndView nuevo(Request req, Response res){

		return new ModelAndView(null, "cuentas/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		String ruta = getString.get(req,"archivo");
		
		if (ruta.isEmpty()){
			model.put("rutaVacia", true);
		}
		
		String rutaCompleta = "./Archivos de prueba/" + ruta ;
		
		PathFileTxtJson datosDeCarga = new PathFileTxtJson(rutaCompleta);
		
		try {
			AppData.getInstance().cargarCuentas(datosDeCarga);
			model.put("cargaExitosa", true);
		} catch (RutaDeArchivoInvalidaException e){
			model.put("cargaErronea", true);
		}
		
		return new ModelAndView(model, "cuentas/carga.hbs");
	}
}
