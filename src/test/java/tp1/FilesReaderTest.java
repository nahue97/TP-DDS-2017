package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.uqbar.commons.model.UserException;

import utils.FileProvider;

//No cambiar ArchivoDeCuentasParaTestsBueno.txt ya que los tests van a fallar

public class FilesReaderTest {
	String rutaDelArchivoBueno="./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	FileProvider fileProvider = new FileProvider();

	
	@Test
	public void leerArchivoBueno() throws Exception {
		String contenidoDelArchivo = fileProvider.leerArchivo(rutaDelArchivoBueno);
		
		assertTrue(contenidoDelArchivo.length() == 231);
	}
	
	@Test(expected = UserException.class)
	public void FallarAlLeerArchivoIxensistente() throws Exception {

		fileProvider.leerArchivo(rutaDeArchivoInexistente);
	}
}