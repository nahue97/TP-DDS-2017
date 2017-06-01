package tp1;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.Cuenta;
import utils.JsonCreator;;

public class JsonCreatorTest {
	private static Cuenta cuenta0 = new Cuenta(0, "Tipo0", "Empresa", "Periodo", 0000L);
	private static Cuenta cuenta1 = new Cuenta(1, "Tipo1", "Empresa", "Periodo", 1000L);
	private static Cuenta cuenta2 = new Cuenta(2, "Tipo2", "Empresa2", "Periodo2", 2000L);
	private static Cuenta cuenta3 = new Cuenta(3, "Tipo3", "Empresa3", "Periodo2", 3000L);
	
	private static List <Cuenta> listaDeObjetos =  Arrays.asList(cuenta0, cuenta1, cuenta2, cuenta3);
	
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
