package ui;

import java.awt.Color;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import model.IndicadorCalculado;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
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
		
		new Label(botonesPanel).setText("Seleccione una regla a modificar").setFontSize(10);
		Selector<Regla> selectorRegla = new Selector<Regla>(botonesPanel)
			    .allowNull(false);
		selectorRegla.setHeight(11).setWidth(200).bindValueToProperty("nombreRegla");
		selectorRegla.bindItemsToProperty("reglas");
/*		
		new Button(botonesPanel).setCaption("Refrescar").onClick(this::refrescarReglas).setFontSize(11)
		.setBackground(Color.darkGray).setWidth(100);
*/		
		new Label(cargaPanel).setText("Detalle de las reglas de mi metodologia").setFontSize(13);
		
		Panel tReglasPanel = new Panel(cargaPanel);
		tReglasPanel.setLayout(new HorizontalLayout());
		
		Panel tReglasCP = new Panel(tReglasPanel);
		tReglasCP.setLayout(new VerticalLayout());
		new Label(tReglasCP).setText("Reglas comparativas").setFontSize(13);
		this.tablaReglasComparativas(tReglasCP);
		
		Panel tReglasTP = new Panel(tReglasPanel);
		tReglasTP.setLayout(new VerticalLayout());
		new Label(tReglasTP).setText("Reglas taxativas").setFontSize(13);
		this.tablaReglasTaxativas(tReglasTP);
		
		new Button(cargaPanel).setCaption("Guardar").onClick(this::guardarMetodologia).setFontSize(11)
		.setBackground(Color.GREEN).setWidth(200);
	}
	
	protected void tablaReglasComparativas(Panel cargaPanel) {
		Table<ReglaComparativa> tableReglasComparativas = new Table<ReglaComparativa>(cargaPanel, ReglaComparativa.class);
		tableReglasComparativas.setHeight(200);
		tableReglasComparativas.bindItemsToProperty("reglasComparativas");

		this.tablaReglasComparativasT(tableReglasComparativas);
	}
	
	protected void tablaReglasTaxativas(Panel cargaPanel) {
		Table<ReglaTaxativa> tableReglasTaxativas = new Table<ReglaTaxativa>(cargaPanel, ReglaTaxativa.class);
		tableReglasTaxativas.setHeight(200);
		tableReglasTaxativas.bindItemsToProperty("reglasTaxativas");

		this.tablaReglasTaxativasT(tableReglasTaxativas);
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
	
protected void tablaReglasComparativasT(Table<ReglaComparativa> tableReglasComparativas) {
				
		Column<ReglaComparativa> columnaNombre = new Column<ReglaComparativa>(tableReglasComparativas);
		columnaNombre.setFont(11).setTitle("Nombre");
		columnaNombre.setFixedSize(180);
		columnaNombre.bindContentsToProperty("nombre");

		Column<ReglaComparativa> columnaIndicador = new Column<ReglaComparativa>(tableReglasComparativas);
		columnaIndicador.setFont(11).setTitle("Indicador utilizado");
		columnaIndicador.setFixedSize(180);
		columnaIndicador.bindContentsToProperty("indicador");
		
		Column<ReglaComparativa> columnaCriterio = new Column<ReglaComparativa>(tableReglasComparativas);
		columnaCriterio.setFont(11).setTitle("Criterio");
		columnaCriterio.setFixedSize(180);
		columnaCriterio.bindContentsToProperty("criterio");
		
	}

protected void tablaReglasTaxativasT(Table<ReglaTaxativa> tableReglasTaxativas) {
	
	Column<ReglaTaxativa> columnaNombre = new Column<ReglaTaxativa>(tableReglasTaxativas);
	columnaNombre.setFont(11).setTitle("Nombre");
	columnaNombre.setFixedSize(180);
	columnaNombre.bindContentsToProperty("nombre");

	Column<ReglaTaxativa> columnaIndicador = new Column<ReglaTaxativa>(tableReglasTaxativas);
	columnaIndicador.setFont(11).setTitle("Indicador utilizado");
	columnaIndicador.setFixedSize(180);
	columnaIndicador.bindContentsToProperty("indicador");
	
	Column<ReglaTaxativa> columnaComparador = new Column<ReglaTaxativa>(tableReglasTaxativas);
	columnaComparador.setFont(11).setTitle("Comparador");
	columnaComparador.setFixedSize(180);
	columnaComparador.bindContentsToProperty("comparador");
	
	Column<ReglaTaxativa> columnaValor = new Column<ReglaTaxativa>(tableReglasTaxativas);
	columnaValor.setFont(11).setTitle("Valor a comparar");
	columnaValor.setFixedSize(180);
	columnaValor.bindContentsToProperty("valorAComparar");
	
}

	
	@Override
	protected void createFormPanel(Panel mainPanel) {	
	}



}
