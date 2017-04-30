package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exceptions.ArchivoNoEncontradoException;

public class JsonFileReader {
	
	public static String leerArchivo(String rutaDelArchivo){
        String contenidoDelArchivo="", linea;
        FileReader f = null;
        
		try {
			f = new FileReader(rutaDelArchivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ArchivoNoEncontradoException("Archivo no encontrado");
		}
		
        BufferedReader b = new BufferedReader(f);
        
			try {
				while((linea = b.readLine()) != null)contenidoDelArchivo += linea;
			} catch (IOException e) {
				e.printStackTrace();
				throw new Error("Error en la lectura del archivo");
			}
			
			try {
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Error("Error al cerrar el archivo");
			}
		
			return contenidoDelArchivo;
	}
}
