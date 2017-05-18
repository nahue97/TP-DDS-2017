package utils;

import java.util.ArrayList;

import dtos.CargaDeCuentasDTO;
import model.repositories.RepositorioDeCuentas;

public class AppData {
	private RepositorioDeCuentas repositorio;
	private ArrayList<IProvider> providers;
	private static AppData instance;
	
	private AppData(){
		providers = new ArrayList<IProvider>();
		providers.add(new FileProvider());
		repositorio = RepositorioDeCuentas.getInstance();
	}
	
	public static synchronized AppData getInstance(){
		if (instance == null){
			instance = new AppData();
		}
		return instance;
	}
	
	public void cargarCuentas(CargaDeCuentasDTO datosDeCarga){
		providers.forEach(proveedor ->
			repositorio.agregarCuentas(proveedor.getInformation(datosDeCarga)));	
	}

//Para Test de AppData
	public RepositorioDeCuentas getRepositorio(){
		return repositorio;
	}
}
