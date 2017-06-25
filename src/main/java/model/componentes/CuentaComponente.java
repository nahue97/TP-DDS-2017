package model.componentes;

import java.math.BigDecimal;
import java.util.List;

import ExceptionsPackage.CuentaNotFoundException;
import model.Cuenta;
import model.repositories.RepositorioCuentas;
import utils.CalculadorDeIndicadores;

public class CuentaComponente implements Componente{
	
	private String tipoDeCuenta;
	private BigDecimal valor;

	public CuentaComponente(String tipoCuenta) {
		super();
		this.tipoDeCuenta = tipoCuenta;
	}

	@Override
	public BigDecimal getValor(String periodo, String empresa) {		
		return valor;
	}

	@Override
	public void preparar(String periodo, String empresa) {
		valor = CalculadorDeIndicadores.getInstance().calcularCuenta(tipoDeCuenta, periodo, empresa);
	}
}
