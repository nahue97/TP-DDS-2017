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
import ui.vm.MenuDeMetodologiasViewModel;
import ui.vm.ConsultaDeMetodologiasViewModel;
import ui.ConsultaDeMetodologiasView;

@SuppressWarnings("serial")
public class MenuDeMetodologiasView extends Dialog<MenuDeMetodologiasViewModel>{

	public MenuDeMetodologiasView(WindowOwner owner) {
		super(owner, new MenuDeMetodologiasViewModel());
	}
	
	@Override
	public void createContents(Panel menuPanel) {

		setTitle("Metodologias");
		menuPanel.setLayout(new VerticalLayout());

		new Label(menuPanel).setText("Menu de Metodologias").setFontSize(13).setWidth(600);
		
		new Label(menuPanel).setFontSize(13);

		Panel datosMetPanel = new Panel(menuPanel);

		datosMetPanel.setLayout(new HorizontalLayout());

		new Label(datosMetPanel).setText("Ingrese nombre:").setFontSize(12).setWidth(250);

		new TextBox(datosMetPanel).setWidth(200).bindValueToProperty("nombre");
	
		new Button(datosMetPanel).setCaption("Comenzar creacion").onClick(this::cargarMetodologia).setFontSize(11)
		.setBackground(Color.GREEN).setWidth(200);	
		
		new Label(menuPanel).setFontSize(13);
		
		new Button(menuPanel).setCaption("Consultar Metodologia").onClick(this::irAConsultas).setFontSize(11)
				.setBackground(Color.MAGENTA);

	}
	
	public void irACreacion(){
		Dialog<CargaDeMetodologiasViewModel> dialog = new CargaDeMetodologiasView(this, "nombre");
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
			return;
		}
		this.irACreacion();
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