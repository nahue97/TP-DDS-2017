package model.repositories;

//Pusimos el sigleton acá para que sea más facil testear el repositorio de cuentas

//Se encarga de darle a todas las clases una instancia del repositorio de cuentas

public class Repositorios {
	
	private static RepositorioDeCuentas repositorio; 
	
	public static synchronized RepositorioDeCuentas getInstanceRepositorioDeCuentas(){
		if(repositorio == null)
			repositorio = new RepositorioDeCuentas();
		return repositorio;
	}

}
