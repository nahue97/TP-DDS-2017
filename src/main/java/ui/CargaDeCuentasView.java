package ui;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.widgets.FileSelector;

import ui.vm.CargaDeCuentasViewModel;

@SuppressWarnings("serial")
public class CargaDeCuentasView extends Dialog<CargaDeCuentasViewModel>{

	public CargaDeCuentasView(WindowOwner owner) {
		super(owner, new CargaDeCuentasViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {

		setTitle("Cargar cuenta de empresa");
		mainPanel.setLayout(new VerticalLayout());
		
		Panel empresaPanel = new Panel(mainPanel);
		empresaPanel.setLayout(new HorizontalLayout());

		new Label(empresaPanel).setText("Ingrese empresa: ").setFontSize(11);
		new TextBox(empresaPanel).setWidth(500).setHeigth(15).bindValueToProperty("empresa");
		
		Panel periodoPanel = new Panel(mainPanel);
		periodoPanel.setLayout(new HorizontalLayout());
		
		new Label(periodoPanel).setText("Período: ").setFontSize(11);
		new TextBox(periodoPanel).setWidth(500).setHeigth(15).bindValueToProperty("periodo");

		Panel cuentaPanel = new Panel(mainPanel);
		cuentaPanel.setLayout(new HorizontalLayout());

		new FileSelector(cuentaPanel)
		   .setCaption("Abrir archivo de cuenta: ")
		   .bindValueToProperty("archivoDeCuenta");
	
	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions).setCaption("Aceptar").onClick(()->getModelObject().guardarCuenta()).setAsDefault();
		new Button(actions).setCaption("Cancelar").onClick(this::cancel);
	}

}


