package appDataTestConStub;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import utils.AppData;

public class AppDataTestDeCuentas implements IAppDataTest{
	
	RutaArchivoDeCuentasBueno dtoArchivoDeCuentasBueno = new RutaArchivoDeCuentasBueno();	
	AppData appData;
	RepositorioCuentas repositorioCuentas = RepositorioCuentas.getInstance();
	RepositorioEmpresas repositorioEmpresas = RepositorioEmpresas.getInstance();
	
	@Before
	public void setUp() {
//		AppData.limpiar();
//		RepositorioCuentas.getInstance().limpiarRepositorio();
		appData = AppData.getInstance();
//		RutaArchivoDeCuentasParaGuardar dtoArchivoDeCuentasParaGuardar = new RutaArchivoDeCuentasParaGuardar();
		appData.setInicializacionDeCuentas(dtoArchivoDeCuentasBueno);
	}
	
	@After	
	public void limpiarRepositorios() {
		repositorioCuentas.limpiarRepositorio();
		repositorioEmpresas.limpiarRepositorio();
	}
	

	@Test
	public void cargarDeArchivoBueno() {
		appData.cargarEmpresasDeCuentas(dtoArchivoDeCuentasBueno);
		appData.cargarCuentas(dtoArchivoDeCuentasBueno);
		assertTrue(repositorioCuentas.getAll().size() == 1);
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
