package model;

import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "metodologias")
public class Metodologia extends PersistentEntity {
	
	@Column(nullable=false)
	private String nombre;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="metodologia")
	@Fetch(value=FetchMode.SELECT)
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
