package controllers;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.RequestUtil.*;
import model.Usuario;

public class LoginController {
	
	public static ModelAndView show(Request req, Response res){
		//Map<String, Object> model = new HashMap<>();

		if (currentUser.isSet(req))
			res.redirect("/");
		
		return new ModelAndView(null, "login/login.hbs");
	}
	
/*	public static ModelAndView login (Request request, Response response){
		Map<String, Object> model = new HashMap<>();

		if (username.get(request).equals("Hector") && password.get(request).equals("sarlanga2017")){
			model.put("authenticationFailed", true);
			return new ModelAndView(null, "login/login.hbs");
		}

		currentUser.set(request, 0L); //Acá va a ir el id long del user en un futuro

		response.redirect("home/home.hbs");
		return null;
	};
*/

	public static ModelAndView login (Request request, Response response){
		Map<String, Object> model = new HashMap<>();

		Usuario user = UserController.authenticate(getString.get(request,"username"), getString.get(request,"password"));

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

		response.redirect("/login");
		return null;
	};
/*
	public static void ensureUserIsLoggedIn(Request request, Response response) {
		if (!currentUser.isSet(request))
			response.redirect("login/login.hbs");
	};
*/

}

