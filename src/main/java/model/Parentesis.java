package model;

import java.util.ArrayList;
import java.util.List;

//Deberían verse como (2+PN+20*30-Caja)

public class Parentesis implements Operacion{
	List<Termino> terminos = new ArrayList<>();
	
	public Double getTotal(String empresa, String periodo){
		Double suma = 0D;
		
		for(Termino termino:terminos)
			suma += termino.getTotal(empresa, periodo);
		
		return suma;
	}
}
