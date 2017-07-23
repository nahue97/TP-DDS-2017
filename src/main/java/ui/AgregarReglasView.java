package ui;

import java.awt.Color;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import ui.vm.AgregarReglasViewModel;
import ui.vm.ReglasComparativasViewModel;
import ui.vm.ReglasTaxativasViewModel;

public class AgregarReglasView extends Dialog<AgregarReglasViewModel> {

	public AgregarReglasView(WindowOwner owner) {
		super(owner, new AgregarReglasViewModel());
	}
	@Override
	protected void createFormPanel(Panel reglaPanel) {
		setTitle("Reglas");
		reglaPanel.setLayout(new VerticalLayout());

		new Label(reglaPanel).setText("Seleccione el tipo de regla a agregar").setFontSize(13).setWidth(500);

		Panel tiposPanel = new Panel(reglaPanel);

		tiposPanel.setLayout(new HorizontalLayout());
	
		new Button(tiposPanel).setCaption("Taxativa").onClick(this::irAReglasTaxativas).setFontSize(11)
		.setBackground(Color.GREEN).setWidth(250);
		
		new Button(tiposPanel).setCaption("Comparativa").onClick(this::irAReglasComparativas).setFontSize(11)
				.setBackground(Color.GREEN).setWidth(250);	
	}
	
	public void irAReglasTaxativas(){
		Dialog<ReglasTaxativasViewModel> dialog = new ReglasTaxativasView(this);
		dialog.open();
	}
	
	public void irAReglasComparativas() {
		Dialog<ReglasComparativasViewModel> dialog = new ReglasComparativasView(this); 
		dialog.open();
	}

}
