package appDataTestConStub;

import dtos.DTO;

public class DtoRutaArchivoDeCuentasBueno implements DTO{

	String rutaDeCuentasBueno = "./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	
	public void setPathFile(String location) {
		rutaDeCuentasBueno = location;
	}
	
	public String getPathFile() {
		return rutaDeCuentasBueno;
	}
	
}