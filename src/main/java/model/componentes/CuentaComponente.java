package model.componentes;

import java.math.BigDecimal;

import utils.CalculadorDeIndicadores;

public class CuentaComponente implements Componente{
	
	private String tipoDeCuenta;

	public CuentaComponente(String tipoCuenta) {
		super();
		this.tipoDeCuenta = tipoCuenta;
	}

	@Override
	public BigDecimal getValor(String periodo, String empresa) {		
		return CalculadorDeIndicadores.getInstance().calcularCuenta(tipoDeCuenta, periodo, empresa);
	}

}
