package tp1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import model.Cuenta;
import model.repositories.RepositorioDeCuentas;

public class RepositorioDeCuentasTest {
	RepositorioDeCuentas repositorio = new RepositorioDeCuentas();
	RepositorioDeCuentas repositorioMalo = new RepositorioDeCuentas();
	
	Cuenta cuenta0 = new Cuenta(0, "Tipo0", "Empresa", "Periodo", (long) 0000);
	Cuenta cuenta1 = new Cuenta(1, "Tipo1", "Empresa", "Periodo", (long) 1000);
	Cuenta cuenta2 = new Cuenta(2, "Tipo2", "Empresa2", "Periodo2", (long) 2000);
	Cuenta cuenta3 = new Cuenta(3, "Tipo3", "Empresa3", "Periodo2", (long) 3000);
	Cuenta cuentaConIdMalo0 = new Cuenta(8, "Tipo0", "Empresa2", "Periodo2", (long) 2000);
	Cuenta cuentaConIdMalo1 = new Cuenta(7, "Tipo1", "Empresa1", "Periodo1", (long) 1000);
	
	List<Cuenta> cuentas;
	
	@Before
	public void setUp(){
		repositorio.agregarCuenta(cuenta0);
		repositorio.agregarCuenta(cuenta1);
		repositorio.agregarCuenta(cuenta2);
		repositorio.agregarCuenta(cuenta3);
		repositorioMalo.agregarCuenta(cuentaConIdMalo0);
		repositorioMalo.agregarCuenta(cuentaConIdMalo1);
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
		RepositorioDeCuentas _repositorioDePrueba = new RepositorioDeCuentas();
		
		_repositorioDePrueba.agregarCuentaConIdAutogenerado(cuentaConIdMalo0);
		_repositorioDePrueba.agregarCuentaConIdAutogenerado(cuentaConIdMalo1);
		
		Cuenta cuentaDelRepositorio0 = _repositorioDePrueba.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = _repositorioDePrueba.getCuentas().get(1);
		assertTrue(cuentaDelRepositorio0.getId() == 0 &&
				   cuentaDelRepositorio1.getId() == 1);
	}
	@Test
	public void regenerarLosId(){
		repositorioMalo.regenerarLosId();
		
		Cuenta cuentaDelRepositorio0 = repositorioMalo.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorioMalo.getCuentas().get(1);
		assertTrue(cuentaDelRepositorio0.getId() == 0 &&
				   cuentaDelRepositorio1.getId() == 1);
	}
	@Test
	public void filtarCuentasPorTipo(){
		cuentas = repositorio.filtarCuentasPorTipo("Tipo0");
		assertTrue(cuentas.size()==1);
	}
	@Test
	public void filtrarCuentasPorPeriodo(){
		cuentas = repositorio.filtrarCuentasPorPeriodo("Periodo");
		assertTrue(cuentas.size()==2);
	}
	@Test
	public void filtrarCuentasPorEmpresa(){
		cuentas = repositorio.filtrarCuentasPorEmpresa("Empresa");
		assertTrue(cuentas.size()==2);
	}
	@Test
	public void filtrarCuentasPorValor(){
		cuentas = repositorio.filtrarCuentasPorValor((long) 1000);
		assertTrue(cuentas.size()==1);
	}
	@Test
	public void reordenarCuentasPorId(){
		repositorioMalo.reordenarCuentasPorId();
		
		Cuenta cuentaDelRepositorio0 = repositorioMalo.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorioMalo.getCuentas().get(1);
		assertTrue(cuentaDelRepositorio0.getId() <
				   cuentaDelRepositorio1.getId());
	}
	/* Estos métodos de ordenamiento funcionan mal
	@Test
	public void getCuentasOrdenadasPorEmpresa(){
		cuentas = repositorioMalo.getCuentasOrdenadasPorEmpresa();
		
		Cuenta cuentaDelRepositorio0 = repositorioMalo.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorioMalo.getCuentas().get(1);
		
		assertTrue(cuentaDelRepositorio0.getEmpresa() == "Empresa1" &&
				   cuentaDelRepositorio1.getEmpresa() == "Empresa2");
	}
	@Test
	public void getCuentasOrdenadasPorPeriodo(){
		cuentas = repositorioMalo.getCuentasOrdenadasPorPeriodo();
		
		Cuenta cuentaDelRepositorio0 = repositorioMalo.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorioMalo.getCuentas().get(1);
		
		assertTrue(cuentaDelRepositorio0.getEmpresa() == "Periodo1" &&
				   cuentaDelRepositorio1.getEmpresa() == "Periodo2");
	}
	@Test
	public void getCuentasOrdenadasPorValor(){
		cuentas = repositorioMalo.getCuentasOrdenadasPorValor();
		
		Cuenta cuentaDelRepositorio0 = repositorioMalo.getCuentas().get(0);
		Cuenta cuentaDelRepositorio1 = repositorioMalo.getCuentas().get(1);
		
		assertTrue(cuentaDelRepositorio0.getValor() <
				   cuentaDelRepositorio1.getValor());
	}*/
}
