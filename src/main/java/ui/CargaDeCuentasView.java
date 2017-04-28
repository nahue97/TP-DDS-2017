package ui;

import java.awt.Color;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.widgets.FileSelector;
import ui.ConsultaDeCuentasView;
import ui.vm.CargaDeCuentasViewModel;

@SuppressWarnings("serial")
public class CargaDeCuentasView extends Window<CargaDeCuentasViewModel> {

	public CargaDeCuentasView(WindowOwner owner) {
		super(owner, new CargaDeCuentasViewModel());
	}

	@Override
	public void createContents(Panel cargaPanel) {

		setTitle("Sistema de análisis de inversiones");
		cargaPanel.setLayout(new VerticalLayout());

		Panel archivoPanel = new Panel(cargaPanel);
		archivoPanel.setLayout(new HorizontalLayout());

		new Label(archivoPanel).setText("Cargar cuenta de empresa").setFontSize(11);

		new FileSelector(archivoPanel).setCaption("Buscar").bindValueToProperty("pathFile");

		new Label(cargaPanel).setFontSize(7).bindValueToProperty("pathFile");
		
		new Label(cargaPanel).setFontSize(7).bindValueToProperty("estado");

		new Button(cargaPanel).setCaption("Cargar").onClick(this::cargarCuenta).setBackground(Color.GREEN);

		new Button(cargaPanel) //
				.setCaption("Consultar cuentas de empresa") //
				.onClick(this::irAConsultas).setBackground(Color.MAGENTA);

	}

	public void cargarCuenta() {
		try {
			getModelObject().cargarCuenta();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void irAConsultas() {
		Dialog<?> dialog = new ConsultaDeCuentasView(this, getModelObject().getCuentas());
		dialog.open();
	}
}
