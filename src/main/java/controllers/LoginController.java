package controllers;

import java.util.HashMap;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.*;
import utils.RequestUtil.*;

public class LoginController {
	
	public static ModelAndView show(Request req, Response res){
		//Map<String, Object> model = new HashMap<>();

		if (currentUser.isSet(req))
			res.redirect("home/home.hbs");
		
		return new ModelAndView(null, "login/login.hbs");
	}
	
	public static ModelAndView login (Request request, Response response){
		Map<String, Object> model = new HashMap<>();

		if (username.get(request).equals("Hector") && password.get(request).equals("sarlanga2017")){
			model.put("authenticationFailed", true);
			return new ModelAndView(null, "login/login.hbs");
		}

		currentUser.set(request, 0L); //Acá va a ir el id long del user en un futuro

		response.redirect("home/home.hbs");
		return null;
	};

}

