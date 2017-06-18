package appDataTestConStub;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import utils.AppData;

public class AppDataTestConStub {
	
	DtoRutaArchivoDeCuentasBueno dtoArchivoDeCuentasBueno = new DtoRutaArchivoDeCuentasBueno();
	DtoRutaArchivoIndicadoresBueno dtoArchivoIndicadoresBueno = new DtoRutaArchivoIndicadoresBueno();
	
	AppData appData = AppData.getInstance();
	
	@Before
	public void setUp() {

		DtoRutaArchivoDeCuentasParaGuardar dtoArchivoDeCuentasParaGuardar = new DtoRutaArchivoDeCuentasParaGuardar();
		DtoRutaArchivoIndicadoresParaGuardar dtoArchivoIndicadoresParaGuardar = new DtoRutaArchivoIndicadoresParaGuardar();
		
		RepositorioCuentas.getInstance().setDtoCuentas(dtoArchivoDeCuentasParaGuardar);
		RepositorioIndicadores.getInstance().setDtoIndicadores(dtoArchivoIndicadoresParaGuardar);
		
		appData.setInicializacionDeCuentas(dtoArchivoDeCuentasBueno);
		appData.setInicializacionDeIndicadores(dtoArchivoIndicadoresBueno);
	}

	@Test
	public void cargarCuentasDeArchivoBueno() {
		
		appData.cargarCuentas(dtoArchivoDeCuentasBueno);
		RepositorioCuentas repositorio = RepositorioCuentas.getInstance();

		assertTrue(repositorio.size() == 1);
	}

	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoInexistente() {
		DtoRutaArchivoInexistente dtoArchivoInexistente = new DtoRutaArchivoInexistente();
		AppData.getInstance().cargarCuentas(dtoArchivoInexistente);
	}

	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoMalo() {
		DtoRutaArchivoDeCuentasMalo dtoArchivoDeCuentasMalo = new DtoRutaArchivoDeCuentasMalo();
		AppData.getInstance().cargarCuentas(dtoArchivoDeCuentasMalo);
	}
/*	
	@Test
	public void cargarIndicadoresDeArchivoBueno() {
		
		appData.cargarIndicadores(dtoArchivoIndicadoresBueno);
		RepositorioIndicadores repositorio = RepositorioIndicadores.getInstance();

		assertTrue(repositorio.size() == 1);
	}
*/	
	@Test(expected = UserException.class)
	public void cargarIndicadoresDeArchivoInexistente() {
		DtoRutaArchivoInexistente dtoArchivoInexistente = new DtoRutaArchivoInexistente();
		AppData.getInstance().cargarIndicadores(dtoArchivoInexistente);
	}
}
