package tp1;

import utils.AppData;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.uqbar.commons.model.UserException;
import dtos.CargaDeCuentasDTO;
import dtos.IndicadoresDTO;
import model.repositories.RepositorioCarpeta;

public class AppDataTest {
	String rutaDeCuentasBueno = "./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoMalo = "./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	String rutaCuentasParaGuardar ="./Archivos de prueba/TestsDeGrabacionDeCuentasDeRepositorio.txt";
	String rutaIndicadoresParaGuardar ="./Archivos de prueba/TestsDeGrabacionDeIndicadoresDeRepositorio.txt";
	
	CargaDeCuentasDTO datosDeCarga = new CargaDeCuentasDTO();
	AppData appData = AppData.getInstance();
	private static CargaDeCuentasDTO dtoCuentasALeer = new CargaDeCuentasDTO();
	private static IndicadoresDTO dtoIndicadoresALeer = new IndicadoresDTO();
	private static CargaDeCuentasDTO dtoCuentasParaGuardar = new CargaDeCuentasDTO();
	private static IndicadoresDTO dtoIndicadoresParaGuardar = new IndicadoresDTO();
	
	@Before
	public void setUp(){
		dtoCuentasParaGuardar.setPathFile(rutaCuentasParaGuardar);
		dtoIndicadoresParaGuardar.setPathFile(rutaIndicadoresParaGuardar);
		
		RepositorioCarpeta.setDtoCuentas(dtoCuentasParaGuardar);
		RepositorioCarpeta.setDtoIndicadores(dtoIndicadoresParaGuardar);
		
		dtoCuentasALeer.setPathFile(rutaDeCuentasBueno);
		//dtoIndicadoresALeer.setPathFile(rutaDeIndicadoresBueno);
		
		appData.setInicializacionDeCuentas(dtoCuentasALeer);
		appData.setInicializacionDeIndicadores(dtoIndicadoresALeer);
	}
	
	@Test
	public void cargarCuentasDeArchivoBueno(){
		datosDeCarga.setPathFile(rutaDeCuentasBueno);
		
		appData.cargarCuentas(datosDeCarga);
		RepositorioCarpeta repositorio = RepositorioCarpeta.getInstance();
		
		assertTrue(repositorio.size()==5);
	}
	/*@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoInexistente(){
		datosDeCarga.setPathFile(rutaDeArchivoInexistente);
		AppData.getInstance().cargarCuentas(datosDeCarga);
	}
	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoMalo(){
		datosDeCarga.setPathFile(rutaDeArchivoMalo);
		AppData.getInstance().cargarCuentas(datosDeCarga);
	}*/
}
