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

import model.Cuenta;
import ui.vm.ConsultaDeCuentasViewModel;

@SuppressWarnings("serial")
public class ConsultaDeCuentasView extends Dialog<ConsultaDeCuentasViewModel> {

	public ConsultaDeCuentasView(WindowOwner owner) {
		super(owner, new ConsultaDeCuentasViewModel());
	}

	@Override
	protected void createFormPanel(Panel consultaPanel) {

		setTitle("Consultar cuentas de empresa");
		consultaPanel.setLayout(new VerticalLayout());

		new Label(consultaPanel).setText("Rellene los siguientes campos para filtar el listado de cuentas")
				.setFontSize(12);

		Panel cuentaPanel = new Panel(consultaPanel);

		cuentaPanel.setLayout(new HorizontalLayout());

		new Label(cuentaPanel).setText("Tipo de cuenta: ").setFontSize(11).setWidth(200);

		new TextBox(cuentaPanel).setWidth(200).setHeigth(20).bindValueToProperty("tipoCuenta");

		Panel empresaPanel = new Panel(consultaPanel);
		empresaPanel.setLayout(new HorizontalLayout());

		new Label(empresaPanel).setText("Empresa: ").setFontSize(11).setWidth(200);

		new TextBox(empresaPanel).setWidth(200).setHeigth(20).bindValueToProperty("empresa");

		Panel periodoPanel = new Panel(consultaPanel);
		periodoPanel.setLayout(new HorizontalLayout());

		new Label(periodoPanel).setText("Periodo: ").setFontSize(11).setWidth(200);

		new TextBox(periodoPanel).setWidth(200).setHeigth(20).bindValueToProperty("periodo");

		Panel valorPanel = new Panel(consultaPanel);
		valorPanel.setLayout(new HorizontalLayout());

		new Label(valorPanel).setText("Valor: ").setFontSize(11).setWidth(200);

		new TextBox(valorPanel).setWidth(200).setHeigth(20).bindValueToProperty("valor");

		Panel ordenarPanel = new Panel(consultaPanel);
		ordenarPanel.setLayout(new HorizontalLayout());

		new Label(ordenarPanel).setText("Ordenar por: ").setFontSize(11).setWidth(200);

		new Button(ordenarPanel).setCaption("Tipo").onClick(() -> getModelObject().ordenarCuentasPorTipo())
				.setAsDefault().setWidth(47);

		new Button(ordenarPanel).setCaption("Empresa").onClick(() -> getModelObject().ordenarCuentasPorEmpresa());

		new Button(ordenarPanel).setCaption("Periodo").onClick(() -> getModelObject().ordenarCuentasPorPeriodo());

		new Button(ordenarPanel).setCaption("Valor").onClick(() -> getModelObject().ordenarCuentasPorValor())
				.setWidth(47);

		this.tablaResultadoCuentas(consultaPanel);

	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions).setCaption("Aplicar filtros").onClick(() -> getModelObject().consultarCuenta())
				.setAsDefault();

		new Button(actions).setCaption("Salir").onClick(this::cancel);
	}

	protected void tablaResultadoCuentas(Panel consultaPanel) {
		Table<Cuenta> tableCuentas = new Table<Cuenta>(consultaPanel, Cuenta.class);
		tableCuentas.setHeigth(100);
		tableCuentas.setWidth(583);
		tableCuentas.bindItemsToProperty("cuentas");

		this.tablaCuentas(tableCuentas);
	}

	protected void tablaCuentas(Table<Cuenta> tableCuentas) {

		new Column<Cuenta>(tableCuentas) //
				.setFont(11).setTitle("ID").setFixedSize(50).bindContentsToProperty("id");

		new Column<Cuenta>(tableCuentas).setFont(11).setTitle("Empresa").setFixedSize(100).setFont(9)
				.bindContentsToProperty("empresa");

		Column<Cuenta> columnaTipo = new Column<Cuenta>(tableCuentas);
		columnaTipo.setFont(11).setTitle("Tipo de cuenta");
		columnaTipo.setFixedSize(150);
		columnaTipo.bindContentsToProperty("tipo");

		Column<Cuenta> columnaPeriodo = new Column<Cuenta>(tableCuentas);
		columnaPeriodo.setFont(11).setTitle("Período");
		columnaPeriodo.setFixedSize(150);
		columnaPeriodo.bindContentsToProperty("periodo");

		Column<Cuenta> columnaValor = new Column<Cuenta>(tableCuentas);
		columnaValor.setFont(11).setTitle("Valor");
		columnaValor.setFixedSize(150);
		columnaValor.bindContentsToProperty("valor");

	}

}