package utils;

import model.RepositorioDeCuentas;

//Así haríamos el repositorio si lo quisieramos hacer singleton?

public class RepositorioDeCuentasSingleton extends RepositorioDeCuentas{
	private static RepositorioDeCuentas repositorio;
	
	private RepositorioDeCuentasSingleton() {
		super();
	}
	
	public static RepositorioDeCuentas getInstance(){
		if(repositorio.equals(null))
			repositorio = new RepositorioDeCuentas();
		return repositorio;
	}
	
}
