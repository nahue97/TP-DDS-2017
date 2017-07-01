package ui;

import java.awt.Color;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.MessageBox;
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

		new Label(cargaPanel).setText("Para crear un indicador ingrese:").setFontSize(13).setWidth(600);

		Panel datosIndPanel = new Panel(cargaPanel);

		datosIndPanel.setLayout(new HorizontalLayout());

		new Label(datosIndPanel).setText("Nombre del indicador").setFontSize(12).setWidth(250);

		new TextBox(datosIndPanel).setWidth(200).setHeigth(22).bindValueToProperty("nombre");

		new Label(datosIndPanel).setText("Fórmula").setFontSize(12).setWidth(100);

		new TextBox(datosIndPanel).setWidth(200).setHeigth(22).bindValueToProperty("formulaIngresada");

		new Button(cargaPanel).setCaption("Cargar Indicador").onClick(this::cargarIndicador).setFontSize(11)
				.setBackground(Color.GREEN);

		new Button(cargaPanel).setCaption("Consultar Indicador").onClick(this::irAConsultas).setFontSize(11)
				.setBackground(Color.MAGENTA);

	}

	public void cargarIndicador() {
		try {
			getModelObject().cargarIndicador();
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensajeError(e.getMessage());
		}

	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		// TODO Auto-generated method stub

	}

	public void irAConsultas() {
		Dialog<ConsultaDeIndicadoresViewModel> dialog = new ConsultaDeIndicadoresView(this);
		dialog.open();
	}

	protected void mostrarMensajeError(String message) {
		MessageBox messageBox = new MessageBox(this, MessageBox.Type.Error);
		messageBox.setMessage(message);
		messageBox.open();
	}
}
