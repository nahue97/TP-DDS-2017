import org.apache.log4j.BasicConfigurator;
import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import dtos.CargaDeArchivoTxtJsonDTO;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import ui.MenuPrincipalView;
import utils.AppData;

public class AsesorApplication extends Application {

	private static CargaDeArchivoTxtJsonDTO dtoCuentas = new CargaDeArchivoTxtJsonDTO();
	private static CargaDeArchivoTxtJsonDTO dtoIndicadores = new CargaDeArchivoTxtJsonDTO();

	public static void main(String[] args) {
		BasicConfigurator.configure();

		dtoCuentas.setPathFile("./Archivos de prueba/ArchivoDeCuentasParaTestsBueno.txt");
		dtoIndicadores.setPathFile("./Archivos de la App/Database Indicadores.txt");

		RepositorioCuentas.getInstance().setDtoCuentas(dtoCuentas);
		RepositorioIndicadores.getInstance().setDtoIndicadores(dtoIndicadores);
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