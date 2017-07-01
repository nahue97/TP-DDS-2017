package providers;

import java.util.List;

import dtos.PathFile;
import model.Cuenta;

public interface IProviderCuenta{
	
	List<Cuenta> getInformationCuentas(PathFile datosDeCarga);
	
}
