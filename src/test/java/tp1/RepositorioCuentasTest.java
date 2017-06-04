package tp1;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ExceptionsPackage.CuentaNotFoundException;
import dtos.CargaDeArchivoTxtJsonDTO;
import model.Cuenta;
import model.repositories.RepositorioCuentas;
import utils.ManejoDeArchivos;

public class RepositorioCuentasTest {
	RepositorioCuentas repositorioCuentas;

	Cuenta cuenta0 = new Cuenta(0, "Tipo0", "Empresa", "Periodo", (long) 0000);
	Cuenta cuenta1 = new Cuenta(1, "Tipo1", "Empresa", "Periodo", (long) 1000);
	Cuenta cuenta2 = new Cuenta(2, "Tipo2", "Empresa2", "Periodo2", (long) 2000);
	Cuenta cuenta3 = new Cuenta(3, "Tipo3", "Empresa3", "Periodo2", (long) 3000);
	Cuenta cuentaConIdMalo0 = new Cuenta(8, "Tipo1", "Empresa2", "Periodo2", (long) 2000);
	Cuenta cuentaConIdMalo1 = new Cuenta(7, "Tipo0", "Empresa1", "Periodo1", (long) 1000);

	List<Cuenta> cuentas;

	String rutaDelArchivoDeCuentas = "./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacion.txt";

	private CargaDeArchivoTxtJsonDTO dtoCuentas = new CargaDeArchivoTxtJsonDTO();

	@Before
	public void setUp() {
		repositorioCuentas = RepositorioCuentas.getInstance();
		dtoCuentas.setPathFile(rutaDelArchivoDeCuentas);
		cuentas = new ArrayList<>();
		cuentas.add(cuenta0);
		cuentas.add(cuenta1);
		cuentas.add(cuenta2);
		cuentas.add(cuenta3);
		repositorioCuentas.agregarCuentas(cuentas);

	}

	@After
	public void limpiarRepositorios() {
		repositorioCuentas.limpiarRepositorio();
	}

	@Test
	public void agregarCuentasGeneraArchivo() {
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo(rutaDelArchivoDeCuentas);

		System.out.println("CONTENIDO DEL ARCHIVO: " + contenidoDelArchivo);

		assertTrue(!contenidoDelArchivo.isEmpty());
	}

	@Test(expected = CuentaNotFoundException.class)
	public void removerCuentaQueNoExiste() {
		Cuenta cuentaQueNoExiste = new Cuenta(404, "Raro", "Rara", "Raro", (long) 999999999);
		repositorioCuentas.removerCuenta(cuentaQueNoExiste);
	}

	@Test(expected = CuentaNotFoundException.class)
	public void removerCuentaPorIdQueNoExiste() {
		repositorioCuentas.removerCuentaPorId(7);
	}

	@Test
	public void getCuentaPorId() {
		Cuenta cuentaObtenidaPorMetodo = repositorioCuentas.getCuentaPorId(0);
		assertTrue(cuenta0 == cuentaObtenidaPorMetodo);
	}

	@Test(expected = CuentaNotFoundException.class)
	public void getCuentaPorIdQueNoExiste() {
		repositorioCuentas.getCuentaPorId(404);
	}

	@Test
	public void filtrarCuentas() {
		List<Cuenta> cuentasIntegral = repositorioCuentas.filtrarCuentas("Tipo0", "Empresa", "Periodo", "0000");
		List<Cuenta> cuentasPorEmpresa = repositorioCuentas.filtrarCuentas("", "Empresa", "", "");
		List<Cuenta> cuentasPorPeriodo = repositorioCuentas.filtrarCuentas("", "", "Periodo2", "");
		List<Cuenta> cuentasPorTipo = repositorioCuentas.filtrarCuentas("Tipo0", "", "", "");
		List<Cuenta> cuentasPorValor = repositorioCuentas.filtrarCuentas("", "", "", "2000");

		assertTrue(cuentasIntegral.size() == 1 && cuentasPorEmpresa.size() == 2 && cuentasPorPeriodo.size() == 2
				&& cuentasPorTipo.size() == 1 && cuentasPorValor.size() == 1);
	}

	@Test
	public void getCuentasPorEmpresa() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorEmpresa();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(
				cuentaDelRepositorio0.getEmpresa() == "Empresa1" && cuentaDelRepositorio1.getEmpresa() == "Empresa2");
	}

	@Test
	public void getCuentasPorPeriodo() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorPeriodo();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(
				cuentaDelRepositorio0.getPeriodo() == "Periodo1" && cuentaDelRepositorio1.getPeriodo() == "Periodo2");
	}

	@Test
	public void getCuentasPorValor() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorValor();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(cuentaDelRepositorio0.getValor() < cuentaDelRepositorio1.getValor());
	}

	@Test
	public void getCuentasPorTipo() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorTipo();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(cuentaDelRepositorio0.getTipo() == "Tipo0" && cuentaDelRepositorio1.getTipo() == "Tipo1");
	}

	@Test
	public void getTiposDeCuenta() {
		Collection<String> tipos = repositorioCuentas.getTiposDeCuenta();

		assertTrue(tipos.size() == 4 && tipos.contains("Tipo0") && tipos.contains("Tipo1") && tipos.contains("Tipo2")
				&& tipos.contains("Tipo3"));
	}
}
