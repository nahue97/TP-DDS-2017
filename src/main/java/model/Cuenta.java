package model;

public class Cuenta {
	
	private int id; //Identificador unico
	private String tipo; //EBITDA, FDS, etc.
	private String empresa; //Facebook, Apple, etc.
	private String periodo; //2016, primer cuatrimestre 2010, etc.
	private Long valor; //Millones de dólares
	
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
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public Long getValor() {
		return valor;
	}
	public void setValor(Long valor) {
		this.valor = valor;
	}
	
	
	

}
