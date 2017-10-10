package providers;

import java.util.List;

import dtos.PathFile;
import model.Empresa;

public interface IProviderEmpresa{
	
	List<Empresa> getInformationEmpresas(PathFile datosDeCarga);
	
}