package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.uqbar.commons.model.UserException;

import utils.FileProvider;

//No cambiar los archivos para test ya que los tests van a fallar

public class FilesProviderTest {
	String rutaDelArchivoBueno="./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	FileProvider fileProvider = new FileProvider();
	
	String contenidoEsperado = "[{\"id\":1,\"tipo\":\"EBITDA\",\"empresa\":\"Facebook\",\"periodo\":\"2016\",\"valor\":10000},"
			+ "{\"id\":2,\"tipo\":\"EBITDA\",\"empresa\":\"Facebook\",\"periodo\":\"2017\",\"valor\":99999999},"
			+ "{\"id\":3,\"tipo\":\"EBITDA\",\"empresa\":\"Twitter\",\"periodo\":\"2017\",\"valor\":20}]";
	
	@Test
	public void leerArchivoBueno(){
		String contenidoDelArchivo = fileProvider.leerArchivo(rutaDelArchivoBueno);
		
		System.out.println(contenidoDelArchivo);
		
		assertTrue(contenidoDelArchivo.equals(contenidoEsperado));
	}
	
	@Test(expected = UserException.class)
	public void FallarAlLeerArchivoIxensistente() throws Exception {

		fileProvider.leerArchivo(rutaDeArchivoInexistente);
	}
}