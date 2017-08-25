package UtilsTest;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import utils.HashMapUtils;


public class HashMapUtilsTest {
	
	LinkedHashMap<String, Integer> hashmapDePruebas;
	
	@Before
	public void setUp() {
		hashmapDePruebas = new LinkedHashMap<String, Integer>();
		hashmapDePruebas.put("key1", 1);
		hashmapDePruebas.put("key2", 2);
		hashmapDePruebas.put("key3", 3);
	}

	@Test
	public void insertarRegistro() {
		HashMapUtils.insertarRegistro(hashmapDePruebas, "key4", new Integer(5));
		assertTrue(hashmapDePruebas.size() == 4);
	}
	
	@Test
	public void eliminarRegistro() {
		HashMapUtils.eliminarRegistro(hashmapDePruebas, "key2");
		assertTrue(hashmapDePruebas.size() == 2);
	}
	
	@Test
	public void obtenerValorPorClave() {
		assertTrue(HashMapUtils.obtenerValorPorClave(hashmapDePruebas, "key1").equals(new Integer(1)));
	}
	
	@Test(expected = UserException.class)
	public void obtenerValorPorClaveInexistente() {
		@SuppressWarnings("unused")
		Integer valor = HashMapUtils.obtenerValorPorClave(hashmapDePruebas, "SARLANGA");
	}
	
	
}
