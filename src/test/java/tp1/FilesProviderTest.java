package tp1;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import dtos.CargaDeCuentasDTO;
import dtos.DTO;
import model.Cuenta;
import providers.FileProvider;

//No cambiar los archivos para test ya que los tests van a fallar

public class FilesProviderTest{
	String rutaDelArchivoBueno="./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	
	FileProvider fileProvider = new FileProvider();
	
	DTO datosDeCarga = new CargaDeCuentasDTO();
	
	@Test
	public void crearElArchivo(){
		datosDeCarga.setPathFile(rutaDelArchivoBueno);
		List<Cuenta> cuentas = fileProvider.getInformationCuentas(datosDeCarga);
		
		assertTrue(cuentas.size()==3);
	}
}