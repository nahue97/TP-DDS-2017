import org.apache.log4j.BasicConfigurator;
import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import dtos.PathFileTxtJson;
import ui.MenuPrincipalView;
import utils.AppData;

public class AsesorApplication extends Application {

	private static PathFileTxtJson dtoEmpresas = new PathFileTxtJson("./Archivos de la App/Database Empresas.txt");
	private static PathFileTxtJson dtoCuentas = new PathFileTxtJson("./Archivos de la App/Database Cuentas.txt");
	private static PathFileTxtJson dtoIndicadores = new PathFileTxtJson("./Archivos de la App/Database Indicadores.txt");

	public static void main(String[] args) {
		BasicConfigurator.configure();
		AppData.getInstance().setInicializacionDeEmpresas(dtoEmpresas);
		AppData.getInstance().setInicializacionDeCuentas(dtoCuentas);
		AppData.getInstance().setInicializacionDeIndicadores(dtoIndicadores);

		AppData.getInstance().inicializarRepositorios();

		new AsesorApplication().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new MenuPrincipalView(this);
	}
}