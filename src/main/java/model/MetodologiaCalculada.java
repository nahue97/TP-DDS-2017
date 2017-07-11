package model;

import java.util.HashMap;

import utils.CalculadorDeMetodologias;

public class MetodologiaCalculada extends Metodologia {

	HashMap<String, Integer> empresasSegunConveniencia;

	public MetodologiaCalculada(Metodologia metodologia) {
		this.reglas = metodologia.reglas;
		this.empresasSegunConveniencia = CalculadorDeMetodologias.getInstance().calcularMetodologia(metodologia);
	}

}
