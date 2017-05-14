package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.uqbar.commons.model.UserException;

public class FilesReader {
	
	public static String leerArchivo(String rutaDelArchivo){
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
				while((linea = b.readLine()) != null)contenidoDelArchivo += linea;
			} catch (IOException e) {
				e.printStackTrace();
				throw new UserException("Error en la lectura del archivo");
			}
			
			try {
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new UserException("Error al cerrar el archivo");
			}
		
			return contenidoDelArchivo;
	}
}
