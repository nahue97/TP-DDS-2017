package server;

import java.util.Arrays;
import java.util.List;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dtos.PathFileTxtJson;
import model.Usuario;
import model.repositories.RepositorioUsuarios;
import utils.AppData;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	
	private static PathFileTxtJson dtoEmpresas = new PathFileTxtJson("./Archivos de la App/Database Empresas.txt");
	private static PathFileTxtJson dtoCuentas = new PathFileTxtJson("./Archivos de la App/Database Cuentas.txt");
	private static PathFileTxtJson dtoIndicadores = new PathFileTxtJson("./Archivos de la App/Database Indicadores.txt");
	
	public static void main(String[] args) {
		init();
	}
	
	public static void init(){
		inicializarDatos();
		List<Usuario> usuarios = usuarios();
		usuarios.forEach((usuario) -> RepositorioUsuarios.getInstance().registrar(usuario));

	}
	
	private static void inicializarDatos() {
		AppData.getInstance().setInicializacionDeEmpresas(dtoEmpresas);
		AppData.getInstance().setInicializacionDeCuentas(dtoCuentas);
		AppData.getInstance().setInicializacionDeIndicadores(dtoIndicadores);
		AppData.getInstance().inicializarRepositorios();
		
		List<Usuario> usuarios = usuarios();
		usuarios.forEach((usuario) -> RepositorioUsuarios.getInstance().registrar(usuario));
	}
	
	private static List<Usuario> usuarios() {
		return Arrays.asList(new Usuario("ale", "ale"),
		new Usuario("hector", "sarlanga"));
	}
	
}
