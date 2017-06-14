package model;

import java.math.BigDecimal;

import model.repositories.RepositorioCuentas;

public class CuentaComponente implements Componente{
	
	private String tipoDeCuenta;
	private String empresa;
	private String periodo;

	@Override
	public BigDecimal valor() {
		//TODO: Traer del RepositorioCuentas el valor asociado al tipoDeCuenta con el empresa y periodo indicados.
		return null;
	}

	public CuentaComponente(String empresa, String periodo) {
		super();
		this.empresa = empresa;
		this.periodo = periodo;
	}
}
