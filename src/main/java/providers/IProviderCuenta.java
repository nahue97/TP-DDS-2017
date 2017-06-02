package providers;

import java.util.List;

import dtos.DTO;
import model.Cuenta;

public interface IProviderCuenta{
	
	List<Cuenta> getInformationCuentas(DTO datosDeCarga);
	
}
