package tp1;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import model.Cuenta;

import utils.LectorDeArchivos;

public class AsesorTest {

	LectorDeArchivos lector;

	@Before
	public void setUp() throws Exception {
		this.lector = new LectorDeArchivos();
		BasicConfigurator.configure();
	}

	@Test
	public void procesarDocumento() throws Exception {
		List<Cuenta> cuentas = this.lector.obtenerCuentas(
				"Archivos de prueba\\ArchivoDeCuentasDePrueba.txt");
		assertTrue(cuentas.size() == 3);
	}
	
//Hacer un test del error parseando el gson
	
}