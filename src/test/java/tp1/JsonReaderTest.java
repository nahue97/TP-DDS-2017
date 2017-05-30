package tp1;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import org.uqbar.commons.model.UserException;

import model.Cuenta;
import utils.JsonReader;

public class JsonReaderTest {
	@Test
	public void elJsonSeParseoBien(){
		String jsonStringBueno="[{\"id\":1,\"tipo\":\"EBITDA\",\"empresa\":\"Facebook\",\"periodo\":\"2016\",\"valor\":10000},"
				+ "{\"id\":2,\"tipo\":\"EBITDA\",\"empresa\":\"Facebook\",\"periodo\":\"2017\",\"valor\":99999999},\n"
				+ "{\"id\":3,\"tipo\":\"EBITDA\",\"empresa\":\"Twitter\",\"periodo\":\"2017\",\"valor\":20}]";
		
		List<Cuenta> cuentas = JsonReader.obtenerCuentas(jsonStringBueno);
		assertTrue(cuentas.size() == 3);
	}
	
	@Test(expected = UserException.class)
	public void elJsonMalHechoTiraError() throws Exception {
		String jsonStringMalo="[{\"id\":1,\"tipo\":\"EBITDA\",\"empresa\":\"Facebook\",\"periodo\":\"2017\",:3,\""
				+ "tipo\":\"EBITDA\",\"empresa\":\"Twitter,\"valor\":20}]";
		
		JsonReader.obtenerCuentas(jsonStringMalo);
	}
}