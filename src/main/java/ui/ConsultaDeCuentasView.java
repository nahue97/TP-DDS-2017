package ui;
import java.util.List;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.Widget;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.IModel;
import org.uqbar.ui.view.ErrorViewer;

import model.Cuenta;
import ui.vm.ConsultaDeCuentasViewModel;

public class ConsultaDeCuentasView extends Dialog<ConsultaDeCuentasViewModel>{
	
	public ConsultaDeCuentasView(WindowOwner owner, List<Cuenta> cuentas){
		super(owner, new ConsultaDeCuentasViewModel(cuentas));
	}
	
	@Override
	protected void createFormPanel(Panel consultaPanel){
		
		setTitle("Consultar cuentas de empresa");
		consultaPanel.setLayout(new VerticalLayout());
		
		Panel empresaPanel = new Panel(consultaPanel);
		empresaPanel.setLayout(new HorizontalLayout());
		
		Panel cuentaPanel = new Panel(consultaPanel);
		cuentaPanel.setLayout(new HorizontalLayout());
		
		new Label(cuentaPanel)
		.setText("Tipo de cuenta: ")
		.setFontSize(11);
		
		new TextBox(cuentaPanel)
		.setWidth(200)
		.setHeigth(20)
		.bindValueToProperty("tipoCuenta");

		new Label(empresaPanel)
		.setText("Empresa: ")
		.setFontSize(11);
		
		new TextBox(empresaPanel)
		.setWidth(200)
		.setHeigth(20)
		.bindValueToProperty("empresa");
		
		Panel periodoPanel = new Panel(consultaPanel);
		periodoPanel.setLayout(new HorizontalLayout());
		
		new Label(periodoPanel)
		.setText("Período: ")
		.setFontSize(11);
		
		new TextBox(periodoPanel)
		.setWidth(50)
		.setHeigth(20)
		.bindValueToProperty("periodo");
		
		//this.tablaResultadoCuentas(consultaPanel);
		
	}
	
	protected void addActions(Panel actions) {
		new Button(actions)
		.setCaption("Consultar")
		.onClick(()->getModelObject().consultarCuenta())
		.setAsDefault();
		
		new Button(actions)
		.setCaption("Cancelar")
		.onClick(this::cancel);
}
	
	protected void tablaResultadoCuentas(Panel mainPanel) {
		Table<Cuenta> tableCuentas = new Table<Cuenta>(mainPanel, Cuenta.class);
		tableCuentas.bindItemsToProperty("cuentas");

		this.tablaCuentas(tableCuentas);
	}

	protected void tablaCuentas(Table<Cuenta> tableCuentas) {

		new Column<Cuenta>(tableCuentas) //
				.setFont(11).setTitle("Id").setFixedSize(50).bindContentsToProperty("id");

		new Column<Cuenta>(tableCuentas).setFont(11).setTitle("Empresa").setFixedSize(100).setFont(9)
				.bindContentsToProperty("empresa");

		Column<Cuenta> columnaTipo = new Column<Cuenta>(tableCuentas);
		columnaTipo.setFont(11).setTitle("Tipo de cuenta");
		columnaTipo.setFixedSize(150);
		columnaTipo.bindContentsToProperty("tipo");

		Column<Cuenta> columnaValor = new Column<Cuenta>(tableCuentas);
		columnaValor.setFont(11).setTitle("Valor");
		columnaValor.setFixedSize(150);
		columnaValor.bindContentsToProperty("valor");

	}

}