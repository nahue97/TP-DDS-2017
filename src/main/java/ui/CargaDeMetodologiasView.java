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

import ui.vm.CargaDeMetodologiasViewModel;
import ui.vm.ConsultaDeMetodologiasViewModel;
import ui.ConsultaDeMetodologiasView;

@SuppressWarnings("serial")
public class CargaDeMetodologiasView extends Dialog<CargaDeMetodologiasViewModel>{

	public CargaDeMetodologiasView(WindowOwner owner) {
		super(owner, new CargaDeMetodologiasViewModel());
	}
	
	@Override
	public void createContents(Panel cargaPanel) {

		setTitle("Metodologias");
		cargaPanel.setLayout(new VerticalLayout());

		new Label(cargaPanel).setText("Para crear una Metodologia ingrese:").setFontSize(13).setWidth(600);

		Panel datosIndPanel = new Panel(cargaPanel);

		datosIndPanel.setLayout(new HorizontalLayout());

		new Label(datosIndPanel).setText("Nombre de la Metodologia").setFontSize(12).setWidth(250);

		new TextBox(datosIndPanel).setWidth(200).setHeigth(22).bindValueToProperty("nombre");


		new Button(cargaPanel).setCaption("Consultar Metodologia").onClick(this::irAConsultas).setFontSize(11)
				.setBackground(Color.MAGENTA);

	}
	
	public void irAConsultas() {
		Dialog<ConsultaDeMetodologiasViewModel> dialog = new ConsultaDeMetodologiasView(this); 
		dialog.open();
	}

	protected void mostrarMensajeError(String message) {
		MessageBox messageBox = new MessageBox(this, MessageBox.Type.Error);
		messageBox.setMessage(message);
		messageBox.open();
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		// TODO Auto-generated method stub
		
	}
}