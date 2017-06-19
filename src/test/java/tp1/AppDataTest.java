package tp1;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import dtos.PathFileTxtJson;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import utils.AppData;

public class AppDataTest {
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";

	PathFileTxtJson datosDeCarga = new PathFileTxtJson("");
	AppData appData = AppData.getInstance();
	private static PathFileTxtJson dtoCuentasALeer = new PathFileTxtJson("./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt");
	private static PathFileTxtJson dtoIndicadoresALeer = new PathFileTxtJson("");
	private static PathFileTxtJson dtoCuentasParaGuardar = new PathFileTxtJson("./Archivos de prueba/TestsDeGrabacionDeCuentasDeRepositorio.txt");
	private static PathFileTxtJson dtoIndicadoresParaGuardar = new PathFileTxtJson("./Archivos de prueba/TestsDeGrabacionDeIndicadoresDeRepositorio.txt");

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
