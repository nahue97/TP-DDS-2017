package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "indicador")
public class Indicador extends PersistentEntity {
	
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private String formula; //Formula String para obtener las cuentas que usa y mostrarlas en la tabla.

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
	
}