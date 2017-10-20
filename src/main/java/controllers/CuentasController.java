package controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ExceptionsPackage.RutaDeArchivoInvalidaException;
import dtos.PathFileTxtJson;
import model.Empresa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.AppData;
import utils.RequestUtil.getString;

public class CuentasController {

	public static ModelAndView listar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model);
		return new ModelAndView(model, "cuentas/consulta.hbs");
	}


	public static ModelAndView mostrar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		
		String tipo = getString.get(req,"tipo");
		Empresa empresa = new Empresa(null, getString.get(req,"empresa"));
		String periodo = getString.get(req,"periodo");
		String valor = getString.get(req,"valor");
		BigDecimal value = new BigDecimal("0");
		
		if (tipo.equals("Todas") && periodo.equals("Todos") && empresa.getNombre().equals("Todas") && valor.equals("Todos"))
			model.put("cuentas", RepositorioCuentas.getInstance().getAll());

		if (!tipo.equals("Todas") || !periodo.equals("Todos") || !empresa.getNombre().equals("Todas") || !valor.equals("Todos")){
			if (tipo.equals("Todas"))
				tipo = null;
			if (empresa.getNombre().equals("Todas"))
				empresa = null;
			else
				empresa = RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa.getNombre());
			if (periodo.equals("Todos"))
				periodo = null;
			if (valor.equals(""))
				value = null;
			else
				value = new BigDecimal(valor);
			
			model.put("cuentas", RepositorioCuentas.getInstance().filtrarCuentas(tipo, empresa, periodo, value));
		}

		model = getDatosFiltros(model);
		return new ModelAndView(model, "cuentas/consulta.hbs");
	}
	
	public static ModelAndView nuevo(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, "cuentas/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
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
	
	
	private static Map<String, Object> getDatosFiltros(Map<String, Object> model) {
		Set<String> tipoDeCuentas = new HashSet<String>();
		Set<String> periodos = new HashSet<String>();
		Set<String> empresas = new HashSet<String>();
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> tipoDeCuentas.add(cuenta.getTipo()));
		model.put("tipos", tipoDeCuentas);
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodos.add(cuenta.getPeriodo()));
		model.put("periodos", periodos);
		RepositorioEmpresas.getInstance().getAll().forEach(empresa -> empresas.add(empresa.getNombre()));
		model.put("empresas", empresas);
		return model;
	}
}
