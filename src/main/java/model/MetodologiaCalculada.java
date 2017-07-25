package model;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import utils.CalculadorDeMetodologias;

public class MetodologiaCalculada extends Metodologia {

	List<EmpresaEvaluadaPorMetodologia> empresasEvaluadas;
	

	public MetodologiaCalculada(String nombre, List<Regla> reglas, int anioInicial, int anioFinal) {
		super(nombre, reglas);
		this.empresasEvaluadas = CalculadorDeMetodologias.getInstance().calcularMetodologia(this, anioInicial, anioFinal);
	}


}
