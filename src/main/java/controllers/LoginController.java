package controllers;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import useCases.LoginUseCases;
import model.Usuario;

public class LoginController {
	private static String usernameHBS = "username";
	private static String passwordHBS = "password";
	private static String authenticationFailedHBS = "authenticationFailed";
	
	public static ModelAndView show(Request req, Response res){
		if (LoginUseCases.isLoginIN(req))
			res.redirect("/home");
		
		return new ModelAndView(null, "login/login.hbs");
	}

	public static ModelAndView login (Request request, Response response){
		Map<String, Object> model = new HashMap<>();

		Usuario user = LoginUseCases.authenticate(request.queryParams(usernameHBS), request.queryParams(passwordHBS));

		if (user == null) {
			model.put(authenticationFailedHBS, true);
			return new ModelAndView(model, "login/login.hbs");
		}

		LoginUseCases.set(request, user.getId());

		response.redirect("/home");
		
		return null;
	};

	public static ModelAndView logout (Request request, Response response){
		LoginUseCases.logout(request);

		response.redirect("/");
		return null;
	};

	public static void verificarSesionIniciada(Request req, Response res) {
		if (!LoginUseCases.isLoginIN(req))
			res.redirect("/");
	};


}

