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
		
		new Label(cargaPanel).setText("Por favor seleccione el archivo de cuentas a cargar")
		 .setFontSize(11).setWidth(400);
		
		Panel archivoPanel = new Panel(cargaPanel);
		archivoPanel.setLayout(new HorizontalLayout());

		new Label(archivoPanel).setText("Seleccionar un archivo y cargar cuentas: ").setFontSize(11);

		new FileSelector(archivoPanel).setCaption("Buscar Archivo").bindValueToProperty("pathFile");
		
		//Se eliminó el botón cargar, ahora consultar cuentas hace todo

		new Button(cargaPanel) //
				.setCaption("Consultar cuentas de empresa") //
				.onClick(this::irAConsultas).setBackground(Color.MAGENTA);

	}
	
	//Retorna falso si no hubo ningún error
	public boolean cargarCuentas() {
		try {
			getModelObject().cargarCuentas();
		} catch (Exception e) {
			e.printStackTrace();
			
			//Se cambió el mensaje del JSON, así es más sencillo para el usuario
			if(e.getMessage() == "Error Sintactico en el JSON")
				mostrarMensajeError("El formato del archivo seleccionado es"
						+ " incorrecto, por favor corregir el archivo o"
						+ " seleccionar otro.");
			
			if(e.getMessage() == "Archivo no encontrado")
				mostrarMensajeError("El archivo no se encuentra,"
					+ " por favor intente poner el archivo de texto"
					+ " en un lugar cómodo como el escritorio de su PC"
					+ " y luego seleccionarlo con el botón Buscar Archivo.");
			
			return true;
		}
		return false;
	}

	public void irAConsultas() {
		if(cargarCuentas())//Si retornó con errores detiene la ejecución del programa
			return;
		
		Dialog<?> dialog = new ConsultaDeCuentasView(this);
		dialog.open();
		dialog.onAccept(() -> {});
	}
	
	protected void mostrarMensajeError(String message) {
		MessageBox messageBox = new MessageBox(this, MessageBox.Type.Error);
		messageBox.setMessage(message);
		messageBox.open();
		//Quiero cerrar esta ventana, no tengo ni idea de como hacer
	}
}

