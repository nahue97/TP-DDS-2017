package model;

import static javax.persistence.InheritanceType.*;
import javax.persistence.*;

@Entity
@Table(name = "reglas")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@DiscriminatorValue(value="R")
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
