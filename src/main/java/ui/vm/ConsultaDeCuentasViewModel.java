package ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import model.Cuenta;

@Observable
public class ConsultaDeCuentasViewModel {
	
	private String empresa;
	private String tipoCuenta;
	private String periodo;
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	public ConsultaDeCuentasViewModel(List<Cuenta> cuentas) {
		super();
		this.cuentas = cuentas;
	}

	public void consultarCuenta() {
		
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
	
}
