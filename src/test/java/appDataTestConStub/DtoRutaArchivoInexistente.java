package appDataTestConStub;

import dtos.DTO;

public class DtoRutaArchivoInexistente implements DTO{

	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	
	public void setPathFile(String location) {
		rutaDeArchivoInexistente = location;
	}
	
	public String getPathFile() {
		return rutaDeArchivoInexistente;
	}
		
}