package tp1;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import model.Cuenta;
import utils.EscrituraDeArchivos;
import utils.FileProvider;
import utils.JsonReader;
import utils.PersistenciaDeDatos;

public class PersistenicaDeDatosTest {
	String rutaDelArchivoAEscribir="./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacion.txt";
	String rutaDeArchivoMala = "./Ruta Invalida/Necronomicon.txt";
	
	private static Cuenta cuenta0 = new Cuenta(0, "Tipo0", "Empresa", "Periodo", 0000L);
	private static Cuenta cuenta1 = new Cuenta(1, "Tipo1", "Empresa", "Periodo", 1000L);
	private static Cuenta cuenta2 = new Cuenta(2, "Tipo2", "Empresa2", "Periodo2", 2000L);
	private static Cuenta cuenta3 = new Cuenta(3, "Tipo3", "Empresa3", "Periodo2", 3000L);
	
	FileProvider lector = new FileProvider();
	
	@After
	public void borrarArchivo(){
		EscrituraDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}
	
	@Test
	public void crearElArchivo(){
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta0, rutaDelArchivoAEscribir);
		
		String jsonEsperado = "[{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0}]";
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonEsperado));
	}
	
	@Test
	public void agregarAlArchivo(){
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta0, rutaDelArchivoAEscribir);
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta1, rutaDelArchivoAEscribir);
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta2, rutaDelArchivoAEscribir);
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta3, rutaDelArchivoAEscribir);
		
		String jsonEsperado= "[{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0},"
							+ "{\"id\":1,\"tipo\":\"Tipo1\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":1000},"
							+ "{\"id\":2,\"tipo\":\"Tipo2\",\"empresa\":\"Empresa2\",\"periodo\":\"Periodo2\",\"valor\":2000},"
							+ "{\"id\":3,\"tipo\":\"Tipo3\",\"empresa\":\"Empresa3\",\"periodo\":\"Periodo2\",\"valor\":3000}]";
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonEsperado));
	}
	
	@Test
	public void testIntgralConJsonReader(){
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta0, rutaDelArchivoAEscribir);
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta1, rutaDelArchivoAEscribir);
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta2, rutaDelArchivoAEscribir);
		PersistenciaDeDatos.agregarObjetoAlArchvioJson(cuenta3, rutaDelArchivoAEscribir);
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		List<Cuenta> objetosDelArchivo = JsonReader.obtenerCuentas(contenidoDelArchivo);
		
		assertTrue(objetosDelArchivo.size() == 4);
	}
}
