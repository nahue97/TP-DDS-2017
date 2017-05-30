package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import utils.JsonReader;
import org.uqbar.commons.model.UserException;
import dtos.CargaDeCuentasDTO;
import model.Cuenta;

public class FileProvider implements IProvider{
	
	public List<Cuenta> getInformation(CargaDeCuentasDTO datosDeCarga){
		
		String rutaDelArchivo = datosDeCarga.getPathFile();
		String json = leerArchivo(rutaDelArchivo);
		List <Cuenta> cuentas = JsonReader.obtenerCuentas(json);
		
		return cuentas;
	}
	
	public String leerArchivo(String rutaDelArchivo){
        String contenidoDelArchivo="", linea;
        FileReader f = null;
  
		try {
			f = new FileReader(rutaDelArchivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new UserException("Archivo no encontrado");
		}
		
        BufferedReader b = new BufferedReader(f);
        
		try {
			while((linea = b.readLine()) != null)
				contenidoDelArchivo += linea + '\n';
			} catch (IOException e) {
				e.printStackTrace();
				throw new UserException("Error en la lectura del archivo");
			}
		
		contenidoDelArchivo=contenidoDelArchivo.substring(0, contenidoDelArchivo.length()-1);
		
		try {
			b.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new UserException("Error al cerrar el archivo");
			}
		
		return contenidoDelArchivo;
	}
}
