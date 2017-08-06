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
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioMetodologias;

public class RepositorioMetodologiasTest {
	RepositorioMetodologias repositorioMetodologias;
	
	
	@Before
	public void setUp() {
		
	}

	@After
	public void limpiarRepositorios() {
		repositorioMetodologias.limpiarRepositorio();
	}

	@Test
	public void agregarMetodologia() {
		
	}
	
	@Test
	public void nombresDeMetodologiasOK() {
		
	}
	
	@Test
	public void metodologiaPorNombreOK() {
		
	}
	
	@Test(expected = MetodologiaNotFoundException.class)
	public void metodologiaPorNombreNoEncontrada() {
		
	}

	@Test(expected = UserException.class)
	public void crearMetodologiaConElMismoNombreQueUnaYaExistente() {
		
	}

	@Test
	public void agregarReglaTemporal() {

	}
	
	@Test
	public void eliminarReglaTemporal() {

	}

	@Test
	public void vaciarReglasTemporales() {

	}

	@Test
	public void nombresDeReglasTemporalesOK() {
		
	}
	
	@Test
	public void filtrarIndicadoresPorNombre() {
	}	

}
