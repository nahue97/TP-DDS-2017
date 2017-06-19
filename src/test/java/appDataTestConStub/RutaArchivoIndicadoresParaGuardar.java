package appDataTestConStub;

import dtos.PathFile;

public class RutaArchivoIndicadoresParaGuardar implements PathFile{

	String rutaIndicadoresParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeIndicadoresDeRepositorio.txt";
	
	public void setPathFile(String location) {
		rutaIndicadoresParaGuardar = location;
	}
	
	public String getPathFile() {
		return rutaIndicadoresParaGuardar;
	}
		
}