package useCases;

import java.util.ArrayList;
import java.util.List;

import model.Empresa;
import model.repositories.RepositorioEmpresas;

public class EmpresasUseCases {
	
	private static String filtroTodas = "Todas";
	
	public static List<Empresa> obtenerEmpresasPor(Empresa empresa) {
		
		List<Empresa> empresas = new ArrayList<Empresa>();
		if (empresa.getNombre().equalsIgnoreCase(filtroTodas))
			return RepositorioEmpresas.getInstance().getAll();
		else {
			empresa = RepositorioEmpresas.getInstance().getEmpresaPorNombre(empresa.getNombre());
			
			empresas.add(empresa);
		}
		return empresas;
		}

}
