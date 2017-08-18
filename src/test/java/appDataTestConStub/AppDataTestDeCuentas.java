package appDataTestConStub;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import model.repositories.RepositorioCuentas;
import utils.AppData;

public class AppDataTestDeCuentas implements IAppDataTest{
	
	RutaArchivoDeCuentasBueno dtoArchivoDeCuentasBueno = new RutaArchivoDeCuentasBueno();	
	AppData appData;
	
	@Before
	public void setUp() {
		AppData.limpiar();
		RepositorioCuentas.getInstance().limpiarRepositorio();
		appData = AppData.getInstance();
		RutaArchivoDeCuentasParaGuardar dtoArchivoDeCuentasParaGuardar = new RutaArchivoDeCuentasParaGuardar();
		RepositorioCuentas.getInstance().setDtoCuentas(dtoArchivoDeCuentasParaGuardar);
		appData.setInicializacionDeCuentas(dtoArchivoDeCuentasBueno);
	}

	@Test
	public void cargarDeArchivoBueno() {
		
		appData.cargarCuentas(dtoArchivoDeCuentasBueno);
		RepositorioCuentas repositorio = RepositorioCuentas.getInstance();

		assertTrue(repositorio.size() == 1);
	}

	@Test(expected = UserException.class)
	public void cargarDeArchivoInexistente() {
		RutaArchivoInexistente dtoArchivoInexistente = new RutaArchivoInexistente();
		AppData.getInstance().cargarCuentas(dtoArchivoInexistente);
	}

	@Test(expected = UserException.class)
	public void cargarDeArchivoMalo() {
		RutaArchivoDeCuentasMalo dtoArchivoDeCuentasMalo = new RutaArchivoDeCuentasMalo();
		AppData.getInstance().cargarCuentas(dtoArchivoDeCuentasMalo);
	}

}
