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
import model.ReglaTaxativa;
import ui.vm.ReglasTaxativasViewModel;

@SuppressWarnings("serial")
public class ReglasTaxativasView extends Dialog<ReglasTaxativasViewModel> {

	public ReglasTaxativasView(WindowOwner owner) {
		super(owner, new ReglasTaxativasViewModel());
	}

	@Override
	protected void createFormPanel(Panel taxativasPanel) {
		setTitle("Reglas taxativas");
		taxativasPanel.setLayout(new VerticalLayout());

		new Label(taxativasPanel).setText("Crear regla taxativa").setFontSize(13).setWidth(500);

		new Label(taxativasPanel).setFontSize(13);

		Panel descripcionPanel = new Panel(taxativasPanel);

		descripcionPanel.setLayout(new HorizontalLayout());

		new Label(descripcionPanel).setText("Indicador").setFontSize(11).setWidth(200);
		new Label(descripcionPanel).setText("Comparador").setFontSize(11).setWidth(200);
		new Label(descripcionPanel).setText("Valor").setFontSize(11).setWidth(200);

		Panel armadoPanel = new Panel(taxativasPanel);

		armadoPanel.setLayout(new HorizontalLayout());

		Selector<Indicador> selectorIndicador = new Selector<Indicador>(armadoPanel).allowNull(false);
		selectorIndicador.setHeight(11).setWidth(200).bindValueToProperty("indicador");
		selectorIndicador.bindItemsToProperty("indicadores");

		Selector<Criterio> selectorCriterio = new Selector<Criterio>(armadoPanel).allowNull(false);
		selectorCriterio.setHeight(11).setWidth(200).bindValueToProperty("comparador");
		selectorCriterio.bindItemsToProperty("comparadores");

		new TextBox(armadoPanel).setWidth(200).setHeight(22).bindValueToProperty("valorAComparar");

		new Label(taxativasPanel).setFontSize(13);

		Panel guardadoPanelTaxativa = new Panel(taxativasPanel);

		guardadoPanelTaxativa.setLayout(new HorizontalLayout());

		new Label(guardadoPanelTaxativa).setText("Ingrese nombre:").setFontSize(12).setWidth(250);

		new TextBox(guardadoPanelTaxativa).setWidth(200).bindValueToProperty("nombreRegla");

		new Label(taxativasPanel).setFontSize(13);

		new Button(taxativasPanel).setCaption("Agregar").onClick(() -> this.agregarReglaTaxativa()).setFontSize(11)
				.setBackground(Color.cyan).setWidth(250);

	}

	private void agregarReglaTaxativa() {
		try {
			crearReglaYRefrescar(this.getModelObject().crearRegla());
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensajeError(e.getMessage());
			return;
		}
		this.close();
	}

	private void crearReglaYRefrescar(ReglaTaxativa regla) {
		((AgregarReglasView) getOwner()).agregarReglaYRefrescar(regla);
	}

	protected void mostrarMensajeError(String message) {
		MessageBox messageBox = new MessageBox(this, MessageBox.Type.Error);
		messageBox.setMessage(message);
		messageBox.open();
	}
}
