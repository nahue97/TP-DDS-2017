package server;

import java.util.Arrays;
import java.util.List;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import batchProcesses.FileUpload;
import dtos.PathFile;
import dtos.PathFileTxtJson;
import model.Usuario;
import model.repositories.RepositorioUsuarios;
import utils.AppData;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	
	private static PathFileTxtJson dtoEmpresas = new PathFileTxtJson("./Archivos de la App/Database Empresas.txt");
	private static PathFileTxtJson dtoCuentas = new PathFileTxtJson("./Archivos de la App/Database Cuentas.txt");
	private static PathFileTxtJson dtoIndicadoresDeAle = new PathFileTxtJson("./Archivos de la App/Database Indicadores de Ale.txt");
	private static PathFileTxtJson dtoIndicadoresDeHector = new PathFileTxtJson("./Archivos de la App/Database Indicadores de Hector.txt");
	
	public static void main(String[] args) {
		init();
	}
	
	public static void init(){
		inicializarUsuarios();
		inicializarDatos();
		inicializarFileUpload();
	}
	
	private static void inicializarDatos() {
		List<Usuario> usuarios =  Arrays.asList(RepositorioUsuarios.getInstance().getAll().get(0),
												RepositorioUsuarios.getInstance().getAll().get(1));
		List<PathFile> dtoIndicadoresDeUsuario = Arrays.asList(dtoIndicadoresDeAle,dtoIndicadoresDeHector);
		AppData.getInstance().setInicializacionDeEmpresas(dtoEmpresas);
		AppData.getInstance().setInicializacionDeCuentas(dtoCuentas);
		AppData.getInstance().inicializarRepositoriosConUsuarios(usuarios, dtoIndicadoresDeUsuario);
	}
	
	private static void inicializarUsuarios() {
		
		Usuario usuarioAle = new Usuario("ale", "ale");
		Usuario usuarioHector = new Usuario("hector", "sarlanga");
		List<Usuario> usuarios =  Arrays.asList( usuarioAle, usuarioHector);
		
		usuarios.forEach((usuario) -> RepositorioUsuarios.getInstance().registrar(usuario));
	}
	
	  public static void inicializarFileUpload() {
		  	FileUpload fileUpload = new FileUpload();
		  	fileUpload.init();
		  }
	
}
