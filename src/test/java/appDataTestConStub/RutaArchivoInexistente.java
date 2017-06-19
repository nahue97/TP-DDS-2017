package appDataTestConStub;

import dtos.PathFile;

public class RutaArchivoInexistente implements PathFile{

	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	
	public void setPathFile(String location) {
		rutaDeArchivoInexistente = location;
	}
	
	public String getPathFile() {
		return rutaDeArchivoInexistente;
	}
		
}