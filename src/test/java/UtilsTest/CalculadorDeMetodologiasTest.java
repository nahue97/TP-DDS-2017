package UtilsTest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import model.Criterio;
import model.Cuenta;
import model.EmpresaEvaluadaPorMetodologia;
import model.Indicador;
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import utils.CalculadorDeMetodologias;
import utils.HashMapUtils;

public class CalculadorDeMetodologiasTest {
	
	RepositorioCuentas repositorioCuentas;
	RepositorioIndicadores repositorioIndicadores;
	LinkedHashMap<String, Integer> empresasConPuntajesFinal;
	LinkedHashMap<String, BigDecimal> empresasEvaluadasConValoresDeIndicadores;
	LinkedHashMap<String, Integer> empresasEvaluadasConPuntajes;
	List<String> empresasQueNoAplican;
	List<String> empresasQueNoConvienen;
	List<String> empresas;
	
	ReglaComparativa regla1;
	ReglaTaxativa regla2;
	Metodologia metodologiaDePrueba;
	
	@Before
	public void setUp() {
		//Cuentas necesarias
		RepositorioCuentas.getInstance().limpiarRepositorio();
		RepositorioIndicadores.getInstance().limpiarRepositorio();
		Cuenta cuenta0 = new Cuenta(0, "EBITDA", "Facebook", "2008", new BigDecimal(2000));
		Cuenta cuenta1 = new Cuenta(1, "EBITDA", "Twitter", "2008", new BigDecimal(1000));
		Cuenta cuenta2 = new Cuenta(2, "EBITDA", "Instagram", "2008", new BigDecimal(1250));
		Cuenta cuenta3 = new Cuenta(3, "EBITDA", "Facebook", "2009", new BigDecimal(4000));
		Cuenta cuenta4 = new Cuenta(4, "EBITDA", "Twitter", "2010", new BigDecimal(2200));
		Cuenta cuenta5 = new Cuenta(5, "EBITDA", "Instagram", "2009", new BigDecimal(2750));
		Cuenta cuenta6 = new Cuenta(6, "FDS", "Facebook", "2008", new BigDecimal(20000));
		Cuenta cuenta7 = new Cuenta(7, "FDS", "Twitter", "2008", new BigDecimal(16000));
		Cuenta cuenta8 = new Cuenta(8, "FDS", "Instagram", "2008", new BigDecimal(28000));
		Cuenta cuenta9 = new Cuenta(9, "FDS", "Facebook", "2009", new BigDecimal(14000));
		Cuenta cuenta10 = new Cuenta(10, "FDS", "Twitter", "2010", new BigDecimal(40000));
		Cuenta cuenta11 = new Cuenta(11, "FDS", "Instagram", "2009", new BigDecimal(70000));
		
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
		
		repositorioCuentas = RepositorioCuentas.getInstance();
		repositorioCuentas.agregarCuentas(cuentasParaElRepositorio);
		
		
		//Indicadores necesarios
		Indicador indicador1 = new Indicador("Indicador1", "EBITDA + FDS");
		Indicador indicador2 = new Indicador("Indicador2", "FDS / EBITDA");
		// Indicador1 Facebook: (22000 + 18000) / 2 = 20000       / 
		// Indicador1 Twitter: (17000 + 42200) / 2 = 29600        / 
		// Indicador1 Instagram: (29250 + 72750) / 2 = 51000      / 
		// Indicador2 Facebook: (10 + 3.5) / 2 = 6.75             / 
		// Indicador2 Twitter: (16 + 18.1818) / 2 = 17.0909       / 
		// Indicador2 Instagram: (22.4 + 25.454545) / 2 = 23.9272 / 
		
		
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
				
		
		//Inicializacion de HashMaps y Listas
		empresasConPuntajesFinal = new LinkedHashMap<>();
		empresasEvaluadasConPuntajes = new LinkedHashMap<>();
		empresasEvaluadasConValoresDeIndicadores = new LinkedHashMap<>();
		empresasQueNoAplican = new ArrayList<>();
		empresasQueNoConvienen = new ArrayList<>();
		empresas = RepositorioCuentas.getInstance().getEmpresasDeCuentas();
		
		
		for (int i = 0; i < empresas.size(); i++) {
			empresasConPuntajesFinal.put(empresas.get(i), 0);
		}
	}

	@Test
	public void evaluarReglaComparativaParaEmpresa() {
		// Regla1, Facebook da 20000
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla1, "Facebook", empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, empresas, 2007, 2018);
		BigDecimal valor = HashMapUtils.obtenerValorPorClave(empresasEvaluadasConValoresDeIndicadores, "Facebook");
		assertTrue(valor.compareTo(new BigDecimal(20000)) == 0);
	}
	
	@Test
	public void evaluarReglaTaxativaParaEmpresaQueNoConviene() {
		// Regla2, Facebook no conviene
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla2, "Facebook", empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, empresas, 2007, 2018);
		BigDecimal valor = HashMapUtils.obtenerValorPorClave(empresasEvaluadasConValoresDeIndicadores, "Facebook");
		assertTrue(valor.compareTo(new BigDecimal(0)) == 0);
	}
	
	@Test
	public void evaluarReglaTaxativaParaEmpresaQueConviene() {
		// Regla2, Instagram conviene
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla2, "Instagram", empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, empresas, 2007, 2018);
		BigDecimal valor = HashMapUtils.obtenerValorPorClave(empresasEvaluadasConValoresDeIndicadores, "Instagram");
		assertTrue(valor.compareTo(new BigDecimal(0)) == 1);
	}
	
	@Test
	public void evaluarReglaParaPeriodosEnLosQueNoAplica() {
		// Regla2, Instagram no tiene las Cuentas necesarias entre 2001 y 2002
		CalculadorDeMetodologias.getInstance().evaluarReglaParaEmpresa(regla2, "Instagram", empresasConPuntajesFinal, empresasQueNoAplican, empresasEvaluadasConValoresDeIndicadores, empresas, 2001, 2002);
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
