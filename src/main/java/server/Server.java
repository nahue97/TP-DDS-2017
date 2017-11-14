package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Bootstrap.init();
		Spark.stop();
		DebugScreen.enableDebugScreen();
		Router.configure();
	}

}