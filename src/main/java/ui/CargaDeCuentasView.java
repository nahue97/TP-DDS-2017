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

		setTitle("Sistema de Analisis de Inversiones");
		cargaPanel.setLayout(new VerticalLayout());
		
		new Label(cargaPanel).setText("Por favor seleccione el archivo de cuentas a cargar")
		 .setFontSize(12).setWidth(400);
		
		Panel archivoPanel = new Panel(cargaPanel);
		archivoPanel.setLayout(new HorizontalLayout());

		new Label(archivoPanel).setText("Seleccionar un archivo: ").setFontSize(12);

		new FileSelector(archivoPanel).setCaption("Buscar Archivo").bindValueToProperty("pathFile");
		
		new Label(cargaPanel).setFontSize(8).bindValueToProperty("pathFile");
		
		new Label(cargaPanel).setFontSize(10).bindValueToProperty("estado");
				
		new Button(cargaPanel)
			.setCaption("Cargar archivo")
			.onClick(this::cargarCuentas)
			.setFontSize(11)
			.setBackground(Color.GREEN);

		new Button(cargaPanel)
			.setCaption("Consultar cuentas de empresa")
			.onClick(this::irAConsultas)
			.setFontSize(11)
			.setBackground(Color.MAGENTA);
	}
	
	public void cargarCuentas() {
		try {
			getModelObject().cargarCuentas();
		} catch (Exception e) {
			e.printStackTrace();
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

