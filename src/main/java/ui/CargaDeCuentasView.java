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

@SuppressWarnings("serial")
public class CargaDeCuentasView extends Window<CargaDeCuentasViewModel> {

	public CargaDeCuentasView(WindowOwner owner) {
		super(owner, new CargaDeCuentasViewModel());
	}

	@Override
	public void createContents(Panel cargaPanel) {

		setTitle("Sistema de analisis de inversiones");
		cargaPanel.setLayout(new VerticalLayout());

		Panel archivoPanel = new Panel(cargaPanel);
		archivoPanel.setLayout(new HorizontalLayout());

		new Label(archivoPanel).setText("Cargar cuenta de empresa").setFontSize(11);

		new FileSelector(archivoPanel).setCaption("Buscar").setWidth(70).bindValueToProperty("pathFile");

		new Label(cargaPanel).setFontSize(7).setWidth(400).bindValueToProperty("pathFile");
		
		new Label(cargaPanel).setFontSize(7).bindValueToProperty("estado");

		new Button(cargaPanel).setCaption("Cargar").onClick(this::cargarCuenta).setBackground(Color.GREEN);

		new Button(cargaPanel) //
				.setCaption("Consultar cuentas de empresa") //
				.onClick(this::irAConsultas).setBackground(Color.MAGENTA);

	}

	public void cargarCuenta() {
		try {
			getModelObject().cargarCuenta();
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensajeError(e.getMessage());
		}
	}

	public void irAConsultas() {
		Dialog<?> dialog = new ConsultaDeCuentasView(this);
		dialog.open();
		dialog.onAccept(() -> {});
	}
	
	protected void mostrarMensajeError(String message) {
		MessageBox messageBox = new MessageBox(this, MessageBox.Type.Error);
		messageBox.setMessage(message);
		messageBox.open();
	}
}

