package useCases;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;

import model.Empresa;
import model.IndicadorCalculado;
import model.repositories.RepositorioCuentas;
import utils.CalculadorDeIndicadores;

public class IndicadoresUseCases {
	
	static List<Empresa> empresasACalcular = new ArrayList<>();
	static List<IndicadorCalculado> indicadores = new ArrayList<IndicadorCalculado>();
	final static String filtroTodas = "Todas";
	final static String filtroTodos = "Todos";
	
	public static List<IndicadorCalculado> obtenerIndicadoresPor(String indicador, String empresa, String periodo){
		List<String> periodos = new ArrayList<String>();
		CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
		
		if (indicador.equals(filtroTodos) && empresa.equals(filtroTodas) && periodo.equals(filtroTodos)){
				RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodos.add(cuenta.getPeriodo()));
				empresasACalcular = RepositorioCuentas.getInstance().getEmpresasConCuenta();
				periodos.forEach(_periodo -> empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, _periodo)
											.forEach(indCalc -> indicadores.add(indCalc))));
				return indicadores;
		}
		else {
			if (!periodo.equals(filtroTodas) && indicador.equals(filtroTodos) && empresa.equals(filtroTodas)){
				empresasACalcular = RepositorioCuentas.getInstance().getEmpresasConCuenta();
				empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, periodo)
											.forEach(indCalc -> indicadores.add(indCalc)));
				return indicadores;
			}
			if (!periodo.equals(filtroTodas) && indicador.equals(filtroTodos) && !empresa.equals(filtroTodas)){
				empresasACalcular = RepositorioCuentas.getInstance().getEmpresasConCuenta();
				empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, periodo)
											.forEach(indCalc -> indicadores.add(indCalc)));
				return indicadores;				
			}

/*			
		if (!empresa.equals(filtroTodas) && !periodo.isEmpty()) {
			CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
			Empresa empresaParaCalcular = RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa);
			return calculadorDeIndicadores.calcularIndicadores(empresaParaCalcular, periodo);
		}*/
	return indicadores;
		}
	}

}
