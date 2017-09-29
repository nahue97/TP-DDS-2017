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
import model.Empresa;
import model.repositories.RepositorioCuentas;
import utils.ManejoDeArchivos;
import org.hibernate.HibernateException;

public class RepositorioCuentasTest {
	RepositorioCuentas repositorioCuentas;

	Empresa empresa1 = new Empresa("Facebook");
	Empresa empresa2 = new Empresa("Twitter");
	Empresa empresa3 = new Empresa("Grupo América");
	
	Cuenta cuenta0 = new Cuenta("Tipo0", empresa1, "Periodo", new BigDecimal(0));
	Cuenta cuenta1 = new Cuenta("Tipo1", empresa1, "Periodo", new BigDecimal(1000));
	Cuenta cuenta2 = new Cuenta("Tipo2", empresa2, "Periodo2", new BigDecimal(2000));
	Cuenta cuenta3 = new Cuenta("Tipo3", empresa3, "Periodo2", new BigDecimal(3000));
	Cuenta cuentaConDecimales = new Cuenta("Tipo3", empresa3, "Periodo2", new BigDecimal(3.3));
	Cuenta cuentaConIdMalo0 = new Cuenta("Tipo0", empresa2, "Periodo0", new BigDecimal(2000));
	Cuenta cuentaConIdMalo1 = new Cuenta("Tipo1", empresa1, "Periodo1", new BigDecimal(1000));

	List<Cuenta> cuentas;


	@Before
	public void setUp() {
		RepositorioCuentas.getInstance().limpiarRepositorio();
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
	public void limpiarRepositorios() {
		repositorioCuentas.limpiarRepositorio();
	}

	@Test
	public void agregarCuentasGeneraArchivo() {
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo("./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacionCuentas.txt");

		System.out.println("CONTENIDO DEL ARCHIVO: " + contenidoDelArchivo);

		assertTrue(!contenidoDelArchivo.isEmpty());
	}

	@Test(expected = RuntimeException.class)
	public void removerCuentaQueNoExiste() {
		Cuenta cuentaQueNoExiste = new Cuenta("Raro", empresa1, "Raro", new BigDecimal(404));
		repositorioCuentas.delete(cuentaQueNoExiste);
	}

	@Test(expected = RuntimeException.class)
	public void removerCuentaPorIdQueNoExiste() {
		Cuenta cuenta = new Cuenta();
		cuenta.setId(-1L);
		repositorioCuentas.delete(cuenta);
	}

	@Test
	public void getCuentaPorId() {
		assertTrue(cuenta0.getId().equals(repositorioCuentas.getCuentaPorId(0L).getId()));
	}

	@Test(expected = RuntimeException.class)
	public void getCuentaPorIdQueNoExiste() {
		repositorioCuentas.getCuentaPorId(404L);
	}

	@Test
	public void filtrarCuentasPorTodo() {
		List<Cuenta> cuentasIntegral = repositorioCuentas.filtrarCuentas("Tipo0", empresa1, "Periodo", new BigDecimal(0));

		assertEquals(1,cuentasIntegral.size());
	}
	
	@Test
	public void filtrarCuentasPorTipo() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("Tipo0", null, "", null);

		assertEquals(1,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorEmpresa() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", empresa1, "", null);

		assertEquals(2,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorPeriodo() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", null, "Periodo", null);

		assertEquals(2,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorValor() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", null, "", new BigDecimal(3.3));

		assertEquals(1,cuentas.size());
	}

	@Test
	public void getCuentasPorEmpresa() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.add(cuentaConIdMalo0);
		repositorioCuentas.add(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getAll();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(
				cuentaDelRepositorio0.getEmpresa().equals(empresa2) && cuentaDelRepositorio1.getEmpresa().equals(empresa1));
	}

	@Test
	public void getCuentasPorPeriodo() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.add(cuentaConIdMalo0);
		repositorioCuentas.add(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorPeriodo();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(
				cuentaDelRepositorio0.getPeriodo().equals("Periodo0") && cuentaDelRepositorio1.getPeriodo().equals("Periodo1"));
	}

	@Test
	public void getCuentasPorValor() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.add(cuentaConIdMalo0);
		repositorioCuentas.add(cuentaConIdMalo1);

		cuentas = repositorioCuentas.getCuentasPorValor();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(cuentaDelRepositorio0.getValor().
				   compareTo(cuentaDelRepositorio1.getValor()) < 0);
	}

	@Test
	public void getCuentasPorTipo() {
		repositorioCuentas.limpiarRepositorio();
		repositorioCuentas.add(cuentaConIdMalo0);
		repositorioCuentas.add(cuentaConIdMalo1);

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
