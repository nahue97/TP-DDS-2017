package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

	final static String tipoCuentaSelectedHBS = "tipo";
	final static String nombreEmpresaSelectedHBS = "empresa";
	final static String periodoSelectedHBS = "periodo";
	final static String valorSelectedHBS = "valor";
	
	final static String cuentasHBS = "cuentas";
	
	final static String filtroTodasHBS = "Todas";
	final static String filtroTodosHBS = "Todos";
	final static String filtroTiposHBS = "tipos";
	final static String filtroPeriodosHBS = "periodos";
	final static String filtroEmpresasHBS = "empresas";
	final static String filtroValorHBS = "valor";
	final static String rutaVaciaHBS = "rutaVacia";
	final static String archivoHBS = "archivo";
	final static String cargaExitosaHBS = "cargaExitosa";
	final static String cargaErroneaHBS = "cargaErronea";
	
	public static ModelAndView listar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model,filtroTodosHBS,filtroTodosHBS,filtroTodasHBS);
		return new ModelAndView(model, "cuentas/consulta.hbs");
	}


	public static ModelAndView mostrar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		String tipo = req.queryParams(tipoCuentaSelectedHBS);
		String periodo = req.queryParams(periodoSelectedHBS);
		String valor = req.queryParams(valorSelectedHBS);
		String nombreEmpresa = req.queryParams(nombreEmpresaSelectedHBS);
		Empresa empresa = new Empresa(null, nombreEmpresa);
		
		cuentas = CuentasUseCases.obtenerCuentasPor(tipo, periodo, valor, empresa);
		
		model = getDatosFiltros(model, tipo, periodo, nombreEmpresa);
		model.put(filtroValorHBS, valor);
		model.put(cuentasHBS,cuentas);
		
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
	
	
	private static Map<String, Object> getDatosFiltros(Map<String, Object> model, String fstFiltroTipo, String fstFiltroPeriodo, String fstFiltroEmpresa) {
		List<String> _periodos = new ArrayList<String>();
		Set<String> tiposDeCuentasFiltro = new LinkedHashSet<String>();
		Set<String> periodosFiltro = new LinkedHashSet<String>();
		Set<String> empresasFiltro = new LinkedHashSet<String>();
		
		if (!fstFiltroTipo.equals(filtroTodosHBS))
			tiposDeCuentasFiltro.add(fstFiltroTipo);
		if (!fstFiltroPeriodo.equals(filtroTodosHBS))
			periodosFiltro.add(fstFiltroPeriodo);
		if (!fstFiltroEmpresa.equals(filtroTodosHBS))
			empresasFiltro.add(fstFiltroEmpresa);
		
		tiposDeCuentasFiltro.add(filtroTodosHBS);
		periodosFiltro.add(filtroTodosHBS);
		empresasFiltro.add(filtroTodasHBS);
		
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> tiposDeCuentasFiltro.add(cuenta.getTipo()));
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> _periodos.add(cuenta.getPeriodo()));
		RepositorioEmpresas.getInstance().getAll().forEach(empresa -> empresasFiltro.add(empresa.getNombre()));
		_periodos.sort(String::compareToIgnoreCase);
		periodosFiltro.addAll(_periodos);
		
		model.put(filtroTiposHBS, tiposDeCuentasFiltro);
		model.put(filtroPeriodosHBS, periodosFiltro);
		model.put(filtroEmpresasHBS, empresasFiltro);
		return model;
	}

}
