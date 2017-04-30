package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utils.JsonFileReader;

//No cambiar ArchivoDeCuentasParaTestsBueno.txt ya que los tests van a fallar

public class JsonFileReaderTest {
	String rutaDelArchivoBueno="Archivos de prueba\\ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDelArchivoMalo="Archivos de prueba\\ArchivoDeCuentasParaTestsMalo.txt";
	String rutaDeArchivoInexistente = "RutaMisteriosa\\CuidadoRegrese\\Necronomicon.txt";
	
	@Test
	public void leerArchivoBueno() throws Exception {
		
		String contenidoDelArchivo = JsonFileReader.leerArchivo(rutaDelArchivoBueno);
		
		assertTrue(contenidoDelArchivo.length() == 231);
	}
	
	@Test(expected = Error.class)
	public void FallarAlLeerArchivoIxensistente() throws Exception {

		JsonFileReader.leerArchivo(rutaDeArchivoInexistente);
	}
}
