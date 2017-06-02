package providers;

import java.util.List;

import dtos.DTO;
import model.Indicador;

public interface IProviderIndicador {
	List<Indicador> getInformationIndicador(DTO datosDeCarga);
}
