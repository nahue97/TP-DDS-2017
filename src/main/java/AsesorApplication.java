import org.apache.log4j.BasicConfigurator;
import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import dtos.CargaDeCuentasDTO;
import dtos.DTO;
import dtos.IndicadoresDTO;
import ui.MenuPrincipalView;
import utils.AppData;

public class AsesorApplication extends Application{
	private static final AppData appData = AppData.getInstance();
	
	private static CargaDeCuentasDTO dtoCuentas = new CargaDeCuentasDTO();
	private static IndicadoresDTO dtoIndicadores = new IndicadoresDTO();

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		dtoCuentas.setPathFile("./Archivos de la App/Database Cuentas.txt");
		dtoIndicadores.setPathFile("./Archivos de la App/Database Indicadores.txt");
		
		appData.cargarCuentas(dtoCuentas);
		appData.cargarIndicadores(dtoIndicadores);
		
		new AsesorApplication().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		return new MenuPrincipalView(this);
	}
}