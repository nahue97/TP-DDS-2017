package useCases;

import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import model.repositories.RepositorioUsuarios;
import spark.Request;

public class LoginUseCases {
	
	
	public static Usuario authenticate(String username, String password) {
		List<Usuario> user = new ArrayList<>();
		if (username.isEmpty() || password.isEmpty())
			return null;
		Usuario usuario = new Usuario(username,password);
		user = RepositorioUsuarios.getInstance().searchByExample(usuario);
		if (!user.isEmpty())
			return user.get(0);
		return null;
	}
	
	public static Long getSession(Request request) {
		String tmp = request.session().attribute("currentUser");
		return tmp != null ? Long.parseLong(tmp) : 0L;
	}

	public static void startSession(Request request, Long idUser) {
		request.session().attribute("currentUser", String.valueOf(idUser));
	}

	public static void logout(Request request) {
		request.session().removeAttribute("currentUser");
	}

	public static boolean isLoginIN(Request request) {
		return request.session().attribute("currentUser") != null;
	}

}
