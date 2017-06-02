package providers;

import java.util.ArrayList;
import java.util.List;
import utils.JsonReader;
import utils.ManejoDeArchivos;
import dtos.DTO;
import model.Cuenta;
import model.Indicador;

public class FileProvider implements IProviderCuenta, IProviderIndicador{
	
	public List<Cuenta> getInformationCuentas(DTO datosDeCarga){
		String rutaDelArchivo = datosDeCarga.getPathFile();
		String json = ManejoDeArchivos.leerArchivo(rutaDelArchivo);
		List <Cuenta> cuentas = JsonReader.obtenerCuentas(json);
		
		return cuentas;
	}
	
	public List<Indicador> getInformationIndicador(DTO datosDeCarga){
		//String rutaDelArchivo = datosDeCarga.getPathFile();
		//String json = ManejoDeArchivos.leerArchivo(rutaDelArchivo);
		List <Indicador> indicadores = new ArrayList<>();
		
		return indicadores;
	}
	
}
