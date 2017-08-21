package ui;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.List;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import model.Regla;
import ui.vm.AgregarReglasViewModel;
import ui.vm.CargaDeMetodologiasViewModel;

@SuppressWarnings("serial")
public class CargaDeMetodologiasView extends Dialog<CargaDeMetodologiasViewModel> {

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

		new Label(cargaPanel).setText("Reglas de mi metodologia").setFontSize(11);

		Panel reglasPanel = new Panel(cargaPanel);
		reglasPanel.setLayout(new HorizontalLayout());

		List<String> lstReglas = new List<String>(reglasPanel);
		lstReglas.bindItemsToProperty("reglas");
		lstReglas.bindValueToProperty("nombreRegla");
		lstReglas.setWidth(220);
		lstReglas.setHeight(220);

		Panel botonesPanel = new Panel(reglasPanel);
		botonesPanel.setLayout(new VerticalLayout());

		new Button(botonesPanel).setCaption("Nueva Regla").onClick(this::irAReglas).setFontSize(11).setWidth(200);

		new Label(botonesPanel).setText("Seleccione una regla a eliminar").setFontSize(10);

		new Button(botonesPanel).setCaption("Eliminar Regla").onClick(this::eliminarRegla).setFontSize(11)
				.setWidth(200);
		/*
		 * new Button(botonesPanel).setCaption("Refrescar").onClick(this::
		 * refrescarReglas).setFontSize(11).setWidth(100);
		 */

		new Button(cargaPanel).setCaption("Guardar metodologia").onClick(this::guardarMetodologia).setFontSize(11)
				.setWidth(200);
	}

	public void irAReglas() {
		Dialog<AgregarReglasViewModel> dialog = new AgregarReglasView(this);
		dialog.open();
	}

	public void eliminarRegla() {
		getModelObject().eliminarRegla();
	}

	protected void guardarMetodologia() {
		getModelObject().cargarMetodologia(nombreMetodologia);
		this.close();
	}

	public void agregarReglaYRefrescar(Regla regla) {
		getModelObject().agregarReglaTemporal(regla);
		getModelObject().refrescarReglas();
	}
	
	

	@Override
	protected void createFormPanel(Panel mainPanel) {
	}

}
