package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.uqbar.commons.model.UserException;

import ExceptionsPackage.RutaDeArchivoInvalidaException;

public class ManejoDeArchivos {

	public static String leerArchivo(String path) {
		String contenidoDelArchivo = "";
		int caracter;
		FileReader f = null;

		try {
			f = new FileReader(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new UserException("Archivo no encontrado");
		}

		BufferedReader b = new BufferedReader(f);

		try {
			while ((caracter = b.read()) != -1)
				contenidoDelArchivo += (char) caracter;
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

	// Recibe un String y sobreescribe (o crea) todo el archivo
	public static void sobreescribirArchivo(String path, String stringAGuardar) {
		FileWriter file = null;

		try {
			file = new FileWriter(path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RutaDeArchivoInvalidaException("Ruta de archivo inválida");
		}
		realizarLaGrabacion(file, stringAGuardar);
	}

	// Borrar un archivo para los test

	public static void borrarArchivo(String path) {
		File file = new File(path);
		file.delete();
	}

	private static void realizarLaGrabacion(FileWriter f, String stringAGuardar) {
		PrintWriter pw = new PrintWriter(f);
		char caracteres[] = stringAGuardar.toCharArray();

		for (char caracter : caracteres)
			pw.print(caracter);

		try {
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Error al guardar el archivo");
		}
	}
}
