package providers;

import java.util.List;
import utils.JsonReader;
import utils.ManejoDeArchivos;
import dtos.PathFile;
import model.Cuenta;
import model.Empresa;
import model.Indicador;
import model.repositories.RepositorioEmpresas;

public class FileProvider implements IProviderCuenta, IProviderIndicador, IProviderEmpresa{
	
	public List<Cuenta> getInformationCuentas(PathFile datosDeCarga){
		String rutaDelArchivo = datosDeCarga.getPathFile();
		String json = ManejoDeArchivos.leerArchivo(rutaDelArchivo);
		List <Cuenta> cuentas = JsonReader.obtenerCuentas(json);
		cuentas.forEach(c -> c.setEmpresa(RepositorioEmpresas.getInstance().obtenerPorNombreODevolverNueva(c.getEmpresa().getNombre())));
		
		return cuentas;
	}
	
	public List<Indicador> getInformationIndicador(PathFile datosDeCarga){
		String rutaDelArchivo = datosDeCarga.getPathFile();
		String json = ManejoDeArchivos.leerArchivo(rutaDelArchivo);
		List <Indicador> indicadores = JsonReader.obtenerIndicadores(json);
		
		return indicadores;
	}
	

	@Override
	public List<Empresa> getInformationEmpresas(PathFile datosDeCarga) {
		String rutaDelArchivo = datosDeCarga.getPathFile();
		String json = ManejoDeArchivos.leerArchivo(rutaDelArchivo);
		List <Empresa> empresas = JsonReader.obtenerEmpresas(json);
		
		return empresas;
	}
	
}
