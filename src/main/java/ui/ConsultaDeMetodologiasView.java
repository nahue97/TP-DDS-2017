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

		new Label(metodologiasPanel).setText("Seleccione metodologia: ").setFontSize(11);

		Selector<MetodologiaCalculada> selectorMetodologia = new Selector<MetodologiaCalculada>(metodologiasPanel)
				.allowNull(false);
		selectorMetodologia.setWidth(200).bindValueToProperty("metodologia");
		selectorMetodologia.bindItemsToProperty("metodologias");
		
		Panel aniosPanel = new Panel(consultaPanel);
		aniosPanel.setLayout(new HorizontalLayout()).setWidth(600);
		
		new Label(aniosPanel).setText("Seleccione periodo de inicio y fin ").setFontSize(11);
		
		Selector<IndicadorCalculado> selectorPeriodoInicio = new Selector<IndicadorCalculado>(aniosPanel);
		selectorPeriodoInicio.bindItemsToProperty("periodos");
		selectorPeriodoInicio.bindValueToProperty("periodoInicio");
		
		Selector<IndicadorCalculado> selectorPeriodoFin = new Selector<IndicadorCalculado>(aniosPanel);
		selectorPeriodoFin.bindItemsToProperty("periodos");
		selectorPeriodoFin.bindValueToProperty("periodoFin");

		Panel tMetodologiasPanel = new Panel(consultaPanel);
		tMetodologiasPanel.setLayout(new VerticalLayout());

		new Button(tMetodologiasPanel).setCaption("Donde invierto?").onClick(() -> this.consultarMetodologia()).setAsDefault()
		.setWidth(600);
		
		this.tablaResultadoMetodologia(tMetodologiasPanel);

	}

	protected void tablaResultadoMetodologia(Panel consultaPanel) {
		Table<EmpresaEvaluadaPorMetodologia> tableMetodologia = new Table<EmpresaEvaluadaPorMetodologia>(consultaPanel,
				EmpresaEvaluadaPorMetodologia.class);
		tableMetodologia.setHeigth(150);
		tableMetodologia.setWidth(500);
		tableMetodologia.bindItemsToProperty("empresasEvaluadasPorMetodologias");

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
		columnaEmpresa.setFixedSize(150);
		columnaEmpresa.bindContentsToProperty("nombreEmpresa");

		Column<EmpresaEvaluadaPorMetodologia> columnaConveniencia = new Column<EmpresaEvaluadaPorMetodologia>(tableMetodologia);
		columnaConveniencia.setFont(11).setTitle("Conveniencia");
		columnaConveniencia.setFixedSize(100);
		columnaConveniencia.bindContentsToProperty("conveniencia");

	}
}