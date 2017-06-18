package appDataTestConStub;

import dtos.CargaDeArchivoTxtJsonDTO;

public class DtoRutasArchivosStub extends CargaDeArchivoTxtJsonDTO{

	public DtoRutasArchivosStub(String pathFile) {
		super(pathFile);
		// TODO Auto-generated constructor stub
	}

	String ruta;
	
	String rutaDeCuentasBueno = "./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt";
	String rutaDeArchivoMalo = "./Archivos de prueba/ArchivoDeCuentasParaTestsMalo.txt";
	String rutaDeArchivoInexistente = "./Archivos de prueba/Necronomicon.txt";
	String rutaCuentasParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeCuentasDeRepositorio.txt";
	
	String rutaIndicadoresArchivoBueno = "./Archivos de prueba/ArchivoDeIndicadoresBueno.txt";
	String rutaIndicadoresParaGuardar = "./Archivos de prueba/TestsDeGrabacionDeIndicadoresDeRepositorio.txt";
	
	
	@Override
	public java.lang.String getPathFile() {

		return ruta;
	}
	
	public void setRutaDeCuentasBueno(){
		ruta = rutaDeCuentasBueno;
	}
	
	public void setRutaDeArchivoMalo(){
		ruta = rutaDeArchivoMalo;
	}
	
	public void setRutaDeArchivoInexistente(){
		ruta = rutaDeArchivoInexistente;
	}
	
	public void setRutaCuentasParaGuardar(){
		ruta = rutaCuentasParaGuardar;
	}

	public void setRutaIndicadoresArchivoBueno(){
		ruta = rutaIndicadoresArchivoBueno;
	}
	
	public void setRutaIndicadoresParaGuardar(){
		ruta = rutaIndicadoresParaGuardar;
	}
	
}
