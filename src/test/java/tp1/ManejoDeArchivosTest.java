package tp1;

import org.junit.After;
import org.junit.Test;
import org.uqbar.commons.model.UserException;
import utils.ManejoDeArchivos;

//No cambiar los archivos para test ya que los tests van a fallar

public class ManejoDeArchivosTest {
	String rutaDelArchivoAEscribir = "./Archivos de prueba/ArchivoParaTestsDeGrabacion.txt";
	String rutaDeArchivoMala = "./Ruta Invalida/Necronomicon.txt";
	String jsonMagico = "Un Json Magico";

	// Si el archivo no est� lo crea as� que no se prueba por archivo
	// inexistente

	@After
	public void borrarArchivo() {
		ManejoDeArchivos.borrarArchivo(rutaDelArchivoAEscribir);
	}

	@Test(expected = UserException.class)
	public void FallarAlLeerArchivoIxensistente() {
		String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";

		ManejoDeArchivos.leerArchivo(rutaDeArchivoInexistente);
	}
}