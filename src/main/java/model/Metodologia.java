package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Metodologia {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String nombre;
	
	@OneToMany
	@JoinColumn(name = "metodologia_id")
	private List<Regla> reglas;

	public Metodologia(String nombre, List<Regla> reglas) {
		super();
		this.nombre = nombre;
		this.reglas = reglas;
	}

	public String getNombre(){
		return nombre;
	}
	
	public List<Regla> getReglas() {
		return reglas;
	}	
}
