package appDataTestConStub;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import utils.AppData;

public class AppDataTestConStub {
	DtoRutasArchivosStub dtoRutaDeArchivo = new DtoRutasArchivosStub("");

	AppData appData = AppData.getInstance();
	
	@Before
	public void setUp() {

		dtoRutaDeArchivo.setRutaCuentasParaGuardar();
		RepositorioCuentas.getInstance().setDtoCuentas(dtoRutaDeArchivo);
		
		dtoRutaDeArchivo.setRutaIndicadoresParaGuardar();
		RepositorioIndicadores.getInstance().setDtoIndicadores(dtoRutaDeArchivo);

		dtoRutaDeArchivo.setRutaDeCuentasBueno();
		appData.setInicializacionDeCuentas(dtoRutaDeArchivo);
		
		dtoRutaDeArchivo.setRutaIndicadoresArchivoBueno();
		appData.setInicializacionDeIndicadores(dtoRutaDeArchivo);
	}

	@Test
	public void cargarCuentasDeArchivoBueno() {
		
		dtoRutaDeArchivo.setRutaDeCuentasBueno();
		appData.cargarCuentas(dtoRutaDeArchivo);
		RepositorioCuentas repositorio = RepositorioCuentas.getInstance();

		assertTrue(repositorio.size() == 1);
	}

	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoInexistente() {
		dtoRutaDeArchivo.setRutaDeArchivoInexistente();
		AppData.getInstance().cargarCuentas(dtoRutaDeArchivo);
	}

	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoMalo() {
		dtoRutaDeArchivo.setRutaDeArchivoMalo();
		AppData.getInstance().cargarCuentas(dtoRutaDeArchivo);
	}
}
