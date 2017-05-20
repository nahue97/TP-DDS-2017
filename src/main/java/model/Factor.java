package model;

import java.util.List;

import org.uqbar.commons.model.UserException;

import model.repositories.RepositorioDeCuentas;

//Debería ser un solo factor como 2, PN, Caja

public class Factor implements Operacion{
	private String tipoDeCuenta;
	private Double constante;
	//private Identificador identificador;
	
	private Factor(String _tipoDeCuenta, Double _constante){
		tipoDeCuenta = _tipoDeCuenta;
		constante = _constante;
	}
	//Pensé en hacer herencia para que quede mejor esto
	public Factor getFactorDeTipo(String _tipoDeCuenta){
		return new Factor(_tipoDeCuenta, 1D);
	}
	
	public Factor getFactorConstante(Double _constante){
		return new Factor("", _constante);
	}
	
	public Double getTotal(String empresa, String periodo){
		return obtenerValor(empresa, periodo) * constante;
	}
	
	private Double obtenerValor(String empresa, String periodo){
		if(tipoDeCuenta.isEmpty())
			return 1D;
		
		final List<Cuenta> cuentasAEvaluar;
		final RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
		
		cuentasAEvaluar = repositorio.filtrarCuentas(tipoDeCuenta, empresa, periodo, "");
		
		if(cuentasAEvaluar.size() == 0)
			throw new UserException("No hay ningún tipo de cuenta para esa empresa en ese periodo");
		
		if(cuentasAEvaluar.size() != 1)
			throw new UserException("Hay más de un tipo de cuenta para esa empresa en ese periodo");
		
		return cuentasAEvaluar.get(0).getValor().doubleValue();
	}
}