package appDataTestConStub;

import dtos.DTO;

public class DtoRutaArchivoIndicadoresBueno implements DTO{

	String rutaIndicadoresArchivoBueno = "./Archivos de prueba/ArchivoDeIndicadoresBueno.txt";
	
	public void setPathFile(String location) {
		rutaIndicadoresArchivoBueno = location;
	}
	
	public String getPathFile() {
		return rutaIndicadoresArchivoBueno;
	}
		
}