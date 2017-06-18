package appDataTestConStub;

import dtos.DTO;

public class DtoRutaArchivoIndicadoresParaGuardar implements DTO{

	String rutaIndicadoresParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeIndicadoresDeRepositorio.txt";
	
	public void setPathFile(String location) {
		rutaIndicadoresParaGuardar = location;
	}
	
	public String getPathFile() {
		return rutaIndicadoresParaGuardar;
	}
		
}