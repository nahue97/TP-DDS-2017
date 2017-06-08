package ui;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import model.Indicador;
import model.IndicadorCalculado;
import ui.vm.ConsultaDeIndicadoresViewModel;

@SuppressWarnings("serial")
public class ConsultaDeIndicadoresView extends Dialog<ConsultaDeIndicadoresViewModel> {

	public ConsultaDeIndicadoresView(WindowOwner owner) {
		super(owner, new ConsultaDeIndicadoresViewModel());
	}

	@Override
	public void createFormPanel(Panel consultaPanel) {

		setTitle("Consulta de indicadores");
		consultaPanel.setLayout(new VerticalLayout());

		Panel indicadorPanel = new Panel(consultaPanel);

		indicadorPanel.setLayout(new HorizontalLayout());

		new Label(indicadorPanel).setText("Nombre de indicador: ").setFontSize(11).setWidth(350);

		new TextBox(indicadorPanel).setWidth(200).setHeigth(20).bindValueToProperty("nombre");

		Panel empresaPanel = new Panel(consultaPanel);
		empresaPanel.setLayout(new HorizontalLayout());

		new Label(empresaPanel).setText("Empresa: ").setFontSize(11).setWidth(350);

		new TextBox(empresaPanel).setWidth(200).setHeigth(20).bindValueToProperty("empresa");

		Panel periodoPanel = new Panel(consultaPanel);
		periodoPanel.setLayout(new HorizontalLayout());

		new Label(periodoPanel).setText("Periodo: ").setFontSize(11).setWidth(350);

		new TextBox(periodoPanel).setWidth(200).setHeigth(20).bindValueToProperty("periodo");

		Panel tIndicadoresPanel = new Panel(consultaPanel);
		tIndicadoresPanel.setLayout(new VerticalLayout());

		new Label(tIndicadoresPanel).setText("Indicadores").setFontSize(11);
		this.tablaResultadoIndicadores(tIndicadoresPanel);

	}

	protected void tablaResultadoIndicadores(Panel consultaPanel) {
		Table<IndicadorCalculado> tableIndicador = new Table<IndicadorCalculado>(consultaPanel, IndicadorCalculado.class);
		tableIndicador.setHeigth(100);
		tableIndicador.setWidth(583);
		tableIndicador.bindItemsToProperty("indicadores");

		this.tablaIndicadores(tableIndicador);
	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions).setCaption("Aplicar").onClick(() -> getModelObject().consultarIndicador()).setAsDefault();

		new Button(actions).setCaption("Salir").onClick(this::cancel);
	}

	protected void tablaIndicadores(Table<IndicadorCalculado> tableIndicador) {

		new Column<IndicadorCalculado>(tableIndicador) //
				.setFont(11).setTitle("ID").setFixedSize(50).bindContentsToProperty("id");

		new Column<IndicadorCalculado>(tableIndicador).setFont(11).setTitle("Empresa").setFixedSize(100).setFont(9)
				.bindContentsToProperty("empresa");

		Column<IndicadorCalculado> columnaNombre = new Column<IndicadorCalculado>(tableIndicador);
		columnaNombre.setFont(11).setTitle("Nombre indicador");
		columnaNombre.setFixedSize(150);
		columnaNombre.bindContentsToProperty("nombre");

		Column<IndicadorCalculado> columnaPeriodo = new Column<IndicadorCalculado>(tableIndicador);
		columnaPeriodo.setFont(11).setTitle("Periodo");
		columnaPeriodo.setFixedSize(150);
		columnaPeriodo.bindContentsToProperty("periodo");

		Column<IndicadorCalculado> columnaValor = new Column<IndicadorCalculado>(tableIndicador);
		columnaValor.setFont(11).setTitle("Valor");
		columnaValor.setFixedSize(150);
		columnaValor.bindContentsToProperty("valor");
	}

}