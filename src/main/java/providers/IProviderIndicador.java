package providers;

import java.util.List;

import dtos.PathFile;
import model.Indicador;

public interface IProviderIndicador {
	List<Indicador> getInformationIndicador(PathFile datosDeCarga);
}
