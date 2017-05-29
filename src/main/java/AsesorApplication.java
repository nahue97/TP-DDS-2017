import org.apache.log4j.BasicConfigurator;
import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import ui.CargaDeCuentasView;
import ui.MenuPrincipalView;

public class AsesorApplication extends Application{

	public static void main(String[] args) {
		BasicConfigurator.configure();
		new AsesorApplication().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		return new MenuPrincipalView(this);
	}
}