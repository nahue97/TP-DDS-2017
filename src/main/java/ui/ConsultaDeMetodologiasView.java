package ui;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.MessageBox;
import org.uqbar.arena.windows.WindowOwner;

import model.EmpresaEvaluadaPorMetodologia;
import model.IndicadorCalculado;
import model.Metodologia;
import model.MetodologiaCalculada;
import ui.vm.ConsultaDeMetodologiasViewModel;

@SuppressWarnings("serial")
public class ConsultaDeMetodologiasView extends Dialog<ConsultaDeMetodologiasViewModel> {

	public ConsultaDeMetodologiasView(WindowOwner owner) {
		super(owner, new ConsultaDeMetodologiasViewModel());
	}

	@Override
	protected void createFormPanel(Panel consultaPanel) {

		setTitle("Consulta de Metodologias");
		consultaPanel.setLayout(new VerticalLayout());

		Panel metodologiasPanel = new Panel(consultaPanel);
		metodologiasPanel.setLayout(new HorizontalLayout()).setWidth(600);

		new Label(metodologiasPanel).setText("Seleccione metodología: ").setFontSize(11);

		Selector<MetodologiaCalculada> selectorMetodologia = new Selector<MetodologiaCalculada>(metodologiasPanel)
				.allowNull(false);
		selectorMetodologia.setWidth(200);// .bindValueToProperty("metodologia");
		// selectorMetodologia.bindItemsToProperty("metodologias");

		new Button(metodologiasPanel).setCaption("Aceptar").onClick(() -> this.consultarMetodologia()).setAsDefault();

		Panel tMetodologiasPanel = new Panel(consultaPanel);
		tMetodologiasPanel.setLayout(new VerticalLayout());

		new Label(tMetodologiasPanel).setText("¿Donde invierto?").setFontSize(11);
		this.tablaResultadoMetodologia(tMetodologiasPanel);

	}

	protected void tablaResultadoMetodologia(Panel consultaPanel) {
		Table<EmpresaEvaluadaPorMetodologia> tableMetodologia = new Table<EmpresaEvaluadaPorMetodologia>(consultaPanel,
				EmpresaEvaluadaPorMetodologia.class);
		tableMetodologia.setHeigth(100);
		tableMetodologia.setWidth(583);
		tableMetodologia.bindItemsToProperty("");

		this.tablaMetodologiaEmpresas(tableMetodologia);
	}

	private void consultarMetodologia() {
		try {
			getModelObject().consultarMetodologia();
		} catch (Exception e) {
			mostrarMensajeError(e.getMessage());
		}
	}

	protected void mostrarMensajeError(String message) {
		MessageBox messageBox = new MessageBox(this, MessageBox.Type.Error);
		messageBox.setMessage(message);
		messageBox.open();
	}

	protected void tablaMetodologiaEmpresas(Table<EmpresaEvaluadaPorMetodologia> tableMetodologia) {

		Column<EmpresaEvaluadaPorMetodologia> columnaEmpresa = new Column<EmpresaEvaluadaPorMetodologia>(tableMetodologia);
		columnaEmpresa.setFont(11).setTitle("Empresa");
		columnaEmpresa.setFixedSize(120);
		columnaEmpresa.bindContentsToProperty("empresa");

		Column<EmpresaEvaluadaPorMetodologia> columnaConveniencia = new Column<EmpresaEvaluadaPorMetodologia>(tableMetodologia);
		columnaConveniencia.setFont(11).setTitle("Conveniencia");
		columnaConveniencia.setFixedSize(100);
		columnaConveniencia.bindContentsToProperty("conveniencia");

	}
}