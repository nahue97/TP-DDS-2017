package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import ExceptionsPackage.RutaDeArchivoInvalidaException;
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

	@Test
	public void funcionSobreescribirCreaArchivoSiNoExiste() {
		ManejoDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);

		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);

		assertTrue(contenidoDelArchivo.equals(jsonMagico));
	}

	@Test
	public void sobreescribirUnArchivo() {
		ManejoDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, "cualquier cosa a sobreescribir");
		ManejoDeArchivos.sobreescribirArchivo(rutaDelArchivoAEscribir, jsonMagico);

		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoAEscribir);

		assertTrue(contenidoDelArchivo.equals(jsonMagico));
	}

	@Test(expected = RutaDeArchivoInvalidaException.class)
	public void rutaDeArchivoMalaTiraExeptionEnSobreescribir() {

		ManejoDeArchivos.sobreescribirArchivo(rutaDeArchivoMala, jsonMagico);
	}

	@Test
	public void leerArchivoBueno() {
		String rutaDelArchivoBueno = "./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoBueno);
		String contenidoEsperado = "[{\"id\":0,\"tipo\":\"EBITDA\",\"empresa\":\"Facebook\",\"periodo\":\"2016\",\"valor\":10000}]";

		assertTrue(contenidoDelArchivo.equals(contenidoEsperado));
	}

	@Test(expected = UserException.class)
	public void FallarAlLeerArchivoIxensistente() {
		String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";

		ManejoDeArchivos.leerArchivo(rutaDeArchivoInexistente);
	}
}