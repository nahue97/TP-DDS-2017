package appDataTestConStub;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import model.repositories.RepositorioIndicadores;
import utils.AppData;

public class AppDataTestDeIndicadores implements IAppDataTest {
	
	RutaArchivoIndicadoresBueno dtoArchivoIndicadoresBueno = new RutaArchivoIndicadoresBueno();
	AppData appData;
	
	@Before
	public void setUp() {
		AppData.limpiar();
		//RepositorioIndicadores.getInstance().limpiarRepositorio();
		appData = AppData.getInstance();
		//RutaArchivoIndicadoresParaGuardar dtoArchivoIndicadoresParaGuardar = new RutaArchivoIndicadoresParaGuardar();
		appData.setInicializacionDeIndicadores(dtoArchivoIndicadoresBueno);
	}

	@Test
	public void cargarDeArchivoBueno() {
		
		appData.cargarIndicadores(dtoArchivoIndicadoresBueno);
		RepositorioIndicadores repositorio = RepositorioIndicadores.getInstance();

		assertTrue(repositorio.getAll().size() == 1);
	}
	
	@Test(expected = UserException.class)
	public void cargarDeArchivoInexistente() {
		RutaArchivoInexistente dtoArchivoInexistente = new RutaArchivoInexistente();
		AppData.getInstance().cargarIndicadores(dtoArchivoInexistente);
	}
	
	@Test(expected = UserException.class)
	public void cargarDeArchivoMalo() {
		RutaArchivoIndicadoresMalo dtoArchivoIndicadoresMalo = new RutaArchivoIndicadoresMalo();
		AppData.getInstance().cargarIndicadores(dtoArchivoIndicadoresMalo);
	}
}
