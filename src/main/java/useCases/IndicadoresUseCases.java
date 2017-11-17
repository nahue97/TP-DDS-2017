package useCases;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.Empresa;
import model.Indicador;
import model.IndicadorCalculado;
import model.Usuario;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import model.repositories.RepositorioIndicadores;
import model.repositories.RepositorioIndicadoresCalculados;
import model.repositories.RepositorioUsuarios;
import utils.AnalizadorDeFormulas;
import utils.AppData;

public class IndicadoresUseCases {
	
	final static String filtroTodas = "Todas";
	final static String filtroTodos = "Todos";
	
	public static List<IndicadorCalculado> obtenerIndicadoresPor(String indicador, String empresa, String periodo, Long id){
		List<IndicadorCalculado> indicadoresCalculados = new ArrayList<IndicadorCalculado>();
		IndicadorCalculado indicadorCalculadoEjemplo = new IndicadorCalculado();
		
		if (!indicador.equalsIgnoreCase(filtroTodos))
			indicadorCalculadoEjemplo.setNombre(indicador);
		
		if (!empresa.equalsIgnoreCase(filtroTodas))
			indicadorCalculadoEjemplo.setEmpresa(RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa));
		
		if (!periodo.equalsIgnoreCase(filtroTodos))
			indicadorCalculadoEjemplo.setPeriodo(periodo);
		
		Usuario usuario = new Usuario(null,null);
		usuario.setId(id);
		usuario = RepositorioUsuarios.getInstance().searchByExample(usuario).get(0);
		indicadorCalculadoEjemplo.setUsuario(usuario);
	
		indicadoresCalculados = RepositorioIndicadoresCalculados.getInstance().searchByExample(indicadorCalculadoEjemplo);
		
		return indicadoresCalculados;
	}
	

	public static void crearIndicador(String nombre, String formula, Long id){
		AnalizadorDeFormulas analizador = new AnalizadorDeFormulas();
		String formulaAnalizada = analizador.analizarYSimplificarFormula(formula);
		Usuario usuario = new Usuario(null,null);
		usuario.setId(id);
		usuario = RepositorioUsuarios.getInstance().searchByExample(usuario).get(0);
		AppData.getInstance().guardarIndicador(formulaAnalizada, nombre, usuario);
		
	}
}
