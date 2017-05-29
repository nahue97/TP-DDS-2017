package ui;
import java.awt.Color;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import ui.vm.CargaDeIndicadoresViewModel;
import ui.vm.ConsultaDeIndicadoresViewModel;

@SuppressWarnings("serial")
public class CargaDeIndicadoresView extends Dialog<CargaDeIndicadoresViewModel> {

	public CargaDeIndicadoresView(WindowOwner owner) {
		super(owner, new CargaDeIndicadoresViewModel());
	}

	@Override
	public void createContents(Panel cargaPanel) {

		setTitle("Indicadores");
		cargaPanel.setLayout(new VerticalLayout());
		
		new Label(cargaPanel).setText("Ingrese el nombre del indicador a cargar:")
		 .setFontSize(12).setWidth(600);
		
		new TextBox(cargaPanel)
		.setWidth(200)
		.setHeigth(20)
		/*.bindValueToProperty("nombre")*/;

				
		new Button(cargaPanel)
			.setCaption("Cargar Indicador")
			.onClick(this::cargarIndicador)
			.setFontSize(11)
			.setBackground(Color.GREEN);

		new Button(cargaPanel)
			.setCaption("Consultar Indicador")
			.onClick(this::irAConsultas)
			.setFontSize(11)
			.setBackground(Color.MAGENTA);
	}
	
	public void cargarIndicador() {
			getModelObject().cargarIndicador();

	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		// TODO Auto-generated method stub
		
	}

	public void irAConsultas() {
		Dialog<ConsultaDeIndicadoresViewModel> dialog = new ConsultaDeIndicadoresView(this);
		dialog.open();
		dialog.onAccept(() -> {});
	}
}

