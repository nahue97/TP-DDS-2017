package useCases;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;

import model.Empresa;
import model.Indicador;
import model.IndicadorCalculado;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import model.repositories.RepositorioIndicadores;
import utils.AnalizadorDeFormulas;
import utils.AppData;
import utils.CalculadorDeIndicadores;

public class IndicadoresUseCases {
	
	static List<Empresa> empresasACalcular = new ArrayList<>();
	static List<IndicadorCalculado> indicadores = new ArrayList<IndicadorCalculado>();
	final static String filtroTodas = "Todas";
	final static String filtroTodos = "Todos";
	
	public static List<IndicadorCalculado> obtenerIndicadoresPor(String indicador, String empresa, String periodo){
		List<String> periodos = new ArrayList<String>();
		CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
		
		if (indicador.equalsIgnoreCase(filtroTodos) && empresa.equalsIgnoreCase(filtroTodas) && periodo.equalsIgnoreCase(filtroTodos)){
				RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodos.add(cuenta.getPeriodo()));
				empresasACalcular = RepositorioCuentas.getInstance().getEmpresasConCuenta();
				periodos.forEach(_periodo -> empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, _periodo)
											.forEach(indCalc -> indicadores.add(indCalc))));
				return indicadores;
		}
		else {
			if (!periodo.equalsIgnoreCase(filtroTodas) && indicador.equalsIgnoreCase(filtroTodos) && empresa.equalsIgnoreCase(filtroTodas)){
				empresasACalcular = RepositorioCuentas.getInstance().getEmpresasConCuenta();
				empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, periodo)
											.forEach(indCalc -> indicadores.add(indCalc)));
				return indicadores;
			}
			if (!periodo.equalsIgnoreCase(filtroTodas) && indicador.equalsIgnoreCase(filtroTodos) && !empresa.equalsIgnoreCase(filtroTodas)){
				Empresa _empresa =  RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa);
				calculadorDeIndicadores.calcularIndicadores(_empresa, periodo).forEach(indCalc -> indicadores.add(indCalc));
				return indicadores;				
			}
			if (!periodo.equalsIgnoreCase(filtroTodas) && !indicador.equalsIgnoreCase(filtroTodos) && !empresa.equalsIgnoreCase(filtroTodas)){
				Empresa _empresa =  RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa);
				Indicador _indicador = RepositorioIndicadores.getInstance().getIndicadorPorNombre(indicador);
				calculadorDeIndicadores.calcularIndicador(_indicador,_empresa, periodo);
				return indicadores;				
			}
			if (!periodo.equalsIgnoreCase(filtroTodas) && !indicador.equalsIgnoreCase(filtroTodos) && empresa.equalsIgnoreCase(filtroTodas)){
				empresasACalcular = RepositorioCuentas.getInstance().getEmpresasConCuenta();
				empresasACalcular.forEach(emp -> calculadorDeIndicadores.calcularIndicadores(emp, periodo)
											.forEach(indCalc -> indicadores.add(indCalc)));
				return indicadores;
			}

	return indicadores;
		}
	}

	public static void crearIndicador(String nombre, String formula){

		AnalizadorDeFormulas analizador = new AnalizadorDeFormulas();
		String formulaAnalizada = analizador.analizarYSimplificarFormula(formula);
		AppData.getInstance().guardarIndicador(formulaAnalizada, nombre);
		
	}
}
