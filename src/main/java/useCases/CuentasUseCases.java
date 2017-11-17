package useCases;

import java.math.BigDecimal;
import java.util.List;

import dtos.PathFileTxtJson;
import model.Cuenta;
import model.Empresa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import utils.AppData;


public class CuentasUseCases {

	private static String filtroTodas = "Todas";
	private static String filtroTodos = "Todos";
	
	public static List<Cuenta> obtenerCuentasPor(String tipo, String periodo, String valor, Empresa empresa) {
		
		BigDecimal value = new BigDecimal("0");
		
		if (tipo.equals(filtroTodos) && periodo.equalsIgnoreCase(filtroTodos) && empresa.getNombre().equalsIgnoreCase(filtroTodas) && valor.isEmpty())
			return RepositorioCuentas.getInstance().getAll();
		else{
			if (tipo.equalsIgnoreCase(filtroTodos))
				tipo = null;
			if (empresa.getNombre().equalsIgnoreCase(filtroTodas))
				empresa = null;
			else
				empresa = RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa.getNombre());
			if (periodo.equalsIgnoreCase(filtroTodos))
				periodo = null;
			if (valor.equals(""))
				value = null;
			else
				value = new BigDecimal(valor);
			
			return RepositorioCuentas.getInstance().filtrarCuentas(tipo, empresa, periodo, value);
		}
	}

	
	  public static void cargarArchivoDeCuentas(String rutaCompleta) { 
		    PathFileTxtJson datosDeCarga = new PathFileTxtJson(rutaCompleta); 
		    AppData.getInstance().cargarCuentas(datosDeCarga); 
		  } 
}
