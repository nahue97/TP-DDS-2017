package model.componentes;

import java.math.BigDecimal;
import java.util.List;

import ExceptionsPackage.CuentaNotFoundException;
import model.Cuenta;
import model.repositories.RepositorioCuentas;

public class CuentaComponente implements Componente{
	
	private String tipoDeCuenta;

	@Override
	public BigDecimal getValor(String periodo, String empresa) {		
		List<Cuenta> cuentas = RepositorioCuentas.getInstance()
								.filtrarCuentas(this.tipoDeCuenta, empresa, periodo, null);
		if (cuentas.size() == 0){
			throw new CuentaNotFoundException("Cuenta no encontrada: Tipo - " + this.tipoDeCuenta + ", Empresa - " + empresa + ", Periodo - " + periodo + ".");
		}
		return cuentas.get(0).getValor();
		
	}

	public CuentaComponente(String tipoCuenta) {
		super();
		this.tipoDeCuenta = tipoCuenta;
	}
}
