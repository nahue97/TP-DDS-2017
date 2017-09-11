package tp1;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Cuenta;
import utils.ManejoDeArchivos;
import utils.JsonReader;
import utils.Archivo;

public class ArchivoTest {
	String rutaDelArchivoAEscribir= "./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacion.txt";
	String rutaDeArchivoMala = "./Ruta Invalida/Necronomicon.txt";
	
	Cuenta cuenta0 = new Cuenta("Tipo0", "Empresa", "Periodo", new BigDecimal(0));
	Cuenta cuenta1 = new Cuenta("Tipo1", "Empresa", "Periodo", new BigDecimal(1000));
	Cuenta cuenta2 = new Cuenta("Tipo2", "Empresa2", "Periodo2", new BigDecimal(2000));
	Cuenta cuenta3 = new Cuenta("Tipo3", "Empresa3", "Periodo2", new BigDecimal(3000));
	
	List<Cuenta> cuentas = new ArrayList<>();
	
	@Before
	public void setUp(){
		cuentas = Arrays.asList(cuenta0, cuenta1, cuenta2, cuenta3);
	}
	
	@After
	public void borrarArchivo(){
		//ManejoDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}
	
	@Test
	public void crearElArchivo(){
		Archivo.archivarObjetos(cuentas, rutaDelArchivoAEscribir);
		
		String jsonEsperado= "[{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0},"
				+ "{\"id\":1,\"tipo\":\"Tipo1\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":1000},"
				+ "{\"id\":2,\"tipo\":\"Tipo2\",\"empresa\":\"Empresa2\",\"periodo\":\"Periodo2\",\"valor\":2000},"
				+ "{\"id\":3,\"tipo\":\"Tipo3\",\"empresa\":\"Empresa3\",\"periodo\":\"Periodo2\",\"valor\":3000}]";
		
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonEsperado));
	}
	
	@Test
	public void modificarElArchivo(){
		cuentas = Arrays.asList(cuenta0, cuenta2, cuenta3);
		Archivo.archivarObjetos(cuentas, rutaDelArchivoAEscribir);
		
		String jsonEsperado= "[{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0},"
							+ "{\"id\":2,\"tipo\":\"Tipo2\",\"empresa\":\"Empresa2\",\"periodo\":\"Periodo2\",\"valor\":2000},"
							+ "{\"id\":3,\"tipo\":\"Tipo3\",\"empresa\":\"Empresa3\",\"periodo\":\"Periodo2\",\"valor\":3000}]";
		
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonEsperado));
	}
	
	@Test
	public void testIntgralConJsonReader(){
		Archivo.archivarObjetos(cuentas, rutaDelArchivoAEscribir);
		
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);
		
		List<Cuenta> objetosDelArchivo = JsonReader.obtenerCuentas(contenidoDelArchivo);
		
		assertTrue(objetosDelArchivo.size() == 4 &&
				   objetosDelArchivo.get(0).getTipo().equals("Tipo0"));
	}
}
