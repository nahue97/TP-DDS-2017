package controllers;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.RequestUtil.*;
import model.Usuario;

public class LoginController {
	
	public static ModelAndView show(Request req, Response res){
		if (currentUser.isSet(req))
			res.redirect("/home");
		
		return new ModelAndView(null, "login/login.hbs");
	}

	public static ModelAndView login (Request request, Response response){
		Map<String, Object> model = new HashMap<>();

		Usuario user = UserController.authenticate(username.get(request), password.get(request));

		if (user == null) {
			model.put("authenticationFailed", true);
			return new ModelAndView(model, "login/login.hbs");
		}

		currentUser.set(request, user.getId());

		response.redirect("/home");
		return null;
	};

	public static ModelAndView logout (Request request, Response response){
		currentUser.remove(request);

		response.redirect("/");
		return null;
	};

	public static void verificarSesionIniciada(Request req, Response res) {
		if (!currentUser.isSet(req))
			res.redirect("/");
	};


}

