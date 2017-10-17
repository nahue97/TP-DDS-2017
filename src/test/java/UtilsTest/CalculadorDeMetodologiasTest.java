package UtilsTest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.Criterio;
import model.Cuenta;
import model.Empresa;
import model.EmpresaEvaluadaPorMetodologia;
import model.Indicador;
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import model.repositories.RepositorioIndicadores;
import model.repositories.RepositorioMetodologias;
import utils.CalculadorDeMetodologias;
import utils.HashMapUtils;

public class CalculadorDeMetodologiasTest {
	
	RepositorioCuentas repositorioCuentas;
	RepositorioIndicadores repositorioIndicadores;
	RepositorioEmpresas repositorioEmpresas;
	RepositorioMetodologias repositorioMetodologias;
	LinkedHashMap<String, Integer> empresasConPuntajesFinal;
	LinkedHashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores;
	LinkedHashMap<String, Integer> empresasEvaluadasConPuntajes;
	List<String> empresasQueNoAplican;
	List<String> empresasQueNoConvienen;
	List<Empresa> empresas;
	List<String> nombresEmpresas;
	List<Indicador> indicadores;
	List<Metodologia> metodologias;
	Empresa facebook = new Empresa("Facebook");
	Empresa twitter = new Empresa("Twitter");
	Empresa instagram = new Empresa("Instagram");
	
	ReglaComparativa regla1;
	ReglaTaxativa regla2;
	Metodologia metodologiaDePrueba;
	
	@Before
	public void setUp() {
		//Cuentas necesarias
//		RepositorioCuentas.getInstance().limpiarRepositorio();
//		RepositorioIndicadores.getInstance().limpiarRepositorio();
		
		Cuenta cuenta0 = new Cuenta("EBITDA", facebook, "2008", new BigDecimal(2000));
		Cuenta cuenta1 = new Cuenta("EBITDA", twitter, "2008", new BigDecimal(1000));
		Cuenta cuenta2 = new Cuenta("EBITDA", instagram, "2008", new BigDecimal(1250));
		Cuenta cuenta3 = new Cuenta("EBITDA", facebook, "2009", new BigDecimal(4000));
		Cuenta cuenta4 = new Cuenta("EBITDA", twitter, "2010", new BigDecimal(2200));
		Cuenta cuenta5 = new Cuenta("EBITDA", instagram, "2009", new BigDecimal(2750));
		Cuenta cuenta6 = new Cuenta("FDS", facebook, "2008", new BigDecimal(20000));
		Cuenta cuenta7 = new Cuenta("FDS", twitter, "2008", new BigDecimal(16000));
		Cuenta cuenta8 = new Cuenta("FDS", instagram, "2008", new BigDecimal(28000));
		Cuenta cuenta9 = new Cuenta("FDS", facebook, "2009", new BigDecimal(14000));
		Cuenta cuenta10 = new Cuenta("FDS", twitter, "2010", new BigDecimal(40000));
		Cuenta cuenta11 = new Cuenta("FDS", instagram, "2009", new BigDecimal(70000));
		
		
		List<Cuenta> cuentasParaElRepositorio = new ArrayList<>();
		cuentasParaElRepositorio.add(cuenta0);
		cuentasParaElRepositorio.add(cuenta1);
		cuentasParaElRepositorio.add(cuenta2);
		cuentasParaElRepositorio.add(cuenta3);
		cuentasParaElRepositorio.add(cuenta4);
		cuentasParaElRepositorio.add(cuenta5);
		cuentasParaElRepositorio.add(cuenta6);
		cuentasParaElRepositorio.add(cuenta7);
		cuentasParaElRepositorio.add(cuenta8);
		cuentasParaElRepositorio.add(cuenta9);
		cuentasParaElRepositorio.add(cuenta10);
		cuentasParaElRepositorio.add(cuenta11);
		
		List<Empresa> empresas = new ArrayList<>();
		empresas.add(facebook);
		empresas.add(twitter);
		empresas.add(instagram);
		repositorioEmpresas = RepositorioEmpresas.getInstance();
		repositorioEmpresas.agregarEmpresas(empresas);
		repositorioCuentas = RepositorioCuentas.getInstance();
		repositorioCuentas.agregarCuentas(cuentasParaElRepositorio);
		
		List<String> nombresEmpresas = new ArrayList<>();
		nombresEmpresas.add("Facebook");
		nombresEmpresas.add("Twitter");
		nombresEmpresas.add("Instagram");
		
		//Indicadores necesarios
		Indicador indicador1 = new Indicador("Indicador1", "EBITDA + FDS");
		Indicador indicador2 = new Indicador("Indicador2", "FDS / EBITDA");
		// Indicador1 Facebook: (22000 + 18000) / 2 = 20000       / 
		// Indicador1 Twitter: (17000 + 42200) / 2 = 29600        / 
		// Indicador1 Instagram: (29250 + 72750) / 2 = 51000      / 
		// Indicador2 Facebook: (10 + 3.5) / 2 = 6.75             / 
		// Indicador2 Twitter: (16 + 18.1818) / 2 = 17.0909       / 
		// Indicador2 Instagram: (22.4 + 25.454545) / 2 = 23.9272 /
		List<Indicador> indicadores = new ArrayList<>();
		indicadores.add(indicador1);
		indicadores.add(indicador2);
		repositorioIndicadores = RepositorioIndicadores.getInstance();
		repositorioIndicadores.agregarIndicadores(indicadores);
		
		//Reglas necesarias
		regla1 = new ReglaComparativa("Regla1", indicador1, Criterio.MAYOR);
		regla2 = new ReglaTaxativa("Regla2", indicador2, '>', new BigDecimal(10));
		// Regla1, puntajes:
		// Facebook:  1
		// Twitter:   2
		// Instagram: 3
		// Regla2, conveniencia:
		// Facebook:  No conviene
		// Twitter:   Conviene
		// Instagram: Conviene
		
		List<Regla> reglas = new ArrayList<>();
		reglas.add(regla1);
		reglas.add(regla2);
		
		metodologiaDePrueba = new Metodologia("Metodologia de Prueba", reglas);
		// Resultado esperado para la metodología evaluada entre 2007 y 2018
		// Facebook: No conviene
		// Twitter: 66,67%
		// Instagram: 100%
		List<Metodologia> metodologias = new ArrayList<>();
		metodologias.add(metodologiaDePrueba);
		repositorioMetodologias = RepositorioMetodologias.getInstance();
		repositorioMetodologias.agregarMetodologias(metodologias);
		
		//Inicializacion de HashMaps y Listas
		empresasConPuntajesFinal = new LinkedHashMap<>();
		empresasEvaluadasConPuntajes = new LinkedHashMap<>();
		empresasEvaluadasConValoresDeIndicadores = new LinkedHashMap<>();
		empresasQueNoAplican = new ArrayList<>();
		empresasQueNoConvienen = new ArrayList<>();

		empresas = RepositorioEmpresas.getInstance().getAll();
		for (int i = 0; i < empresas.size(); i++) {
			empresasConPuntajesFinal.put(empresas.get(i).getNombre(), 0);
		}
	}

	@After	
	public void limpiarRepositorios() {
		repositorioCuentas.limpiarRepositorio();
		repositorioEmpresas.limpiarRepositorio();
		repositorioMetodologias.limpiarRepositorio();
		repositorioIndicadores.limpiarRepositorio();
	}

	@Test
	public void evaluarReglaComparativaParaEmpresa() {
		// Regla1, Facebook da 20000
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla1, facebook.getNombre(), empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, nombresEmpresas, 2007, 2018);
		BigDecimal valor = HashMapUtils.obtenerValorPorClave(empresasEvaluadasConValoresDeIndicadores, "Facebook");
		assertTrue(valor.compareTo(new BigDecimal(20000)) == 0);
	}
	
	@Test
	public void evaluarReglaTaxativaParaEmpresaQueNoConviene() {
		// Regla2, Facebook no conviene
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla2, "Facebook", empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, nombresEmpresas, 2007, 2018);
		BigDecimal valor = HashMapUtils.obtenerValorPorClave(empresasEvaluadasConValoresDeIndicadores, "Facebook");
		assertTrue(valor.compareTo(new BigDecimal(0)) == 0);
	}
	
	@Test
	public void evaluarReglaTaxativaParaEmpresaQueConviene() {
		// Regla2, Instagram conviene
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla2, "Instagram", empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, nombresEmpresas, 2007, 2018);
		BigDecimal valor = HashMapUtils.obtenerValorPorClave(empresasEvaluadasConValoresDeIndicadores, "Instagram");
		assertTrue(valor.compareTo(new BigDecimal(0)) == 1);
	}
	
	@Test
	public void evaluarReglaParaPeriodosEnLosQueNoAplica() {
		// Regla2, Instagram no tiene las Cuentas necesarias entre 2001 y 2002
		List<String> nombresEmpresas = new ArrayList<>();
		nombresEmpresas.add("Facebook");
		nombresEmpresas.add("Twitter");
		nombresEmpresas.add("Instagram");
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla2, "Instagram", empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, nombresEmpresas, 2001, 2002);
		assertTrue(empresasQueNoAplican.get(0).equals("Instagram"));
	}
	
	@Test
	public void evaluarMetodologia() {
		// Comparamos con los resultados arrojados en el calculo manual. 
		// Estan comentados arriba.
		List<EmpresaEvaluadaPorMetodologia> empresasEvaluadas = CalculadorDeMetodologias.getInstance().calcularMetodologia(metodologiaDePrueba, 2007, 2018);
		assertTrue((empresasEvaluadas.get(0).getNombreEmpresa().equals("Instagram") && empresasEvaluadas.get(0).getConveniencia().equals("100 %")) && (empresasEvaluadas.get(1).getNombreEmpresa().equals("Twitter") && empresasEvaluadas.get(1).getConveniencia().equals("66,67 %")) && (empresasEvaluadas.get(2).getNombreEmpresa().equals("Facebook") && empresasEvaluadas.get(2).getConveniencia().equals("No conviene")));
	}


}
