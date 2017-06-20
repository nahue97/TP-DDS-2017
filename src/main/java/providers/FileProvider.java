package providers;

import java.util.List;
import utils.JsonReader;
import utils.ManejoDeArchivos;
import dtos.PathFile;
import model.Cuenta;
import model.Indicador;

public class FileProvider implements IProviderCuenta, IProviderIndicador{
	
	public List<Cuenta> getInformationCuentas(PathFile datosDeCarga){
		String rutaDelArchivo = datosDeCarga.getPathFile();
		String json = ManejoDeArchivos.leerArchivo(rutaDelArchivo);
		List <Cuenta> cuentas = JsonReader.obtenerCuentas(json);
		
		return cuentas;
	}
	
	public List<Indicador> getInformationIndicador(PathFile datosDeCarga){
		String rutaDelArchivo = datosDeCarga.getPathFile();
		String json = ManejoDeArchivos.leerArchivo(rutaDelArchivo);
		List <Indicador> indicadores = JsonReader.obtenerIndicadores(json);
		
		return indicadores;
	}
	
}
