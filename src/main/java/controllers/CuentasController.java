package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import useCases.CuentasUseCases;
import utils.AppData;
import model.Cuenta;

public class CuentasController {
	
	private static Set<String> tipoDeCuentas = new HashSet<String>();
	private static Set<String> periodos = new HashSet<String>();
	private static Set<String> empresas = new HashSet<String>();
	private static String tipoCuentaHBS = "tipo";
	private static String nombreEmpresaHBS = "empresa";
	private static String periodoHBS = "periodo";
	private static String valorHBS = "valor";
	private static String cuentasHBS = "cuentas";
	private static String rutaVaciaHBS = "rutaVacia";
	private static String archivoHBS = "archivo";
	private static String cargaExitosaHBS = "cargaExitosa";
	private static String cargaErroneaHBS = "cargaErronea";
	
	public static ModelAndView listar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model);
		return new ModelAndView(model, "cuentas/consulta.hbs");
	}


	public static ModelAndView mostrar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		String tipo = req.queryParams(tipoCuentaHBS);
		String periodo = req.queryParams(periodoHBS);
		String valor = req.queryParams(valorHBS);
		String nombreEmpresa = req.queryParams(nombreEmpresaHBS);
		Empresa empresa = new Empresa(null, nombreEmpresa);
		
		cuentas = CuentasUseCases.obtenerCuentasPor(tipo, periodo, valor, empresa);
		
				
		model.put(cuentasHBS,cuentas);
		model.put(tipoCuentaHBS, tipoDeCuentas.add(tipo));
		model.put(nombreEmpresaHBS, empresas.add(nombreEmpresa));
		model.put(periodoHBS, periodos.add(periodo));
		model.put(valorHBS, valor);
		return new ModelAndView(model, "cuentas/consulta.hbs");
	}
	
	public static ModelAndView nuevo(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, "cuentas/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		
		String ruta = req.queryParams(archivoHBS);
		
		if (ruta.isEmpty()){
			model.put(rutaVaciaHBS, true);
		}
		
		try {
			String rutaCompleta = "./Archivos de prueba/" + ruta ;
			PathFileTxtJson datosDeCarga = new PathFileTxtJson(rutaCompleta);
			AppData.getInstance().cargarCuentas(datosDeCarga);
			model.put(cargaExitosaHBS, true);
		} catch (RutaDeArchivoInvalidaException e){
			model.put(cargaErroneaHBS, true);
		}
		return new ModelAndView(model, "cuentas/carga.hbs");
	}
	
	
	private static Map<String, Object> getDatosFiltros(Map<String, Object> model) {
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> tipoDeCuentas.add(cuenta.getTipo()));
		model.put("tipos", tipoDeCuentas);
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodos.add(cuenta.getPeriodo()));
		model.put("periodos", periodos);
		RepositorioEmpresas.getInstance().getAll().forEach(empresa -> empresas.add(empresa.getNombre()));
		model.put("empresas", empresas);
		return model;
	}
}
