package appDataTestConStub;

import dtos.PathFile;

public class RutaArchivoIndicadoresMalo implements PathFile{

	String rutaIndicadoresArchivoMalo = "./Archivos de prueba/ArchivoDeIndicadoresMalo.txt";
	
	public void setPathFile(String location) {
		rutaIndicadoresArchivoMalo = location;
	}
	
	public String getPathFile() {
		return rutaIndicadoresArchivoMalo;
	}
	
}