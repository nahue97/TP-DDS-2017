package server;

import model.repositories.RepositorioIndicadoresCalculados;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		RepositorioIndicadoresCalculados.getInstance();
		Bootstrap.init();
		Spark.stop();
		DebugScreen.enableDebugScreen();
		Router.configure();
	}

}