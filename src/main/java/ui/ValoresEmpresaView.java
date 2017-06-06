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
import org.uqbar.arena.windows.MessageBox;
import org.uqbar.arena.windows.WindowOwner;

import model.Cuenta;
import model.IndicadorCalculado;
import ui.vm.ValoresEmpresaViewModel;

@SuppressWarnings("serial")
public class ValoresEmpresaView extends Dialog<ValoresEmpresaViewModel> {

	public ValoresEmpresaView(WindowOwner owner) {
		super(owner, new ValoresEmpresaViewModel());
	}

	@Override
	public void createFormPanel(Panel valoresPanel) {

		setTitle("Valores de empresa");
		valoresPanel.setLayout(new VerticalLayout());

		new Label(valoresPanel).setText("Rellene los siguientes campos para listar los valores de una empresa")
				.setFontSize(12).setWidth(800);

		Panel empresaPeriodoPanel = new Panel(valoresPanel);
		empresaPeriodoPanel.setLayout(new HorizontalLayout());

		new Label(empresaPeriodoPanel).setText("Empresa: ").setFontSize(11).setWidth(150);

		new TextBox(empresaPeriodoPanel).setWidth(200).setHeigth(20).bindValueToProperty("empresa");

		new Label(empresaPeriodoPanel).setText("Período: ").setFontSize(11).setWidth(150);

		new TextBox(empresaPeriodoPanel).setWidth(200).setHeigth(20).bindValueToProperty("periodo");

		Panel tablasPanel = new Panel(valoresPanel);
		tablasPanel.setLayout(new HorizontalLayout());

		Panel tCuentasPanel = new Panel(tablasPanel);
		tCuentasPanel.setLayout(new VerticalLayout());

		new Label(tCuentasPanel).setText("Cuentas").setFontSize(11);
		this.tablaResultadoCuentas(tCuentasPanel);

		Panel tIndicadoresPanel = new Panel(tablasPanel);
		tIndicadoresPanel.setLayout(new VerticalLayout());

		new Label(tIndicadoresPanel).setText("Indicadores").setFontSize(11);
		this.tablaResultadoIndicadores(tIndicadoresPanel);

	}

	protected void tablaResultadoCuentas(Panel valoresPanel) {
		Table<Cuenta> tableCuentas = new Table<Cuenta>(valoresPanel, Cuenta.class);
		tableCuentas.setHeigth(100);
		tableCuentas.setWidth(583);
		tableCuentas.bindItemsToProperty("cuentas");

		this.tablaCuentas(tableCuentas);
	}

	protected void tablaCuentas(Table<Cuenta> tableCuentas) {

		new Column<Cuenta>(tableCuentas).setFont(11).setTitle("ID").setFixedSize(50).bindContentsToProperty("id");

		new Column<Cuenta>(tableCuentas).setFont(11).setTitle("Empresa").setFixedSize(100).setFont(9)
				.bindContentsToProperty("empresa");

		Column<Cuenta> columnaTipo = new Column<Cuenta>(tableCuentas);
		columnaTipo.setFont(11).setTitle("Tipo de cuenta");
		columnaTipo.setFixedSize(150);
		columnaTipo.bindContentsToProperty("tipo");

		Column<Cuenta> columnaPeriodo = new Column<Cuenta>(tableCuentas);
		columnaPeriodo.setFont(11).setTitle("Periodo");
		columnaPeriodo.setFixedSize(150);
		columnaPeriodo.bindContentsToProperty("periodo");

		Column<Cuenta> columnaValor = new Column<Cuenta>(tableCuentas);
		columnaValor.setFont(11).setTitle("Valor");
		columnaValor.setFixedSize(150);
		columnaValor.bindContentsToProperty("valor");
	}

	protected void tablaResultadoIndicadores(Panel valoresPanel) {
		Table<IndicadorCalculado> tableIndicador = new Table<IndicadorCalculado>(valoresPanel,
				IndicadorCalculado.class);
		tableIndicador.setHeigth(100);
		tableIndicador.setWidth(583);
		tableIndicador.bindItemsToProperty("indicadores");

		this.tablaIndicadores(tableIndicador);
	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions).setCaption("Aplicar").onClick(() -> this.consultarValores()).setAsDefault();

		new Button(actions).setCaption("Salir").onClick(this::cancel);
	}

	private void consultarValores() {
		try {
			getModelObject().consultarValores();
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
		columnaPeriodo.setFont(11).setTitle("Período");
		columnaPeriodo.setFixedSize(150);
		columnaPeriodo.bindContentsToProperty("periodo");

		Column<IndicadorCalculado> columnaValor = new Column<IndicadorCalculado>(tableIndicador);
		columnaValor.setFont(11).setTitle("Valor");
		columnaValor.setFixedSize(150);
		columnaValor.bindContentsToProperty("valor");
	}

}
