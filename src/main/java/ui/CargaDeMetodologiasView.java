package ui;

import java.awt.Color;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import model.Regla;
import ui.vm.AgregarReglasViewModel;
import ui.vm.CargaDeMetodologiasViewModel;

@SuppressWarnings("serial")
public class CargaDeMetodologiasView extends Dialog<CargaDeMetodologiasViewModel>{
	
	public String nombreMetodologia;
	
	public CargaDeMetodologiasView(WindowOwner owner, String nombreMetodologia) {
		super(owner, new CargaDeMetodologiasViewModel());
		this.nombreMetodologia = nombreMetodologia;
	}

	@Override
	public void createContents(Panel cargaPanel) {
		setTitle("Metodologias");
		cargaPanel.setLayout(new VerticalLayout());

		new Label(cargaPanel).setText("Creacion de Metodologias").setFontSize(13).setWidth(600);
		
		new Label(cargaPanel).setFontSize(13);

		Panel datosMetPanel = new Panel(cargaPanel);

		datosMetPanel.setLayout(new HorizontalLayout());
	
		new Button(datosMetPanel).setCaption("Nueva Regla").onClick(this::irAReglas).setFontSize(11)
		.setBackground(Color.YELLOW).setWidth(200);
		
		new Button(datosMetPanel).setCaption("Eliminar Regla").onClick(this::irAReglas).setFontSize(11)
		.setBackground(Color.RED).setWidth(200);
		
		Selector<Regla> selectorRegla = new Selector<Regla>(datosMetPanel)
			    .allowNull(false);
		selectorRegla.setHeigth(11).setWidth(200).bindValueToProperty("regla");
		selectorRegla.bindItemsToProperty("regla");
	}
	
	public void irAReglas(){
		Dialog<AgregarReglasViewModel> dialog = new AgregarReglasView(this);
		dialog.open();
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {	
	}
}
