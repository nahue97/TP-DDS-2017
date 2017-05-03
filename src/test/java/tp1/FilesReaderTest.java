package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utils.FilesReader;

//No cambiar ArchivoDeCuentasParaTestsBueno.txt ya que los tests van a fallar

public class FilesReaderTest {
	String rutaDelArchivoBueno="./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	
	@Test
	public void leerArchivoBueno() throws Exception {
		
		String contenidoDelArchivo = FilesReader.leerArchivo(rutaDelArchivoBueno);
		
		assertTrue(contenidoDelArchivo.length() == 231);
	}
	
	@Test(expected = Error.class)
	public void FallarAlLeerArchivoIxensistente() throws Exception {

		FilesReader.leerArchivo(rutaDeArchivoInexistente);
	}
}
