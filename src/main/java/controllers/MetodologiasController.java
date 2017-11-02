package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioMetodologias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import useCases.LoginUseCases;
import useCases.MetodologiasUseCases;


public class MetodologiasController {
	
	final static String metodologiaSeleccionadaHBS = "metodologia";
	final static String periodoDesdeSeleccionadoHBS = "periodoDesde";
	final static String periodoHastaSeleccionadoHBS = "periodoHasta";
	
	final static String filtroMetodologiasHBS = "metodologias";
	final static String filtroPeriodosDesdeHBS = "periodosDesde";
	final static String filtroPeriodosHastaHBS = "periodosHasta";
	
	final static String metodologiasCalculadasHBS = "metodologiasCalculadas";

	final static String errorPeriodosHBS = "errorPeriodos";
	final static String errorCalculoHBS = "errorCalculo";

	
	final static String consultaMetodolgiasHBS = "metodologias/consulta.hbs";
	
	public static ModelAndView listar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Long id = LoginUseCases.getSession(req);
		Map<String, Object> model = new HashMap<>();
		model = getDatosFiltros(model, null, null, null, id);
		return new ModelAndView(model, consultaMetodolgiasHBS);
	}
	
	public static ModelAndView mostrar(Request req, Response res){
		LoginController.verificarSesionIniciada(req, res);
		Long id = LoginUseCases.getSession(req);
		Map<String, Object> model = new HashMap<>();
		
		String metodologia = req.queryParams(metodologiaSeleccionadaHBS);
		String periodoDesde = req.queryParams(periodoDesdeSeleccionadoHBS);
		String periodoHasta = req.queryParams(periodoHastaSeleccionadoHBS);
		
		if (Integer.parseInt(periodoDesde) > Integer.parseInt(periodoHasta)){
			model.put(errorPeriodosHBS, true);
		}else{
			try{
				model.put(metodologiasCalculadasHBS,MetodologiasUseCases.obtenerEmpresasEvaluadasPorMetodologia(metodologia, periodoDesde, periodoHasta, id));
			}catch(RuntimeException e){
				model.put(errorCalculoHBS, true);
			}	
		}
		model = getDatosFiltros(model, metodologia, periodoDesde, periodoHasta, id);
		return new ModelAndView(model, consultaMetodolgiasHBS);
	}
	
	private static Map<String, Object> getDatosFiltros(Map<String, Object> model, String metodologia, String desde, String hasta, Long id) {
		List<String> _periodos = new ArrayList<String>();
		Set<String> periodos = new LinkedHashSet<String>();
		Set<String> periodosDesde = new LinkedHashSet<String>();
		Set<String> periodosHasta = new LinkedHashSet<String>();
		Set<String> metodologias = new LinkedHashSet<String>();
		
		RepositorioCuentas.getInstance().getAll().forEach(cuenta -> _periodos.add(cuenta.getPeriodo()));
		_periodos.sort(String::compareToIgnoreCase);
		periodos.addAll(_periodos);
		_periodos.addAll(periodosDesde);
		
		if (metodologia != null)
			metodologias.add(metodologia);
		
		if (desde != null || hasta != null){
		if (!desde.equalsIgnoreCase(_periodos.get(0)))
			periodosDesde.add(desde);
		if (!hasta.equalsIgnoreCase(_periodos.get(periodos.size())))
			periodosHasta.add(hasta);
		}
		
		periodosDesde.addAll(_periodos);
		periodosHasta.addAll(_periodos);
		model.put(filtroPeriodosDesdeHBS, periodosDesde);
		model.put(filtroPeriodosHastaHBS, periodosHasta);
		RepositorioMetodologias.getInstance().getAllFromUserId(id).forEach(metod -> metodologias.add(metod.getNombre()));
		model.put(filtroMetodologiasHBS, metodologias );
		
		return model;
	}

}
