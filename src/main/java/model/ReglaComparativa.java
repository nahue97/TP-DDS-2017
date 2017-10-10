package model;

import javax.persistence.*;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.uqbar.commons.utils.Observable;

@Observable
@Entity
@DiscriminatorValue("C")
public class ReglaComparativa extends Regla {

	@Enumerated(EnumType.STRING)
	Criterio criterio;

	public ReglaComparativa(){
	}
	
	public ReglaComparativa(String nombre,Indicador indicador, Criterio criterio) {
		this.nombre = nombre;
		this.indicador = indicador;
		this.criterio = criterio;
		//Cuanto mayor el valor, mas conviene: DESC
		//Cuanto menor el valor, mas conviene: ASC
	}

	public Criterio getCriterio() {
		return criterio;
	}

}
