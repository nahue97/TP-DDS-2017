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
	String rutaDeCuentasBueno = "./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoMalo = "./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	String rutaCuentasParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeCuentasDeRepositorio.txt";
	String rutaIndicadoresParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeIndicadoresDeRepositorio.txt";

	CargaDeArchivoTxtJsonDTO datosDeCarga = new CargaDeArchivoTxtJsonDTO();
	AppData appData = AppData.getInstance();
	private static CargaDeArchivoTxtJsonDTO dtoCuentasALeer = new CargaDeArchivoTxtJsonDTO();
	private static CargaDeArchivoTxtJsonDTO dtoIndicadoresALeer = new CargaDeArchivoTxtJsonDTO();
	private static CargaDeArchivoTxtJsonDTO dtoCuentasParaGuardar = new CargaDeArchivoTxtJsonDTO();
	private static CargaDeArchivoTxtJsonDTO dtoIndicadoresParaGuardar = new CargaDeArchivoTxtJsonDTO();

	@Before
	public void setUp() {
		dtoCuentasParaGuardar.setPathFile(rutaCuentasParaGuardar);
		dtoIndicadoresParaGuardar.setPathFile(rutaIndicadoresParaGuardar);

		RepositorioCuentas.setDtoCuentas(dtoCuentasParaGuardar);
		RepositorioIndicadores.setDtoIndicadores(dtoIndicadoresParaGuardar);

		dtoCuentasALeer.setPathFile(rutaDeCuentasBueno);
		// dtoIndicadoresALeer.setPathFile(rutaDeIndicadoresBueno);

		appData.setInicializacionDeCuentas(dtoCuentasALeer);
		appData.setInicializacionDeIndicadores(dtoIndicadoresALeer);
	}

	@Test
	public void cargarCuentasDeArchivoBueno() {
		datosDeCarga.setPathFile(rutaDeCuentasBueno);

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
		datosDeCarga.setPathFile(rutaDeArchivoMalo);
		AppData.getInstance().cargarCuentas(datosDeCarga);
	}
}
