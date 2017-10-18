package server;

import controllers.LoginController;
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
		
		LoginController proyectosController = new LoginController();
		
		Spark.get("/", LoginController::login, engine);
		Spark.get("/home", HomeController::home, engine);
		//Cuentas		
		Spark.get("/cuentas", cuentasController::listar, engine); //Acá vamos al clickear en "consulta" en la tab "Cuentas"
		Spark.get("/cuentas/listado", cuentasController::mostrar, engine); //Acá vamos al filtrar la lista de cuentas en la vista
		Spark.get("/cuentas/new", cuentasController::nuevo, engine); //Acá vamos al clickear en "carga" en la tab "Cuentas"
		Spark.post("/cuentas", cuentasController::crear); //Acá vamos a entrar al cargar el archivo de cuentas
		//Indicadores
		Spark.get("/indicadores", indicadoresController::listar, engine); //Acá vamos al clickear en "consulta" en la tab "Indicadores"
		Spark.get("/indicadores/listado", indicadoresController::mostrar, engine); //Acá vamos al filtrar la lista de indicadores en la vista
		Spark.get("/indicadores/new", indicadoresController::nuevo, engine); //Acá vamos al clickear en "carga" en la tab "Indicadores"
		Spark.post("/indicadores", indicadoresController::crear); //Acá vamos a entrar al cargar el indicador en la vista
		//Empresas
		Spark.get("/empresas/new", empresasController::nuevo, engine); //Acá vamos al clickear en "carga" en la tab "Empresas"
		Spark.post("/empresas", empresasController::crear); //Acá vamos a entrar al cargar la empresa en la vista
		//Metodologias
		Spark.get("/metodologias", metodologiasController::listar, engine); //Acá vamos al clickear en "consulta" en la tab "Metodologias"
		Spark.get("/metodologias/listado", metodologiasController::mostrar, engine); //Acá vamos al filtrar la lista de metodologias en la vista
		Spark.get("/metodologias/new", metodologiasController::nuevo, engine); //Acá vamos al clickear en "carga" en la tab "Metodologias"
		Spark.post("/metodologias", metodologiasController::crear); //Acá vamos a entrar al cargar la metodologia (Click en Finalizar) en la vista
		//Reglas
		Spark.get("/metodologias/new/reglas", metodologiasController::listar, engine); //Acá vamos al clickear en "continuar" al ingresar el nombre de la metodología
		Spark.get("/metodologias/new/reglas/taxativa", metodologiasController::nuevaTaxativa, engine); //Acá vamos al clickear en el botón "Taxativa"
		Spark.get("/metodologias/new/reglas/comparativa", metodologiasController::nuevaComparativa, engine); //Acá vamos al clickear en el botón "Comparativa"
		Spark.post("/metodologias/new/reglas", metodologiasController::mostrar, engine); //Acá vamos al crear una regla, la pasamos por post para que la agregue
	}
}
