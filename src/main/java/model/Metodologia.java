package model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "metodologia")
public class Metodologia extends PersistentEntity {
	
	@Column(nullable=false)
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
