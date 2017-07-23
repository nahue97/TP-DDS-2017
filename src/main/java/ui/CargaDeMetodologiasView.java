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

import ui.vm.AgregarReglasViewModel;
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

		new Label(cargaPanel).setText("Crear una Metodologia").setFontSize(13).setWidth(600);

		Panel datosMetPanel = new Panel(cargaPanel);

		datosMetPanel.setLayout(new HorizontalLayout());

		new Label(datosMetPanel).setText("Ingrese nombre:").setFontSize(12).setWidth(250);

		new TextBox(datosMetPanel).setWidth(200).bindValueToProperty("nombre");
	
		new Button(datosMetPanel).setCaption("Agregar reglas").onClick(this::irAReglas).setFontSize(11)
		.setBackground(Color.YELLOW).setWidth(200);
		
		new Button(cargaPanel).setCaption("Aceptar").onClick(() -> this.cargarMetodologia()).setAsDefault().setFontSize(11)
		.setBackground(Color.YELLOW);
		
		new Button(cargaPanel).setCaption("Consultar Metodologia").onClick(this::irAConsultas).setFontSize(11)
				.setBackground(Color.MAGENTA);

	}
	
	public void irAReglas(){
		Dialog<AgregarReglasViewModel> dialog = new AgregarReglasView(this);
		dialog.open();
	}
	
	public void irAConsultas() {
		Dialog<ConsultaDeMetodologiasViewModel> dialog = new ConsultaDeMetodologiasView(this); 
		dialog.open();
	}
	
	public void cargarMetodologia() {
		try {
			getModelObject().cargarMetodologia();
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensajeError(e.getMessage());
		}

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