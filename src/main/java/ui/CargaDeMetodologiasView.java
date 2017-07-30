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

		Panel botonesPanel = new Panel(cargaPanel);

		botonesPanel.setLayout(new HorizontalLayout());
	
		new Button(botonesPanel).setCaption("Nueva Regla").onClick(this::irAReglas).setFontSize(11)
		.setBackground(Color.YELLOW).setWidth(200);
		
		new Button(botonesPanel).setCaption("Eliminar Regla").onClick(this::eliminarRegla).setFontSize(11)
		.setBackground(Color.RED).setWidth(200);
		
		Selector<Regla> selectorRegla = new Selector<Regla>(botonesPanel)
			    .allowNull(false);
		selectorRegla.setHeigth(11).setWidth(200).bindValueToProperty("nombreRegla");
		selectorRegla.bindItemsToProperty("reglas");
/*		
		new Button(botonesPanel).setCaption("Refrescar").onClick(this::refrescarReglas).setFontSize(11)
		.setBackground(Color.darkGray).setWidth(100);
*/		
		new Label(cargaPanel).setFontSize(13);
		
		new Button(cargaPanel).setCaption("Guardar").onClick(this::guardarMetodologia).setFontSize(11)
		.setBackground(Color.GREEN).setWidth(200);
	}
	
	public void irAReglas(){
		Dialog<AgregarReglasViewModel> dialog = new AgregarReglasView(this);
		dialog.open();
	}
	
	public void eliminarRegla(){
		getModelObject().eliminarRegla();
	}
	
	protected void guardarMetodologia() {
		getModelObject().cargarMetodologia(nombreMetodologia);
		this.close();
	}
	
	public void refrescarReglas() {
		getModelObject().refrescarReglas();
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {	
	}



}
