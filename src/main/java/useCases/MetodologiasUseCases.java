package useCases;

import java.util.List;
import model.EmpresaEvaluadaPorMetodologia;
import model.Metodologia;
import model.repositories.RepositorioMetodologias;
import utils.CalculadorDeMetodologias;

public class MetodologiasUseCases {
	
	public static List<EmpresaEvaluadaPorMetodologia> obtenerEmpresasEvaluadasPorMetodologia (String metodologia, String periodoDesde, String periodoHasta){ 
		
		Metodologia metodologiaACalcular = RepositorioMetodologias.getInstance().getMetodologiaPorNombre(metodologia);
		return CalculadorDeMetodologias.getInstance().calcularMetodologia(metodologiaACalcular,
					Integer.parseInt(periodoDesde), Integer.parseInt(periodoHasta));
	}

}
