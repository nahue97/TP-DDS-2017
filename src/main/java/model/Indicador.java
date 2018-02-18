package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.*;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import utils.CalculadorDeIndicadores;

@Entity
@Table(name = "indicadores")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Indicador extends PersistentEntity {

	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String formula; // Formula String para obtener las cuentas que usa.
	@Column(nullable=false)
	private String cuentas;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	protected Usuario usuario;

	public Indicador() {
	}

	public Indicador(String nombre, String formula, Usuario usuario) {
		this.nombre = nombre;
		this.formula = formula;
		this.usuario = usuario;
		Indicador indicadorDeMuestra = new Indicador();
		indicadorDeMuestra.setFormula(formula);
		if (formula != null){			
		cuentas = CalculadorDeIndicadores.getInstance().obtenerCuentasSeparadasPorComa(indicadorDeMuestra);
		}
		cuentas = CalculadorDeIndicadores.getInstance().obtenerCuentasSeparadasPorComa(indicadorDeMuestra);
	}

	public Indicador(String nombre, String formula) {
		this.nombre = nombre;
		this.formula = formula;
		Indicador indicadorDeMuestra = new Indicador();
		indicadorDeMuestra.setFormula(formula);
		if (formula != null){			
		cuentas = CalculadorDeIndicadores.getInstance().obtenerCuentasSeparadasPorComa(indicadorDeMuestra);
		}
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

	public String getCuentas() {
		return cuentas;
	}

	public void setCuentas(String cuentas) {
		this.cuentas = cuentas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}