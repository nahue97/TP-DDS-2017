package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ExceptionsPackage.RutaDeArchivoInvalidaException;
import model.Empresa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import useCases.CuentasUseCases;
import model.Cuenta;

public class CuentasController {

	final static String tipoCuentaSeleccionadoHBS = "tipo";
	final static String nombreEmpresaSeleccionadoHBS = "empresa";
	final static String periodoSeleccionadoHBS = "periodo";
	final static String valorSeleccionadoHBS = "Valor";
	
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
	
	final static String consultaCuentasHBS = "cuentas/consulta.hbs";
	final static String cargaDeCuentasHBS = "cuentas/carga.hbs";
	
	final static String rutaArchivoDeCuentas = "./Archivos de prueba/";
	
	public static ModelAndView listar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model,filtroTodosHBS,filtroTodosHBS,filtroTodasHBS, valorSeleccionadoHBS);
		return new ModelAndView(model, consultaCuentasHBS);
	}


	public static ModelAndView mostrar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		String tipo = req.queryParams(tipoCuentaSeleccionadoHBS);
		String periodo = req.queryParams(periodoSeleccionadoHBS);
		String valor = req.queryParams(valorSeleccionadoHBS);
		String nombreEmpresa = req.queryParams(nombreEmpresaSeleccionadoHBS);
		Empresa empresa = new Empresa(null, nombreEmpresa);
		
		cuentas = CuentasUseCases.obtenerCuentasPor(tipo, periodo, valor, empresa);
		
		model = getDatosFiltros(model, tipo, periodo, nombreEmpresa, valor);
		model.put(cuentasHBS,cuentas);
		
		return new ModelAndView(model, consultaCuentasHBS);
	}
	
	public static ModelAndView nuevo(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, cargaDeCuentasHBS);
	}
	
	public static ModelAndView crear(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		
		String ruta = req.queryParams(archivoHBS);
		
		if (ruta.isEmpty()){
			model.put(rutaVaciaHBS, true);
		}
		
		try {
			String rutaCompleta = rutaArchivoDeCuentas + ruta ;
			CuentasUseCases.cargarArchivoDeCuentas(rutaCompleta);
			model.put(cargaExitosaHBS, true);
			model.put("nombreArchivo", ruta);
		} catch (RutaDeArchivoInvalidaException e){
			model.put(cargaErroneaHBS, true);
			model.put("nombreArchivo", ruta);
		}
		return new ModelAndView(model, cargaDeCuentasHBS);
	}
	
	
	private static Map<String, Object> getDatosFiltros(Map<String, Object> model, String fstFiltroTipo, String fstFiltroPeriodo, String fstFiltroEmpresa, String filtroValor) {
		List<String> _periodos = new ArrayList<String>();
		Set<String> tiposDeCuentasFiltro = new LinkedHashSet<String>();
		Set<String> periodosFiltro = new LinkedHashSet<String>();
		Set<String> empresasFiltro = new LinkedHashSet<String>();
		
		if (!fstFiltroTipo.equalsIgnoreCase(filtroTodosHBS))
			tiposDeCuentasFiltro.add(fstFiltroTipo);
		if (!fstFiltroPeriodo.equalsIgnoreCase(filtroTodosHBS))
			periodosFiltro.add(fstFiltroPeriodo);
		if (!fstFiltroEmpresa.equalsIgnoreCase(filtroTodosHBS))
			empresasFiltro.add(fstFiltroEmpresa);
		if (filtroValor.isEmpty())
			filtroValor = valorSeleccionadoHBS;
		
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
		model.put(filtroValorHBS, filtroValor);
		return model;
	}
	
}
