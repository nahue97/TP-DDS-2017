package model;

import java.util.ArrayList;
import java.util.List;

//Debería ser algo como PN*2*Caja

public class Termino implements Operacion{
	List <Factor> factores = new ArrayList<>();
	
	public Double getTotal(String empresa, String periodo){
		Double total = 1D;
		
		for(Factor factor:factores)
			total *= factor.getTotal(empresa, periodo);
		
		return total;
	}
}