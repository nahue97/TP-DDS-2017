package tp1;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;
import dtos.PathFileTxtJson;
import model.repositories.RepositorioCuentas;
import utils.AppData;

public class AppDataTest {
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";

	PathFileTxtJson datosDeCarga = new PathFileTxtJson("");
	AppData appData = AppData.getInstance();
	private static PathFileTxtJson dtoCuentasALeer = new PathFileTxtJson("./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt");
	private static PathFileTxtJson dtoIndicadoresALeer = new PathFileTxtJson("");

	@Before
	public void setUp() {
		AppData.limpiar();
		//RepositorioCuentas.getInstance().limpiarRepositorio();
		//RepositorioIndicadores.getInstance().limpiarRepositorio();
		appData = AppData.getInstance();
		// dtoIndicadoresALeer.setPathFile(rutaDeIndicadoresBueno);

		appData.setInicializacionDeCuentas(dtoCuentasALeer);
		appData.setInicializacionDeIndicadores(dtoIndicadoresALeer);
	}
	

	@Test
	public void cargarCuentasDeArchivoBueno() {
		RepositorioCuentas repositorio = RepositorioCuentas.getInstance();
		RepositorioCuentas.getInstance().limpiarRepositorio();
//		repositorio.limpiarRepositorio();
		datosDeCarga.setPathFile("./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt");
		appData.cargarEmpresasDeCuentas(datosDeCarga);
		appData.cargarCuentas(datosDeCarga);
		assertTrue(repositorio.getAll().size() == 1);
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
