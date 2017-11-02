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
import model.repositories.RepositorioUsuarios;
import utils.AnalizadorDeFormulas;
import utils.AppData;

public class IndicadoresUseCases {
	
	final static String filtroTodas = "Todas";
	final static String filtroTodos = "Todos";
	
	public static List<IndicadorCalculado> obtenerIndicadoresPor(String indicador, String empresa, String periodo, Long id){
		List<IndicadorCalculado> indicadores = new ArrayList<IndicadorCalculado>();
		Set<String> periodosACalcular = new LinkedHashSet<String>();
		List<Empresa> empresasACalcular = new ArrayList<Empresa>();
		List<Indicador> indicadoresACalcular = new ArrayList<Indicador>();
		
		Usuario usuarioConId = new Usuario(null,null);
		usuarioConId.setId(id);
		Indicador _indicador = new Indicador(indicador, null, usuarioConId);
		
		if (indicador.equalsIgnoreCase(filtroTodos))
			indicadoresACalcular.addAll(RepositorioIndicadores.getInstance().getAllFromUserId(id));
		else 
			indicadoresACalcular.addAll(RepositorioIndicadores.getInstance().searchByExample(_indicador));
		if (empresa.equalsIgnoreCase(filtroTodas))
			empresasACalcular.addAll(RepositorioCuentas.getInstance().getEmpresasConCuenta());
		else 
			empresasACalcular.add(RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa));
		if (periodo.equalsIgnoreCase(filtroTodos))
			RepositorioCuentas.getInstance().getAll().forEach(cuenta -> periodosACalcular.add(cuenta.getPeriodo()));
		else
			periodosACalcular.add(periodo);
	
		indicadoresACalcular.forEach(ind -> empresasACalcular.forEach(emp -> periodosACalcular.forEach(per -> indicadores.add(new IndicadorCalculado(ind, emp, per)))));
		
		return indicadores;

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
