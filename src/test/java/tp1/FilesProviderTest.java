package tp1;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.uqbar.commons.model.UserException;

import dtos.PathFileTxtJson;
import dtos.PathFile;
import model.Cuenta;
import providers.FileProvider;

//No cambiar los archivos para test ya que los tests van a fallar

public class FilesProviderTest {
	String rutaDelArchivoBueno = "./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";

	FileProvider fileProvider = new FileProvider();

	PathFile datosDeCarga = new PathFileTxtJson("");

	@Test
	public void leerArchivo() {
		datosDeCarga.setPathFile(rutaDelArchivoBueno);
		List<Cuenta> cuentas = fileProvider.getInformationCuentas(datosDeCarga);

		assertTrue(cuentas.size() == 1 && cuentas.get(0).getPeriodo().equals("2016"));
	}

	@Test(expected = UserException.class)
	public void leerArchivoInexistente() {
		datosDeCarga.setPathFile(rutaDeArchivoInexistente);
		fileProvider.getInformationCuentas(datosDeCarga);
	}
}