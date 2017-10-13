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
	
	@ManyToOne(targetEntity = Metodologia.class)
	@JoinColumn(name = "Metodologia_Id", nullable = false)
	Metodologia metodologia;

	public String getNombre(){
		return nombre;
	}

	public Indicador getIndicador() {
		return indicador;
	}	
	
	public Metodologia getMetodologia() {
		return metodologia;
	}	
	
	public void setMetodologia(Metodologia metodologia) {
		this.metodologia = metodologia;
	}	

}
