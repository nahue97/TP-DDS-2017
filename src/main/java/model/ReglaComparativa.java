package model;

public class ReglaComparativa extends Regla {

	Criterio criterio;

	public ReglaComparativa(Indicador indicador, Criterio criterio) {
		this.indicador = indicador;
		this.criterio = criterio;
		//Cuanto mayor el valor, mas conviene: DESC
		//Cuanto menor el valor, mas conviene: ASC
	}

	public Criterio getCriterio() {
		return criterio;
	}

}
