package controllers;

import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import model.repositories.RepositorioUsuarios;
import scala.Array;

public class UserController {

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

}
