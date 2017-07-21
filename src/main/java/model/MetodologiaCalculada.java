package model;

import java.util.HashMap;
import java.util.List;

import utils.CalculadorDeMetodologias;

public class MetodologiaCalculada extends Metodologia {

	HashMap<String, String> empresasSegunConveniencia;

	public MetodologiaCalculada(List<Regla> reglas) {
		super(reglas);
		this.empresasSegunConveniencia = CalculadorDeMetodologias.getInstance().calcularMetodologia(this);
	}

}
