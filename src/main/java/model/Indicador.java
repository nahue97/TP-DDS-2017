package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "indicadores")
public class Indicador extends PersistentEntity {
	
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private String formula; //Formula String para obtener las cuentas que usa y mostrarlas en la tabla.

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Usuario usuario;
	
	public Indicador(){
	}
	
	public Indicador(String nombre, String formula, Usuario usuario) {
		this.nombre = nombre;
		this.formula = formula;
		this.usuario = usuario;
	}

	public Indicador(String nombre, String formula) {
		this.nombre = nombre;
		this.formula = formula;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}