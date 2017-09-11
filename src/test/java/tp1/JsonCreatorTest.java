package tp1;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;

import org.junit.Test;

import model.Cuenta;
import utils.JsonCreator;;

public class JsonCreatorTest {
	Cuenta cuenta0 = new Cuenta("Tipo0", "Empresa", "Periodo", new BigDecimal(0));
	Cuenta cuenta1 = new Cuenta("Tipo1", "Empresa", "Periodo", new BigDecimal(1000));
	Cuenta cuenta2 = new Cuenta("Tipo2", "Empresa2", "Periodo2", new BigDecimal(2000));
	Cuenta cuenta3 = new Cuenta("Tipo3", "Empresa3", "Periodo2", new BigDecimal(3000));
	
	List <Cuenta> listaDeObjetos =  Arrays.asList(cuenta0, cuenta1, cuenta2, cuenta3);
	
	String jsonDeListaEsperado= "[{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0},"
							   + "{\"id\":1,\"tipo\":\"Tipo1\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":1000},"
							   + "{\"id\":2,\"tipo\":\"Tipo2\",\"empresa\":\"Empresa2\",\"periodo\":\"Periodo2\",\"valor\":2000},"
							   + "{\"id\":3,\"tipo\":\"Tipo3\",\"empresa\":\"Empresa3\",\"periodo\":\"Periodo2\",\"valor\":3000}]";
	
	String jsonDeUnObjetoEsperado = "{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0}";
			
	@Test
	public void obtenerJsonDeUnaListaDeObjetos(){
		String jsonObtenido = JsonCreator.getJson(listaDeObjetos);
		
		//System.out.println(jsonObtenido);
		
		assertTrue(jsonObtenido.equals(jsonDeListaEsperado));
	}
	
	@Test
	public void obtenerJsonDeUnObjeto(){
		String jsonObtenido = JsonCreator.getJson(cuenta0);
		
		//System.out.println(jsonObtenido);
		
		assertTrue(jsonObtenido.equals(jsonDeUnObjetoEsperado));
	}
}
