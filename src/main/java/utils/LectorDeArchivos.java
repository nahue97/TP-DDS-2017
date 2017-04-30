package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import model.Cuenta;

public class LectorDeArchivos {
	
    public static List<Cuenta> obtenerCuentas(String rutaDelArchivo){
        String cadena;
        List<Cuenta> cuentas = new ArrayList<>();
        Gson gson = new Gson();
        FileReader f = null;
		try {
			f = new FileReader(rutaDelArchivo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        BufferedReader b = new BufferedReader(f);
        
        try {
			while((cadena = b.readLine())!=null) {
				Type listType = new TypeToken<List<Cuenta>>(){}.getType();
			    cuentas = gson.fromJson(cadena, listType);
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return cuentas;
	}

}