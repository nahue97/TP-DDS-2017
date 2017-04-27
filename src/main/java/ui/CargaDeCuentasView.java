package ui;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.widgets.FileSelector;
import ui.ConsultaDeCuentasView;
import ui.vm.CargaDeCuentasViewModel;

@SuppressWarnings("serial")
public class CargaDeCuentasView extends MainWindow<CargaDeCuentasViewModel>{

	public CargaDeCuentasView(WindowOwner owner) {
		super(new CargaDeCuentasViewModel());
	}

	@Override
	public void createContents(Panel cargaPanel) {
		
		setTitle("Sistema de análisis de inversiones");
		cargaPanel.setLayout(new VerticalLayout());
		
		setTitle("Cargar cuenta de empresa"); 
		cargaPanel.setLayout(new VerticalLayout());
		
		Panel empresaPanel = new Panel(cargaPanel);
		empresaPanel.setLayout(new HorizontalLayout());

		new Label(empresaPanel)
		.setText("Ingrese empresa: ")
		.setFontSize(11);
		
		new TextBox(empresaPanel)
		.setWidth(500)
		.setHeigth(15)
		.bindValueToProperty("empresa");
		
		Panel periodoPanel = new Panel(cargaPanel);
		periodoPanel.setLayout(new HorizontalLayout());
		
		new Label(periodoPanel)
		.setText("Período: ")
		.setFontSize(11);
		
		new TextBox(periodoPanel)
		.setWidth(500)
		.setHeigth(15)
		.bindValueToProperty("periodo");
		
		new Button(cargaPanel) //
		.setCaption("Cargar cuenta de empresa") //
		.onClick(this::cargarCuenta).setFontSize(9).setHeigth(22);

		Panel cuentaPanel = new Panel(cargaPanel);
		cuentaPanel.setLayout(new HorizontalLayout());

		new FileSelector(cuentaPanel)
		   .setCaption("Abrir archivo de cuenta: ")
		   .bindValueToProperty("archivoDeCuenta");
		
		new Button(cuentaPanel) //
		.setCaption("Consultar cuentas de empresa") //
		.onClick(this::consultarCuenta).setFontSize(9);
	
	}
	
	public void cargarCuenta() {
		//Llamada al metodo del ViewModel que procesa el archivo y limpia los campos
	}

	public void consultarCuenta() {
		Dialog<?> dialog = new ConsultaDeCuentasView(this);
		dialog.open();
	}
}
