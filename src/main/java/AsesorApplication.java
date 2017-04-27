import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import ui.CargaDeCuentasView;

public class AsesorApplication extends Application{

	public static void main(String[] args) {
		new AsesorApplication().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		return new CargaDeCuentasView(this);
	}
}