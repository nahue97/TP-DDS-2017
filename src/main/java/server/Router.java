package server;
import controllers.*;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	
	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();

		Spark.staticFiles.location("/public");
		Spark.port(9001);
		
		//Login
		Spark.get("/", LoginController::show, engine);
		Spark.post("/logout", LoginController::logout, engine);
		Spark.post("/login", LoginController::login, engine);
		//Home		
		Spark.get("/home", HomeController::home, engine);
		//Cuentas		
		Spark.get("/cuentas", CuentasController::listar, engine); //Acá vamos al clickear en "consulta" en la tab "Cuentas"
		Spark.get("/cuentas/listado", CuentasController::mostrar, engine); //Acá vamos al filtrar la lista de cuentas en la vista
		Spark.get("/cuentas/new", CuentasController::nuevo, engine); //Acá vamos al clickear en "carga" en la tab "Cuentas"
		Spark.post("/cuentas", CuentasController::crear, engine); //Acá vamos a entrar al cargar el archivo de cuentas
		//Indicadores
		Spark.get("/indicadores", IndicadoresController::listar, engine); //Acá vamos al clickear en "consulta" en la tab "Indicadores"
		Spark.get("/indicadores/listado", IndicadoresController::mostrar, engine); //Acá vamos al filtrar la lista de indicadores en la vista
		Spark.get("/indicadores/new", IndicadoresController::nuevo, engine); //Acá vamos al clickear en "carga" en la tab "Indicadores"
		Spark.post("/indicadores", IndicadoresController::crear, engine); //Acá vamos a entrar al cargar el indicador en la vista
		//Empresas
		Spark.get("/empresas", EmpresasController::listar, engine); //Acá vamos al clickear en "consulta" en la tab "Cuentas"
		Spark.get("/empresas/listado", EmpresasController::mostrar, engine); //Acá vamos al filtrar la lista de cuentas en la vista
		Spark.get("/empresas/new", EmpresasController::nuevo, engine); //Acá vamos al clickear en "carga" en la tab "Empresas"
		Spark.post("/empresas/new", EmpresasController::crear, engine); //Acá vamos a entrar al cargar la empresa en la vista
		//Metodologias
		Spark.get("/metodologias", MetodologiasController::listar, engine); //Acá vamos al clickear en "consulta" en la tab "Metodologias"
		Spark.get("/metodologias/listado", MetodologiasController::mostrar, engine); //Acá vamos al filtrar la lista de metodologias en la vista
	}
}
