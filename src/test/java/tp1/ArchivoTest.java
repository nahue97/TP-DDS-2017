package tp1;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import model.Cuenta;
import utils.ManejoDeArchivos;
import utils.JsonReader;
import utils.Archivo;

public class ArchivoTest {
	String rutaDelArchivoAEscribir="./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacion.txt";
	String rutaDeArchivoMala = "./Ruta Invalida/Necronomicon.txt";
	
	private static Cuenta cuenta0 = new Cuenta(0, "Tipo0", "Empresa", "Periodo", 0000L);
	private static Cuenta cuenta1 = new Cuenta(1, "Tipo1", "Empresa", "Periodo", 1000L);
	private static Cuenta cuenta2 = new Cuenta(2, "Tipo2", "Empresa2", "Periodo2", 2000L);
	private static Cuenta cuenta3 = new Cuenta(3, "Tipo3", "Empresa3", "Periodo2", 3000L);
	
	@After
	public void borrarArchivo(){
		ManejoDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}
	
	@Test
	public void crearElArchivo(){
		Archivo.archivarObjeto(cuenta0, rutaDelArchivoAEscribir);
		
		String jsonEsperado = "[{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0}]";
		
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonEsperado));
	}
	
	@Test
	public void agregarAlArchivo(){
		Archivo.archivarObjeto(cuenta0, rutaDelArchivoAEscribir);
		Archivo.archivarObjeto(cuenta1, rutaDelArchivoAEscribir);
		Archivo.archivarObjeto(cuenta2, rutaDelArchivoAEscribir);
		Archivo.archivarObjeto(cuenta3, rutaDelArchivoAEscribir);
		
		String jsonEsperado= "[{\"id\":0,\"tipo\":\"Tipo0\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":0},"
							+ "{\"id\":1,\"tipo\":\"Tipo1\",\"empresa\":\"Empresa\",\"periodo\":\"Periodo\",\"valor\":1000},"
							+ "{\"id\":2,\"tipo\":\"Tipo2\",\"empresa\":\"Empresa2\",\"periodo\":\"Periodo2\",\"valor\":2000},"
							+ "{\"id\":3,\"tipo\":\"Tipo3\",\"empresa\":\"Empresa3\",\"periodo\":\"Periodo2\",\"valor\":3000}]";
		
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonEsperado));
	}
	
	@Test
	public void testIntgralConJsonReader(){
		Archivo.archivarObjeto(cuenta0, rutaDelArchivoAEscribir);
		Archivo.archivarObjeto(cuenta1, rutaDelArchivoAEscribir);
		Archivo.archivarObjeto(cuenta2, rutaDelArchivoAEscribir);
		Archivo.archivarObjeto(cuenta3, rutaDelArchivoAEscribir);
		
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);
		
		List<Cuenta> objetosDelArchivo = JsonReader.obtenerCuentas(contenidoDelArchivo);
		
		assertTrue(objetosDelArchivo.size() == 4);
	}
}
