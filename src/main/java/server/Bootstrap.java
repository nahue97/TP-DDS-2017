package server;

import java.util.Arrays;
import java.util.List;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import model.Usuario;
import model.repositories.RepositorioUsuarios;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps{
	
	public static void main(String[] args) {
		new Bootstrap().init();
	}
	
	public static void init(){
		
		List<Usuario> usuarios = usuarios();
		usuarios.forEach((usuario) -> RepositorioUsuarios.getInstance().registrar(usuario));

	}
	
	private static List<Usuario> usuarios() {
		return Arrays.asList(new Usuario("ale", "ale"),
		new Usuario("hector", "sarlanga"));
	}
	
}
