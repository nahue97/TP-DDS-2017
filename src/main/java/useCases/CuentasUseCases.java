package useCases;

import java.math.BigDecimal;
import java.util.List;

import model.Cuenta;
import model.Empresa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;

public class CuentasUseCases {

	private static String filtroTodas = "Todas";
	private static String filtroTodos = "Todos";
	
	public static List<Cuenta> obtenerCuentasPor(String tipo, String periodo, String valor, Empresa empresa) {
		
		BigDecimal value = new BigDecimal("0");
		
		if (tipo.equals(filtroTodos) && periodo.equals(filtroTodos) && empresa.getNombre().equals(filtroTodas) && valor.isEmpty())
			return RepositorioCuentas.getInstance().getAll();
		else{
			if (tipo.equals(filtroTodos))
				tipo = null;
			if (empresa.getNombre().equals(filtroTodas))
				empresa = null;
			else
				empresa = RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa.getNombre());
			if (periodo.equals(filtroTodos))
				periodo = null;
			if (valor.equals(""))
				value = null;
			else
				value = new BigDecimal(valor);
			
			return RepositorioCuentas.getInstance().filtrarCuentas(tipo, empresa, periodo, value);
		}
	}

}
