package tp1;

import model.repositories.RepositorioDeCuentas;
import utils.AppData;

import org.junit.Test;
import static org.junit.Assert.*;

import org.uqbar.commons.model.UserException;

public class AppDataTest {
	String rutaDelArchivoBueno="./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoMalo = "./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	
	
	@Test
	public void cargarCuentasDeArchivoBueno(){
		AppData.cargarCuentasDeArchivo(rutaDelArchivoBueno);
		RepositorioDeCuentas repositorio = RepositorioDeCuentas.getInstance();
		assertTrue(repositorio.size()==3);
	}
	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoInexistente(){
		AppData.cargarCuentasDeArchivo(rutaDeArchivoInexistente);
	}
	@Test(expected = UserException.class)
	public void cargarCuentasDeArchivoMalo(){
		AppData.cargarCuentasDeArchivo(rutaDeArchivoMalo);
	}
}
