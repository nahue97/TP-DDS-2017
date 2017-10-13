package model;

import javax.persistence.*;

@Entity
@Table(name = "reglas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Regla extends PersistentEntity {
	
	@Column(nullable=false)
	String nombre;
	
	@ManyToOne
	Indicador indicador;

	public String getNombre(){
		return nombre;
	}

	public Indicador getIndicador() {
		return indicador;
	}	

}
