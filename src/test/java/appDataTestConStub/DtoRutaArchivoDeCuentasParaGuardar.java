package appDataTestConStub;

import dtos.DTO;

public class DtoRutaArchivoDeCuentasParaGuardar implements DTO{

	String rutaCuentasParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeCuentasDeRepositorio.txt";
	
	public void setPathFile(String location) {
		rutaCuentasParaGuardar = location;
	}
	
	public String getPathFile() {
		return rutaCuentasParaGuardar;
	}
		
}