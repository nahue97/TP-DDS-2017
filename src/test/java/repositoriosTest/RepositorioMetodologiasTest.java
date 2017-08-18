package repositoriosTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbar.commons.model.UserException;

import ExceptionsPackage.MetodologiaNotFoundException;
import model.Indicador;
import model.Metodologia;
import model.Criterio;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioMetodologias;
import utils.AppData;

public class RepositorioMetodologiasTest {
	RepositorioMetodologias repositorioMetodologias;
	Indicador indicador0 = new Indicador("Indicador0", "EBITDA + 1");
	Indicador indicador1 = new Indicador("Indicador1", "EBITDA + 2");
	ReglaComparativa reglaComparativa = new ReglaComparativa("regla01",indicador0,Criterio.MAYOR);
	BigDecimal valor = new BigDecimal(100);
	ReglaTaxativa reglaTaxativa = new ReglaTaxativa("regla02", indicador1, '>', valor);
	List<Regla> reglas = new ArrayList<Regla>();
	Metodologia metodologia0 = new Metodologia("metodologia0",reglas);
	
	@Before
	public void setUp() {
		RepositorioCuentas.getInstance().limpiarRepositorio();
		repositorioMetodologias = RepositorioMetodologias.getInstance();
		reglas.add(reglaComparativa);
		reglas.add(reglaTaxativa);
		repositorioMetodologias.agregarMetodologia(metodologia0);
		repositorioMetodologias.agregarReglaTemporal(reglaTaxativa);
	}

	@After
	public void limpiarRepositorios() {
		repositorioMetodologias.limpiarRepositorio();
	}

	@Test
	public void agregarMetodologia() {
		assertEquals(repositorioMetodologias.getMetodologias().size(),1);
	}
	
	@Test
	public void nombresDeMetodologiasOK() {
		List<String> nombres = new ArrayList<String>();
		nombres.add("metodologia0");
		assertEquals(repositorioMetodologias.getNombresDeMetodologias(),nombres);
	}
	
	@Test
	public void metodologiaPorNombreOK() {
		assertEquals(repositorioMetodologias.getMetodologiaPorNombre("metodologia0"), metodologia0);
	}
	
	@Test(expected = MetodologiaNotFoundException.class)
	public void metodologiaPorNombreNoEncontrada() {
		repositorioMetodologias.getMetodologiaPorNombre("noExiste");
	}

	@Test(expected = UserException.class)
	public void crearMetodologiaConElMismoNombreQueUnaYaExistente() {
		repositorioMetodologias.existeNombreMetodologia("metodologia0");
	}

	@Test
	public void agregarReglaTemporal() {
		repositorioMetodologias.agregarReglaTemporal(reglaComparativa);
		assertEquals(repositorioMetodologias.getReglasTemporales().size(),2);
	}
	
	@Test
	public void eliminarReglaTemporal() {
		repositorioMetodologias.eliminarReglaTemporal("regla02");
		assertTrue(repositorioMetodologias.getReglasTemporales().isEmpty());
	}

	@Test
	public void vaciarReglasTemporales() {
		repositorioMetodologias.limpiarRepositorio();
		assertTrue(repositorioMetodologias.getReglasTemporales().isEmpty());
	}

	@Test
	public void nombresDeReglasTemporalesOK() {
		List<String> reglasN = new ArrayList<String>();
		reglasN.add("regla02");
		assertEquals(repositorioMetodologias.getNombresReglasTemporales(),reglasN);
	}
}