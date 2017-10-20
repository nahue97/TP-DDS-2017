package utils;

import spark.*;

public class RequestUtil {

	public static class currentUser {
		public static Long get(Request request) {
			String tmp = request.session().attribute("currentUser");
			return tmp != null ? Long.parseLong(tmp) : 0L;
		}

		public static void set(Request request, Long idUser) {
			request.session().attribute("currentUser", String.valueOf(idUser));
		}

		public static void remove(Request request) {
			request.session().removeAttribute("currentUser");
		}

		public static boolean isSet(Request request) {
			return request.session().attribute("currentUser") != null;
		}
	}

	public static class getString {
		public static String get(Request request, String string){
			String ret = request.queryParams(string);
			if (ret == null)
				ret = "";
			return ret;
		}
	}
	
/*	public static class username {
		public static String get(Request request) {
			String user = request.queryParams("username");
			if (user == null)
				user = "";
			return user;
		}
	}

	public static class password {
		public static String get(Request request) {
			String pass = request.queryParams("password");
			if (pass == null)
				pass = "";
			return pass;
		}
	}
	
	public static class empresa {
		public static String get(Request request) {
			String emp = request.queryParams("empresa");
			if (emp == null)
				emp = "";
			return emp;
		}
	}
*/
	public static boolean clientAcceptsHtml(Request request) {
		String accept = request.headers("Accept");
		return accept != null && accept.contains("text/html");
	}

	public static boolean clientAcceptsJson(Request request) {
		String accept = request.headers("Accept");
		return accept != null && accept.contains("application/json");
	}

}
