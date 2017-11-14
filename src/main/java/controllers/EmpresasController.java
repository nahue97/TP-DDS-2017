package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.TransactionalException;

import model.Empresa;
import model.repositories.RepositorioEmpresas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import useCases.EmpresasUseCases;

public class EmpresasController {
	
	private static String nombreEmpresaHBS = "empresa";
	private static String empresaExistenteHBS = "empresaExistente";
	private static String cargaExitosaHBS = "cargaExitosa";
	private static String cargaErroneaHBS = "cargaErronea";
	
	
	final static String empresasHBS = "empresasMostrar";
	
	final static String filtroEmpresa = "empresas";
	final static String filtroTodasHBS = "Todas";
	
	final static String consultaEmpresasHBS = "empresas/consulta.hbs";
	final static String cargaEmpresasHBS = "empresas/carga.hbs";
	
	public static ModelAndView listar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model,filtroTodasHBS);
		return new ModelAndView(model, consultaEmpresasHBS);
	}

	public static ModelAndView mostrar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		List<Empresa> empresas = new ArrayList<Empresa>();

		String nombreEmpresa = req.queryParams(nombreEmpresaHBS);
		Empresa empresa = new Empresa(null, nombreEmpresa);
		
		empresas = EmpresasUseCases.obtenerEmpresasPor(empresa);
		
		model = getDatosFiltros(model, nombreEmpresa);
		model.put(empresasHBS,empresas);
		
		return new ModelAndView(model, consultaEmpresasHBS);
	}
	
	public static ModelAndView nuevo(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		return new ModelAndView(null, cargaEmpresasHBS);
	}
	
	public static ModelAndView crear(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Map<String, Object> model = new HashMap<>();
		
		Empresa emp = new Empresa(null,req.queryParams(nombreEmpresaHBS));
		
		if (RepositorioEmpresas.getInstance().existeEmpresa(emp)){
			model.put(empresaExistenteHBS, true);
			return new ModelAndView(model, cargaEmpresasHBS);
		}			
		
		try{
			RepositorioEmpresas.getInstance().add(emp);
			model.put(cargaExitosaHBS, true);
		} catch (TransactionalException e){
			model.put(cargaErroneaHBS, true);			
		}
		return new ModelAndView(model, "empresas/carga.hbs");
	}
	
	private static Map<String, Object> getDatosFiltros(Map<String, Object> model, String filtroNombreEmpresa) {

		List<String> empresasFiltro = new ArrayList<String>();

		if (!filtroNombreEmpresa.equalsIgnoreCase(filtroTodasHBS))
			empresasFiltro.add(filtroNombreEmpresa);
		
		empresasFiltro.add(filtroTodasHBS);
		
		RepositorioEmpresas.getInstance().getAll().forEach(empresa -> empresasFiltro.add(empresa.getNombre()));
		
		model.put(filtroEmpresa, empresasFiltro);
		
		return model;
	}
	
}
