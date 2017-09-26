package repositoriosTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ExceptionsPackage.CuentaNotFoundException;
import model.Cuenta;
import model.repositories.RepositorioCuentas;
import utils.ManejoDeArchivos;

public class RepositorioCuentasTest {
	RepositorioCuentas repositorioCuentas;

	Cuenta cuenta0 = new Cuenta("Tipo0", "Empresa", "Periodo", new BigDecimal(0));
	Cuenta cuenta1 = new Cuenta("Tipo1", "Empresa", "Periodo", new BigDecimal(1000));
	Cuenta cuenta2 = new Cuenta("Tipo2", "Empresa2", "Periodo2", new BigDecimal(2000));
	Cuenta cuenta3 = new Cuenta("Tipo3", "Empresa3", "Periodo2", new BigDecimal(3000));
	Cuenta cuentaConDecimales = new Cuenta("Tipo3", "Empresa3", "Periodo2", new BigDecimal(3.3));
	Cuenta cuentaConIdMalo0 = new Cuenta("Tipo1", "Empresa2", "Periodo2", new BigDecimal(2000));
	Cuenta cuentaConIdMalo1 = new Cuenta("Tipo0", "Empresa1", "Periodo1", new BigDecimal(1000));

	List<Cuenta> cuentas;


	@Before
	public void setUp() {
		//RepositorioCuentas.getInstance().limpiarRepositorio();
		repositorioCuentas = RepositorioCuentas.getInstance();
		cuentas = new ArrayList<>();
		cuentas.add(cuenta0);
		cuentas.add(cuenta1);
		cuentas.add(cuenta2);
		cuentas.add(cuenta3);
		cuentas.add(cuentaConDecimales);
		repositorioCuentas.agregarCuentas(cuentas);

	}

	@After
	/*public void limpiarRepositorios() {
		repositorioCuentas.limpiarRepositorio();
	}*/

	@Test
	public void agregarCuentasGeneraArchivo() {
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo("./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacionCuentas.txt");

		System.out.println("CONTENIDO DEL ARCHIVO: " + contenidoDelArchivo);

		assertTrue(!contenidoDelArchivo.isEmpty());
	}

	@Test(expected = CuentaNotFoundException.class)
	public void removerCuentaQueNoExiste() {
		Cuenta cuentaQueNoExiste = new Cuenta("Raro", "Rara", "Raro", new BigDecimal(404));
		repositorioCuentas.removerCuenta(cuentaQueNoExiste);
	}

	@Test(expected = CuentaNotFoundException.class)
	public void removerCuentaPorIdQueNoExiste() {
		repositorioCuentas.removerCuentaPorId(7L);
	}

	@Test
	public void getCuentaPorId() {
		Cuenta cuentaObtenidaPorMetodo = repositorioCuentas.getCuentaPorId(0L);
		assertTrue(cuenta0.equals(cuentaObtenidaPorMetodo));
	}

	@Test(expected = CuentaNotFoundException.class)
	public void getCuentaPorIdQueNoExiste() {
		repositorioCuentas.getCuentaPorId(404L);
	}

	@Test
	public void filtrarCuentasPorTodo() {
		List<Cuenta> cuentasIntegral = repositorioCuentas.filtrarCuentas("Tipo0", "Empresa", "Periodo", new BigDecimal(0));

		assertEquals(1,cuentasIntegral.size());
	}
	
	@Test
	public void filtrarCuentasPorTipo() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("Tipo0", "", "", null);

		assertEquals(1,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorEmpresa() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", "Empresa", "", null);

		assertEquals(2,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorPeriodo() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", "", "Periodo", null);

		assertEquals(2,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorValor() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", "", "", new BigDecimal(3.3));

		assertEquals(1,cuentas.size());
	}

	@Test
	public void getCuentasPorEmpresa() {
		//repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorEmpresa();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(
				cuentaDelRepositorio0.getEmpresa().equals("Empresa1") && cuentaDelRepositorio1.getEmpresa().equals("Empresa2"));
	}

	@Test
	public void getCuentasPorPeriodo() {
		//repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorPeriodo();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(
				cuentaDelRepositorio0.getPeriodo().equals("Periodo1") && cuentaDelRepositorio1.getPeriodo().equals("Periodo2"));
	}

	@Test
	public void getCuentasPorValor() {
		//repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorValor();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(cuentaDelRepositorio0.getValor().
				   compareTo(cuentaDelRepositorio1.getValor()) < 0);
	}

	@Test
	public void getCuentasPorTipo() {
		//repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.agregarCuenta(cuentaConIdMalo0);
		repositorioCuentas.agregarCuenta(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorTipo();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(cuentaDelRepositorio0.getTipo().equals("Tipo0") && cuentaDelRepositorio1.getTipo().equals("Tipo1"));
	}

	@Test
	public void getTiposDeCuenta() {
		Collection<String> tipos = repositorioCuentas.getTiposDeCuenta();

		assertTrue(tipos.size() == 4 && tipos.contains("Tipo0") && tipos.contains("Tipo1") && tipos.contains("Tipo2")
				&& tipos.contains("Tipo3"));
	}
}
