package model;

import java.math.BigDecimal;

import javax.persistence.*;

import org.uqbar.commons.utils.Observable;

import com.google.gson.annotations.SerializedName;

@Observable
@Entity
@Table(name = "cuentas")

public class Cuenta extends PersistentEntity{
	
	@SerializedName("tipo")
	private String tipo; // EBITDA, FDS, etc.
	
	@SerializedName("empresa")
	@ManyToOne
	Empresa empresa; // Facebook, Apple, etc.
	
	@SerializedName("periodo")
	private String periodo; // 2016, primer cuatrimestre 2010, etc.
	@SerializedName("valor")
	private BigDecimal valor; // Millones de dolares
	
	public Cuenta(){
	}
	
	public Cuenta(String _tipo, Empresa _empresa, String _periodo, BigDecimal bigDecimal) {
		tipo = _tipo;
		empresa = _empresa;
		periodo = _periodo;
		valor = bigDecimal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setEmpresa(Empresa nuevaEmpresa){
		empresa = nuevaEmpresa;
	}
	
	public Empresa getEmpresa(){
		return empresa;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "id: " + this.getId() + ", " + "tipo: " + tipo + ", " + "empresa: " + empresa.getNombre() + ", " + "periodo: " + periodo + ", "
				+ "valor: " + valor;
	}
}
