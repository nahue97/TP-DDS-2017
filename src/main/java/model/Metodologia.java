package model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "metodologias")
public class Metodologia extends PersistentEntity {
	
	@Column(nullable=false)
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "metodologia_id")
	private List<Regla> reglas;

	public Metodologia(){
	}
	
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
