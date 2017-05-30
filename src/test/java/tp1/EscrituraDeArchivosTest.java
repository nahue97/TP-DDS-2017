package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utils.EscrituraDeArchivos;
import utils.FileProvider;

//No cambiar los archivos para test ya que los tests van a fallar

public class EscrituraDeArchivosTest {
	String rutaDelArchivoAEscribir="./Archivos de prueba/ArchivoDeCuentasParaTestsDeGrabacion.txt";
	String rutaDeArchivoMala = "./Ruta Invalida/Necronomicon.txt";
	String jsonMagico = "Un Json Magico\ncon varias lineas";
	FileProvider lector = new FileProvider();//Debería crear una clase o método auxiliar para que sean independientes?
	
	//Si el archivo no está lo crea así que no se prueba por archivo inexistente
	
	@Test
	public void crearArchivoBueno(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonMagico));
		
		EscrituraDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}
	
	@Test
	public void sobreescribirUnArchivo(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, "cualquier cosa a sobreescribir");
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonMagico));
		
		EscrituraDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}
	
	@Test
	public void agregarCosasAlArchivo(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);
		EscrituraDeArchivos.agregarAlArchivo(rutaDelArchivoAEscribir, "210");
		
		String contenidoDelArchivo = lector.leerArchivo(rutaDelArchivoAEscribir);
		
		assertTrue(contenidoDelArchivo.equals(jsonMagico+"210"));
		
		EscrituraDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}
	
	@Test(expected = Error.class)
	public void rutaDeArchivoMala(){
		EscrituraDeArchivos.sobreescribirArchivo(rutaDeArchivoMala, jsonMagico);
	}
}