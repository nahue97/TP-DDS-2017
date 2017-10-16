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
import model.Cuenta;
import model.Empresa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import utils.ManejoDeArchivos;

public class RepositorioCuentasTest {
	RepositorioCuentas repositorioCuentas;
	RepositorioEmpresas repositorioEmpresas;

	Empresa empresa1 = new Empresa("Facebook");
	Empresa empresa2 = new Empresa("Twitter");
	Empresa empresa3 = new Empresa("Grupo América");
	
	Cuenta cuenta0 = new Cuenta("_Tipo0", empresa1, "2016", new BigDecimal(0));
	Cuenta cuenta1 = new Cuenta("_Tipo1", empresa1, "2016", new BigDecimal(1000));
	Cuenta cuenta2 = new Cuenta("_Tipo2", empresa2, "2017", new BigDecimal(2000));
	Cuenta cuenta3 = new Cuenta("_Tipo3", empresa3, "2017", new BigDecimal(3000));
	Cuenta cuentaConDecimales = new Cuenta("_Tipo3", empresa3, "2017", new BigDecimal(3.3));
	Cuenta cuentaConIdMalo0 = new Cuenta("_Tipo0", empresa2, "2014", new BigDecimal(-1));
	Cuenta cuentaConIdMalo1 = new Cuenta("_Tipo1", empresa1, "2015", new BigDecimal(-1));

	List<Cuenta> cuentas;
	List<Empresa> empresas;
	
	@Before
	public void setUp() {
		repositorioCuentas = RepositorioCuentas.getInstance();
		repositorioEmpresas = RepositorioEmpresas.getInstance();
		cuentas = new ArrayList<>();
		empresas = new ArrayList<>();
		cuentas.add(cuenta0);
		cuentas.add(cuenta1);
		cuentas.add(cuenta2);
		cuentas.add(cuenta3);
		cuentas.add(cuentaConDecimales);
		cuentas.add(cuentaConIdMalo0);
		cuentas.add(cuentaConIdMalo1);
		empresas.add(empresa1);
		empresas.add(empresa2);
		empresas.add(empresa3);
		repositorioEmpresas.agregarEmpresas(empresas);
		repositorioCuentas.agregarCuentas(cuentas);
	}

	@After	
	public void limpiarRepositorios() {
		repositorioCuentas.limpiarRepositorio();
		repositorioEmpresas.limpiarRepositorio();
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
		try{repositorioCuentas.delete(cuentaQueNoExiste);
		}catch (Exception e) {
			
		}
		throw new RuntimeException("ok");
		
	}
	
	@Test(expected = RuntimeException.class)
	public void removerCuentaPorIdQueNoExiste() {
		Cuenta cuenta = new Cuenta();
		cuenta.setId(-1L);
		repositorioCuentas.delete(cuenta);
	}

	@Test
	public void getCuentaPorId() {
		Long id = repositorioCuentas.getAll().get(0).getId();
		assertTrue((repositorioCuentas.getCuentaPorId(id)) instanceof Cuenta);
	}

	@Test(expected = RuntimeException.class)
	public void getCuentaPorIdQueNoExiste() {
		repositorioCuentas.getCuentaPorId(404L);
	}

	@Test
	public void filtrarCuentasPorTodo() {
		List<Cuenta> cuentasIntegral = repositorioCuentas.filtrarCuentas("_Tipo0", empresa1, "2016", new BigDecimal(0));

		assertEquals(1,cuentasIntegral.size());
	}
	
	@Test
	public void filtrarCuentasPorTipo() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("_Tipo0", null, "", null);

		assertEquals(2,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorEmpresa() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", empresa1, "", null);

		assertEquals(3,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorPeriodo() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", null, "2016", null);

		assertEquals(2,cuentas.size());
	}
	
	@Test
	public void filtrarCuentasPorValor() {
		List<Cuenta> cuentas = repositorioCuentas.filtrarCuentas("", null, "", new BigDecimal(0));

		assertEquals(1,cuentas.size());
	}

	@Test
	public void getCuentasPorPeriodo() {

		cuentas = repositorioCuentas.getCuentasPorPeriodo();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(
				cuentaDelRepositorio0.getPeriodo().equals("2014") && cuentaDelRepositorio1.getPeriodo().equals("2015"));
	}

	@Test
	public void getCuentasPorValor() {

		cuentas = repositorioCuentas.getCuentasPorValor();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(cuentaDelRepositorio0.getValor().
				   compareTo(cuentaDelRepositorio1.getValor()) == 0);
	}

	@Test
	public void getCuentasPorTipo() {

		cuentas = repositorioCuentas.getCuentasPorTipo();

		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);

		assertTrue(cuentaDelRepositorio0.getTipo().equals("_Tipo0") && cuentaDelRepositorio1.getTipo().equals("_Tipo0"));
	}

	@Test
	public void get_TiposDeCuenta() {
		Collection<String> _Tipos = repositorioCuentas.getTiposDeCuenta();

		assertTrue(_Tipos.size() == 4 && _Tipos.contains("_Tipo0") && _Tipos.contains("_Tipo1") && _Tipos.contains("_Tipo2")
				&& _Tipos.contains("_Tipo3"));
	}
}
