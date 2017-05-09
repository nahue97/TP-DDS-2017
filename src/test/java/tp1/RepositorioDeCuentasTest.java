package tp1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;

public class RepositorioDeCuentasTest {
	RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
	
	Cuenta cuenta0 = new Cuenta(0, "Tipo0", "Empresa", "Periodo", (long) 0000);
	Cuenta cuenta1 = new Cuenta(1, "Tipo1", "Empresa", "Periodo", (long) 1000);
	Cuenta cuenta2 = new Cuenta(2, "Tipo2", "Empresa2", "Periodo2", (long) 2000);
	Cuenta cuenta3 = new Cuenta(3, "Tipo3", "Empresa3", "Periodo2", (long) 3000);
	Cuenta cuentaConIdMalo0 = new Cuenta(8, "Tipo1", "Empresa2", "Periodo2", (long) 2000);
	Cuenta cuentaConIdMalo1 = new Cuenta(7, "Tipo0", "Empresa1", "Periodo1", (long) 1000);
	
	List<Cuenta> cuentas;
	
	@Before
	public void setUp(){
		repositorio.agregarCuenta(cuenta0);
		repositorio.agregarCuenta(cuenta1);
		repositorio.agregarCuenta(cuenta2);
		repositorio.agregarCuenta(cuenta3);
	}
	@After
	public void after(){
		repositorio.limpiarRepositorio();
	}
	
	@Test
	public void removerCuenta(){
		repositorio.removerCuenta(cuenta1);
		assertTrue(repositorio.size() == 3);
	}
	@Test(expected = Error.class)
	public void removerCuentaQueNoExiste(){
		Cuenta cuentaQueNoExiste = new Cuenta(404, "Raro", "Rara", "Raro", (long) 999999999);
		repositorio.removerCuenta(cuentaQueNoExiste);
	}
	@Test
	public void removerCuentaPorId(){
		repositorio.removerCuentaPorId(1);
		assertTrue(repositorio.size() == 3);
	}
	@Test(expected = Error.class)
	public void removerCuentaPorIdQueNoExiste(){
		repositorio.removerCuentaPorId(7);
	}
	@Test
	public void getCuentaPorId(){
		Cuenta cuentaObtenidaPorMetodo = repositorio.getCuentaPorId(0);
		assertTrue(cuenta0 == cuentaObtenidaPorMetodo);
	}
	@Test(expected = Error.class)
	public void getCuentaPorIdQueNoExiste(){
		repositorio.getCuentaPorId(404);
	}
	@Test
	public void agregarCuentaConIdAutogenerado(){
		repositorio.limpiarRepositorio();
		
		repositorio.agregarCuentaConIdAutogenerado(cuentaConIdMalo0);
		repositorio.agregarCuentaConIdAutogenerado(cuentaConIdMalo1);
		
		Cuenta cuentaDelRepositorio0 = repositorio.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorio.getCuentas().get(1);
		assertTrue(cuentaDelRepositorio0.getId() == 0 &&
				   cuentaDelRepositorio1.getId() == 1);
	}
	@Test
	public void regenerarLosId(){
		repositorio.limpiarRepositorio();
		repositorio.agregarCuenta(cuentaConIdMalo0);
		repositorio.agregarCuenta(cuentaConIdMalo1);
		
		repositorio.regenerarLosId();
		
		Cuenta cuentaDelRepositorio0 = repositorio.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorio.getCuentas().get(1);
		assertTrue(cuentaDelRepositorio0.getId() == 0 &&
				   cuentaDelRepositorio1.getId() == 1);
	}
	@Test
	public void filtrarCuentas(){
		List<Cuenta> cuentasIntegral = repositorio.filtrarCuentas("Tipo0", "Empresa", "Periodo","0000");
		List<Cuenta> cuentasPorEmpresa = repositorio.filtrarCuentas("", "Empresa", "", "");
		List<Cuenta> cuentasPorPeriodo = repositorio.filtrarCuentas("", "", "Periodo2", "");
		List<Cuenta> cuentasPorTipo = repositorio.filtrarCuentas("Tipo0", "", "", "");
		List<Cuenta> cuentasPorValor = repositorio.filtrarCuentas("", "", "", "2000");
		
		assertTrue(cuentasIntegral.size() == 1 &&
				   cuentasPorEmpresa.size() == 2 &&
				   cuentasPorPeriodo.size() == 2 &&
				   cuentasPorTipo.size() == 1 &&
				   cuentasPorValor.size() == 1);
	}
	
	@Test
	public void reordenarCuentasPorId(){
		repositorio.limpiarRepositorio();
		repositorio.agregarCuenta(cuentaConIdMalo0);
		repositorio.agregarCuenta(cuentaConIdMalo1);
		
		repositorio.reordenarCuentasPorId();
		
		Cuenta cuentaDelRepositorio0 = repositorio.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorio.getCuentas().get(1);
		assertTrue(cuentaDelRepositorio0.getId() <
				   cuentaDelRepositorio1.getId());
	}
	
	@Test
	public void getCuentasOrdenadasPorEmpresa(){
		repositorio.limpiarRepositorio();
		repositorio.agregarCuenta(cuentaConIdMalo0);
		repositorio.agregarCuenta(cuentaConIdMalo1);
		
		cuentas = repositorio.getCuentasOrdenadasPorEmpresa();
		
		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);
		
		assertTrue(cuentaDelRepositorio0.getEmpresa() == "Empresa1" &&
				   cuentaDelRepositorio1.getEmpresa() == "Empresa2");
	}
	@Test
	public void getCuentasOrdenadasPorPeriodo(){
		repositorio.limpiarRepositorio();
		repositorio.agregarCuenta(cuentaConIdMalo0);
		repositorio.agregarCuenta(cuentaConIdMalo1);
		
		cuentas = repositorio.getCuentasOrdenadasPorPeriodo();
		
		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);
		
		assertTrue(cuentaDelRepositorio0.getPeriodo() == "Periodo1" &&
				   cuentaDelRepositorio1.getPeriodo() == "Periodo2");
	}
	@Test
	public void getCuentasOrdenadasPorValor(){
		repositorio.limpiarRepositorio();
		repositorio.agregarCuenta(cuentaConIdMalo0);
		repositorio.agregarCuenta(cuentaConIdMalo1);
		
		cuentas = repositorio.getCuentasOrdenadasPorValor();
		
		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);
		
		assertTrue(cuentaDelRepositorio0.getValor() <
				   cuentaDelRepositorio1.getValor());
	}
	@Test
	public void getCuentasOrdenadasPorTipo(){
		repositorio.limpiarRepositorio();
		repositorio.agregarCuenta(cuentaConIdMalo0);
		repositorio.agregarCuenta(cuentaConIdMalo1);
		
		cuentas = repositorio.getCuentasOrdenadasPorTipo();
		
		Cuenta cuentaDelRepositorio0 = cuentas.get(0);
		Cuenta cuentaDelRepositorio1 = cuentas.get(1);
		
		assertTrue(cuentaDelRepositorio0.getTipo() == "Tipo0" &&
				   cuentaDelRepositorio1.getTipo() == "Tipo1");
	}
}
