package useCases;

import java.util.List;
import model.EmpresaEvaluadaPorMetodologia;
import model.Metodologia;
import model.Usuario;
import model.repositories.RepositorioMetodologias;
import utils.CalculadorDeMetodologias;

public class MetodologiasUseCases {
	
	public static List<EmpresaEvaluadaPorMetodologia> obtenerEmpresasEvaluadasPorMetodologia (String metodologia, String periodoDesde, String periodoHasta, Long id){ 
		Usuario usuario = new Usuario(null,null);
		usuario.setId(id);
		Metodologia _metodologia = new Metodologia(null, null, usuario);
		Metodologia metodologiaACalcular = RepositorioMetodologias.getInstance().searchByExample(_metodologia).get(0);
		return CalculadorDeMetodologias.getInstance().calcularMetodologia(metodologiaACalcular,
					Integer.parseInt(periodoDesde), Integer.parseInt(periodoHasta));
	}

}
