package model.componentes;

import java.math.BigDecimal;

public class CuentaComponente implements Componente{
	
	private BigDecimal valor;
	private String tipoDeCuenta;

	public CuentaComponente(String tipoDeCuenta) {
		super();
		this.setTipoDeCuenta(tipoDeCuenta);
	}

	@Override
	public BigDecimal getValor() {		
		return valor;
	}
	
	public void setValor(BigDecimal valor){
		this.valor = valor;
	}

	public String getTipoDeCuenta() {
		return tipoDeCuenta;
	}

	public void setTipoDeCuenta(String tipoDeCuenta) {
		this.tipoDeCuenta = tipoDeCuenta;
	}

}
