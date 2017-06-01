package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import ExceptionsPackage.RutaDeArchivoInvalidaExeption;
import utils.EscrituraDeArchivos;
import utils.FileProvider;

//No cambiar los archivos para test ya que los tests van a fallar

public class EscrituraDeArchivosTest {
	String rutaDelArchivoAEscribir="./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacion.txt";
	String rutaDeArchivoMala = "./Ruta Invalida/Necronomicon.txt";
	String jsonMagico = "Un Json Magico";
	FileProvider lector = new FileProvider();//Debería crear una clase o método auxiliar para que sean independientes?
	
	//Si el archivo no está lo crea así que no se prueba por archivo inexistente
	
	@After
	public void borrarArchivo(){
		EscrituraDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}
	
	@Test
	public void funcionSobreescribirCreaArchivoSiNoExiste(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonMagico));
	}
	
	@Test
	public void sobreescribirUnArchivo(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, "cualquier cosa a sobreescribir");
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonMagico));
	}
	
	@Test
	public void agregarCosasAlFinalDeUnArchivo(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);
		EscrituraDeArchivos.agregarAlArchivo(rutaDelArchivoAEscribir, "210");
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonMagico+"210"));
	}
	
	@Test
	public void funcionAgregarCreaElArchivoSiNoExiste(){
		EscrituraDeArchivos.agregarAlArchivo(rutaDelArchivoAEscribir, jsonMagico);
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonMagico));
	}
	
	@Test(expected = RutaDeArchivoInvalidaExeption.class)
	public void rutaDeArchivoMalaTiraExeptionEnSobreescribir(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDeArchivoMala, jsonMagico);
	}
}