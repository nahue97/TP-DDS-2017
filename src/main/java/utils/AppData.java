package utils;

import java.util.ArrayList;

import dtos.CargaDeCuentasDTO;
import model.repositories.RepositorioCarpeta;

public class AppData {
	private RepositorioCarpeta repositorio;
	private ArrayList<IProvider> providers;
	private static AppData instance;
	
	private AppData(){
		providers = new ArrayList<IProvider>();
		providers.add(new FileProvider());
		repositorio = RepositorioCarpeta.getInstance();
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
	public RepositorioCarpeta getRepositorio(){
		return repositorio;
	}

	public void cargarIndicadores(String formula, String nombre) {
		// TODO Auto-generated method stub
		
	}
}
