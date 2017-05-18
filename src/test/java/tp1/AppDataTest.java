package tp1;

import utils.AppData;
import org.junit.Test;
import static org.junit.Assert.*;
import org.uqbar.commons.model.UserException;
import dtos.CargaDeCuentasDTO;

public class AppDataTest {
	String rutaDelArchivoBueno="./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoMalo = "./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	CargaDeCuentasDTO datosDeCarga = new CargaDeCuentasDTO();
	
	
	@Test
	public void cargarCuentasDeArchivoBueno(){
		datosDeCarga.setPathFile(rutaDelArchivoBueno);
		AppData appData = AppData.getInstance();
		appData.cargarCuentas(datosDeCarga);
		assertTrue(appData.getRepositorio().size()==3);
	}
	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoInexistente(){
		datosDeCarga.setPathFile(rutaDeArchivoInexistente);
		AppData.getInstance().cargarCuentas(datosDeCarga);
	}
	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoMalo(){
		datosDeCarga.setPathFile(rutaDeArchivoMalo);
		AppData.getInstance().cargarCuentas(datosDeCarga);
	}
}
