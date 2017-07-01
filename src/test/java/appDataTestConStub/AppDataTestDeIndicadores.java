package appDataTestConStub;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import model.repositories.RepositorioIndicadores;
import utils.AppData;

public class AppDataTestDeIndicadores implements IAppDataTest {
	
	RutaArchivoIndicadoresBueno dtoArchivoIndicadoresBueno = new RutaArchivoIndicadoresBueno();
	AppData appData = AppData.getInstance();
	
	@Before
	public void setUp() {
		RutaArchivoIndicadoresParaGuardar dtoArchivoIndicadoresParaGuardar = new RutaArchivoIndicadoresParaGuardar();
		RepositorioIndicadores.getInstance().setDtoIndicadores(dtoArchivoIndicadoresParaGuardar);		
		appData.setInicializacionDeIndicadores(dtoArchivoIndicadoresBueno);
	}

	@Test
	public void cargarDeArchivoBueno() {
		
		appData.cargarIndicadores(dtoArchivoIndicadoresBueno);
		RepositorioIndicadores repositorio = RepositorioIndicadores.getInstance();

		assertTrue(repositorio.size() == 1);
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
