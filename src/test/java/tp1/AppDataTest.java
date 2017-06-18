package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import dtos.CargaDeArchivoTxtJsonDTO;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import utils.AppData;

public class AppDataTest {
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";

	CargaDeArchivoTxtJsonDTO datosDeCarga = new CargaDeArchivoTxtJsonDTO("");
	AppData appData = AppData.getInstance();
	private static CargaDeArchivoTxtJsonDTO dtoCuentasALeer = new CargaDeArchivoTxtJsonDTO("./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt");
	private static CargaDeArchivoTxtJsonDTO dtoIndicadoresALeer = new CargaDeArchivoTxtJsonDTO("");
	private static CargaDeArchivoTxtJsonDTO dtoCuentasParaGuardar = new CargaDeArchivoTxtJsonDTO("./Archivos de prueba/TestsDeGrabacionDeCuentasDeRepositorio.txt");
	private static CargaDeArchivoTxtJsonDTO dtoIndicadoresParaGuardar = new CargaDeArchivoTxtJsonDTO("./Archivos de prueba/TestsDeGrabacionDeIndicadoresDeRepositorio.txt");

	@Before
	public void setUp() {

		RepositorioCuentas.getInstance().setDtoCuentas(dtoCuentasParaGuardar);
		RepositorioIndicadores.getInstance().setDtoIndicadores(dtoIndicadoresParaGuardar);
		// dtoIndicadoresALeer.setPathFile(rutaDeIndicadoresBueno);

		appData.setInicializacionDeCuentas(dtoCuentasALeer);
		appData.setInicializacionDeIndicadores(dtoIndicadoresALeer);
	}

	@Test
	public void cargarCuentasDeArchivoBueno() {
		datosDeCarga.setPathFile("./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt");

		appData.cargarCuentas(datosDeCarga);
		RepositorioCuentas repositorio = RepositorioCuentas.getInstance();

		assertTrue(repositorio.size() == 1);
	}

	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoInexistente() {
		datosDeCarga.setPathFile(rutaDeArchivoInexistente);
		AppData.getInstance().cargarCuentas(datosDeCarga);
	}

	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoMalo() {
		datosDeCarga.setPathFile("./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt");
		AppData.getInstance().cargarCuentas(datosDeCarga);
	}
}
