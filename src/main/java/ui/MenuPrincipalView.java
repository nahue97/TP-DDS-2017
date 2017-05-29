package ui;

import java.awt.Color;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.windows.MessageBox;
import org.uqbar.arena.widgets.FileSelector;
import ui.ConsultaDeCuentasView;
import ui.vm.CargaDeCuentasViewModel;
import ui.vm.MenuPrincipalViewModel;

@SuppressWarnings("serial")
public class MenuPrincipalView extends Window<MenuPrincipalViewModel> {

	public MenuPrincipalView(WindowOwner owner) {
		super(owner, new MenuPrincipalViewModel());
	}

	@Override
	public void createContents(Panel cargaPanel) {

		setTitle("Sistema de análisis de inversiones");
		cargaPanel.setLayout(new VerticalLayout())
		.setWidth(600);
		
		new Label(cargaPanel).setText("Seleccione:")
		 .setFontSize(12).setWidth(600);
		
		Panel archivoPanel = new Panel(cargaPanel);
		archivoPanel.setLayout(new HorizontalLayout());
				
		new Button(cargaPanel)
			.setCaption("Cuentas")
			.onClick(this::irACuentas)
			.setFontSize(11)
			.setBackground(Color.pink);

		/*new Button(cargaPanel)
			.setCaption("Indicadores")
			.onClick(this::irAIndicadores)
			.setFontSize(11)
			.setBackground(Color.PINK);*/
	}
	

	public void irACuentas() {
		Dialog<CargaDeCuentasViewModel> dialog = new CargaDeCuentasView(this);
		dialog.open();
		dialog.onAccept(() -> {});
	}
	
	/*public void irAIndicadores() {
		Dialog<?> dialog = new CargaDeIndicadoresView(this);
		dialog.open();
		dialog.onAccept(() -> {});
	}*/
	
}

