package ui;

import java.awt.Color;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.MessageBox;
import org.uqbar.arena.windows.WindowOwner;
import model.Criterio;
import model.Indicador;
import ui.vm.ReglasComparativasViewModel;

@SuppressWarnings("serial")
public class ReglasComparativasView extends Dialog<ReglasComparativasViewModel> {
	
	AgregarReglasView pantallaAnterior;
	
	public ReglasComparativasView(WindowOwner owner) {
		super(owner, new ReglasComparativasViewModel());
		setPantallaAnterior(owner);
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
		selectorIndicador.setHeight(11).setWidth(200).bindValueToProperty("indicador");
		selectorIndicador.bindItemsToProperty("indicadores");
		
		Panel armadoPanelComparativa = new Panel(comparativaPanel);
		
		armadoPanelComparativa.setLayout(new HorizontalLayout());
		
		new Label(armadoPanelComparativa).setText("Cuanto: ").setFontSize(11).setWidth(200);
		
		Selector<Criterio> selectorOrden = new Selector<Criterio>(armadoPanelComparativa)
			    .allowNull(false);
		selectorOrden.setHeight(11).setWidth(200).bindValueToProperty("criterio");
		selectorOrden.bindItemsToProperty("criterios");
		
		new Label(armadoPanelComparativa).setText("mas conviene.").setFontSize(11).setWidth(200);
		
		new Label(comparativaPanel).setFontSize(13);
		
		Panel guardadoPanelComparativa = new Panel(comparativaPanel);
		
		guardadoPanelComparativa.setLayout(new HorizontalLayout());
		
		new Label(guardadoPanelComparativa).setText("Ingrese nombre:").setFontSize(12).setWidth(250);

		new TextBox(guardadoPanelComparativa).setWidth(200).bindValueToProperty("nombreRegla");

		new Label(comparativaPanel).setFontSize(13);
		
		new Button(comparativaPanel).setCaption("Agregar").onClick(()-> this.agregarReglaComparativa()).setFontSize(11)
		.setBackground(Color.cyan).setWidth(250);
		
	}
	
	private void agregarReglaComparativa() {
		try {
			pantallaAnterior.getPantallaAnterior().getModelObject().agregarReglaTemporal(this.getModelObject().crearRegla());
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensajeError(e.getMessage());
			return;
		}
		this.close();
		pantallaAnterior.getPantallaAnterior().refrescarReglas();
	}

	protected void mostrarMensajeError(String message) {
		MessageBox messageBox = new MessageBox(this, MessageBox.Type.Error);
		messageBox.setMessage(message);
		messageBox.open();
	}
	
	private void setPantallaAnterior(WindowOwner owner) {
		pantallaAnterior = (AgregarReglasView) owner;
		
	}
}
