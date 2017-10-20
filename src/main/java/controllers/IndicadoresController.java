package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uqbar.commons.model.UserException;

import model.Empresa;
import model.IndicadorCalculado;
import model.repositories.RepositorioEmpresas;
import model.repositories.RepositorioIndicadores;
import scala.collection.parallel.ParIterableLike.Foreach;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.CalculadorDeIndicadores;
import utils.RequestUtil.getString;

public class IndicadoresController {
	static List<IndicadorCalculado> indicadores = new ArrayList<IndicadorCalculado>();

	public static ModelAndView listar(Request req, Response res){

		return new ModelAndView(null, "indicadores/consulta.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res){
		Map<String, Object> model = new HashMap<>();
		
		
		String empresa = getString.get(req,"empresa");
		String periodo = getString.get(req,"periodo");

		if (empresa.equals("Todas") && !periodo.isEmpty()) {
			List<Empresa> empresasACalcular = new ArrayList<>();
			CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
			empresasACalcular = RepositorioEmpresas.getInstance().getAll();
			empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, periodo)
											.forEach(indCalc -> indicadores.add(indCalc))); 
			model.put("indicadores", indicadores);
		}
		if (periodo.isEmpty()) {
			throw new UserException("El campo Periodo esta vacio");
		}
		if (!empresa.isEmpty() && !periodo.isEmpty()){
			CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
			Empresa empresaParaCalcular = RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa);
			indicadores = calculadorDeIndicadores.calcularIndicadores(empresaParaCalcular, periodo);
			model.put("indicadores", indicadores);
		}
		
		return new ModelAndView(model, "indicadores/consulta.hbs");
	}
	
	public static ModelAndView nuevo(Request req, Response res){

		return new ModelAndView(null, "indicadores/carga.hbs");
	}
	
	public static ModelAndView crear(Request req, Response res){

		return new ModelAndView(null, "indicadores/carga.hbs");
	}
	
}
