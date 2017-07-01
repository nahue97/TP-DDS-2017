package appDataTestConStub;

import dtos.PathFile;

public class RutaArchivoIndicadoresBueno implements PathFile{

	String rutaIndicadoresArchivoBueno = "./Archivos de prueba/ArchivoDeIndicadoresBueno.txt";
	
	public void setPathFile(String location) {
		rutaIndicadoresArchivoBueno = location;
	}
	
	public String getPathFile() {
		return rutaIndicadoresArchivoBueno;
	}
		
}