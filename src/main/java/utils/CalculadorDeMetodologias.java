package utils;

import java.util.HashMap;

import model.Metodologia;

public class CalculadorDeMetodologias {

	private static CalculadorDeMetodologias instance;

	public static synchronized CalculadorDeMetodologias getInstance() {
		if (instance == null)
			instance = new CalculadorDeMetodologias();
		return instance;
	}

	public HashMap<String, Integer> calcularMetodologia(Metodologia metodologia) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
