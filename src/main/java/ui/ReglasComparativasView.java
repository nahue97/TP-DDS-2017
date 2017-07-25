package ui;

import java.awt.Color;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.MessageBox;
import org.uqbar.arena.windows.WindowOwner;

import model.Criterio;
import model.Indicador;
import ui.vm.ReglasComparativasViewModel;
import ui.vm.ReglasTaxativasViewModel;

public class ReglasComparativasView extends Dialog<ReglasComparativasViewModel> {

	public ReglasComparativasView(WindowOwner owner) {
		super(owner, new ReglasComparativasViewModel());
	}
	
	@Override
	protected void createFormPanel(Panel comparativaPanel) {
		setTitle("Reglas comparativa");
		comparativaPanel.setLayout(new VerticalLayout());

		new Label(comparativaPanel).setText("Crear regla comparativa").setFontSize(13).setWidth(500);
		
		new Label(comparativaPanel).setFontSize(13);
		
		Panel armadoPanel = new Panel(comparativaPanel);
		
		armadoPanel.setLayout(new HorizontalLayout());
		
		new Label(armadoPanel).setText("Indicador: ").setFontSize(11).setWidth(200);

		Selector<Indicador> selectorIndicador = new Selector<Indicador>(armadoPanel)
			    .allowNull(false);
		selectorIndicador.setHeigth(11).setWidth(200).bindValueToProperty("indicador");
		selectorIndicador.bindItemsToProperty("indicadores");
		
		Panel armadoPanelCriterio = new Panel(comparativaPanel);
		
		armadoPanelCriterio.setLayout(new HorizontalLayout());
		
		new Label(armadoPanelCriterio).setText("Cuanto: ").setFontSize(11).setWidth(200);
		
		Selector<Criterio> selectorOrden = new Selector<Criterio>(armadoPanelCriterio)
			    .allowNull(false);
		selectorOrden.setHeigth(11).setWidth(200).bindValueToProperty("criterio");
		selectorOrden.bindItemsToProperty("criterios");
		
		new Label(armadoPanelCriterio).setText("mas conviene.").setFontSize(11).setWidth(200);
		
		new Label(comparativaPanel).setFontSize(13);
		
		new Button(comparativaPanel).setCaption("Agregar").onClick(()-> this.agregarReglaComparativa()).setFontSize(11)
		.setBackground(Color.cyan).setWidth(250);
		
	}
	
	private void agregarReglaComparativa() {
		try {
			getModelObject().agregarRegla();
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


}
