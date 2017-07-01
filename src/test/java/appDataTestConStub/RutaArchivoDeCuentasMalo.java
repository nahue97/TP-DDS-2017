package appDataTestConStub;

import dtos.PathFile;

public class RutaArchivoDeCuentasMalo implements PathFile{

	String rutaDeArchivoMalo = "./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt";
	
	public void setPathFile(String location) {
		rutaDeArchivoMalo = location;
	}
	
	public String getPathFile() {
		return rutaDeArchivoMalo;
	}
	
}