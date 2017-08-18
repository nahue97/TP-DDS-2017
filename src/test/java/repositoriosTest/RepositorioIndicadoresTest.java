package repositoriosTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ExceptionsPackage.CuentaNotFoundException;
import ExceptionsPackage.IndicadorNotFoundException;
import dtos.PathFileTxtJson;
import model.Indicador;
import model.repositories.RepositorioIndicadores;
import utils.ManejoDeArchivos;

public class RepositorioIndicadoresTest {
	RepositorioIndicadores repositorioIndicadores;

	Indicador indicador0 = new Indicador("Indicador0", "EBITDA + 1");
	Indicador indicador1 = new Indicador("Indicador1", "EBITDA + 2");
	Indicador indicador2 = new Indicador("Indicador2", "EBITDA + 3");
	PathFileTxtJson _dtoIndicadores = new PathFileTxtJson("./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacionInd.txt");
	
	List<Indicador> indicadores;


	@Before
	public void setUp() {
		RepositorioIndicadores.getInstance().limpiarRepositorio();
		repositorioIndicadores = RepositorioIndicadores.getInstance();
		indicadores = new ArrayList<>();
		indicadores.add(indicador0);
		indicadores.add(indicador1);
		indicadores.add(indicador2);	
		repositorioIndicadores.setDtoIndicadores(_dtoIndicadores);
		repositorioIndicadores.agregarIndicadores(indicadores);

	}

	@After
	public void limpiarRepositorios() {
		repositorioIndicadores.limpiarRepositorio();
	}

	@Test
	public void agregarIndicadoresGeneraArchivo() {
		String contenidoDelArchivo = ManejoDeArchivos.leerArchivo("./Archivos de prueba/ArchivoDePruebaParaTestsDeGrabacionInd.txt");

		System.out.println("CONTENIDO DEL ARCHIVO: " + contenidoDelArchivo);

		assertTrue(!contenidoDelArchivo.isEmpty());
	}
	
	@Test(expected = IndicadorNotFoundException.class)
	public void removerIndicadorQueNoExiste() {
		Indicador indicadorQueNoExiste = new Indicador("Raro", "Raro");
		indicadorQueNoExiste.setId(9);
		repositorioIndicadores.removerIndicador(indicadorQueNoExiste);
	}

	@Test(expected = IndicadorNotFoundException.class)
	public void removerIndicadorPorIdQueNoExiste() {
		repositorioIndicadores.removerIndicadorPorId(404);
	}

	@Test
	public void getIndicadorPorId() {
		Indicador indicadorObtenidaPorMetodo = repositorioIndicadores.getIndicadorPorId(0);
		assertTrue(indicador0 == indicadorObtenidaPorMetodo);
	}

	@Test(expected = IndicadorNotFoundException.class)
	public void getIndicadorPorIdQueNoExiste() {
		repositorioIndicadores.getIndicadorPorId(404);
	}

	@Test(expected = CuentaNotFoundException.class)
	public void filtrarIndicadorPorTodo() {
		repositorioIndicadores.filtrarIndicadores("Facebook", " ", " ", new BigDecimal(1000));
	}
	
	@Test
	public void filtrarIndicadoresPorNombre() {
		List<Indicador> indicadores = repositorioIndicadores.filtrarIndicadoresPorNombre("Indicador0");

		assertEquals(1,indicadores.size());
	}	

}
