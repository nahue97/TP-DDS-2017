package appDataTestConStub;

import dtos.PathFile;

public class RutaArchivoDeCuentasParaGuardar implements PathFile{

	String rutaCuentasParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeCuentasDeRepositorio.txt";
	
	public void setPathFile(String location) {
		rutaCuentasParaGuardar = location;
	}
	
	public String getPathFile() {
		return rutaCuentasParaGuardar;
	}
		
}