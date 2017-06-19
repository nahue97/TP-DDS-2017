package appDataTestConStub;

import dtos.PathFile;

public class RutaArchivoDeCuentasBueno implements PathFile{

	String rutaDeCuentasBueno = "./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	
	public void setPathFile(String location) {
		rutaDeCuentasBueno = location;
	}
	
	public String getPathFile() {
		return rutaDeCuentasBueno;
	}
	
}