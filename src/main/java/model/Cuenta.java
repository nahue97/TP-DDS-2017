package model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.uqbar.commons.utils.Observable;
import com.google.gson.annotations.SerializedName;
import model.Empresa;

@Observable
@Entity
public class Cuenta {

	@Id
	@SerializedName("id")
	private int id; // Identificador unico interno del sistema
	
	@SerializedName("tipo")
	private String tipo; // EBITDA, FDS, etc.
	
	@SerializedName("empresa")
	@OneToOne
	Empresa empresa; // Facebook, Apple, etc.
	
	@SerializedName("periodo")
	private String periodo; // 2016, primer cuatrimestre 2010, etc.
	
	@SerializedName("valor")
	private BigDecimal valor; // Millones de dolares
	
	public Cuenta(int _id, String _tipo, String _empresa, String _periodo, BigDecimal bigDecimal) {
		id = _id;
		tipo = _tipo;
		periodo = _periodo;
		valor = bigDecimal;
		empresa = new Empresa(_empresa);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEmpresa() {
		return empresa.getNombre();
	}

	public void setEmpresa(String nombre) {
		empresa.setNombre(nombre);
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
		return "id: " + id + ", " + "tipo: " + tipo + ", " + "empresa: " + empresa + ", " + "periodo: " + periodo + ", "
				+ "valor: " + valor;
	}
}
