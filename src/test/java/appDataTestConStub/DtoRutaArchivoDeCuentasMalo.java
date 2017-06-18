package appDataTestConStub;

import dtos.DTO;

public class DtoRutaArchivoDeCuentasMalo implements DTO{

	String rutaDeArchivoMalo = "./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt";
	
	public void setPathFile(String location) {
		rutaDeArchivoMalo = location;
	}
	
	public String getPathFile() {
		return rutaDeArchivoMalo;
	}
	
}