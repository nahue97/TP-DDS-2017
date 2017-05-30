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
import org.uqbar.arena.windows.MessageBox;

import model.Indicador;
import ui.vm.CrearIndicadorViewModel;

@SuppressWarnings("serial")
public class CrearIndicadorView extends Dialog<CrearIndicadorViewModel>{
	
	public CrearIndicadorView(WindowOwner owner){
		super(owner, new CrearIndicadorViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle("Crear Indicador");
		mainPanel.setLayout(new HorizontalLayout());
		
		Panel tablaTiposPanel = new Panel(mainPanel);
		tablaTiposPanel.setLayout(new VerticalLayout());
		
		new Label(tablaTiposPanel)
		.setText("Tipos de Cuenta")
		.setFontSize(11)
		.setWidth(200);
		
		this.tablaVerTiposDeCuenta(tablaTiposPanel);
		
		Panel introducirDatosPanel = new Panel(mainPanel);
		introducirDatosPanel.setLayout(new VerticalLayout());
		
		new Label(introducirDatosPanel)
		.setText("Fórmula: ")
		.setFontSize(11)
		.setWidth(200);
		
		new TextBox(introducirDatosPanel)
		.setWidth(200)
		.setHeigth(20)
		.bindValueToProperty("formula");
		
		new Label(introducirDatosPanel)
		.setText("Nombre del indicador: ")
		.setFontSize(11)
		.setWidth(200);
		
		new TextBox(introducirDatosPanel)
		.setWidth(200)
		.setHeigth(20)
		.bindValueToProperty("nombreDelIndicador");
		
		new Button(introducirDatosPanel)
		.setCaption("Crear Indicador")
		.onClick(this::crearIndicador)
		.setAsDefault()
		.setWidth(47);
		
		Panel tablaIndicadoresPanel = new Panel(mainPanel);
		tablaIndicadoresPanel.setLayout(new VerticalLayout());
		
		new Label(tablaIndicadoresPanel)
		.setText("Indicadores")
		.setFontSize(11)
		.setWidth(200);
		
		this.tablaVerOtrosIndicadores(tablaIndicadoresPanel);
	}
	
	protected void tablaVerTiposDeCuenta(Panel unPanel) {
		Table<String> tableTiposDeCuenta = new Table<String>(unPanel);
		tableTiposDeCuenta.setHeigth(100);
		
		tableTiposDeCuenta.bindItemsToProperty("tiposDeCuenta");
	}
	
	protected void tablaVerOtrosIndicadores(Panel unPanel) {
		Table<Indicador> tablaIndicadores = new Table<Indicador>(unPanel, Indicador.class);
		tablaIndicadores.setHeigth(100);
		tablaIndicadores.bindItemsToProperty("indicadores");
		
		Column<Indicador> columnaTipo = new Column<Indicador>(tablaIndicadores);
			columnaTipo.setFont(11).setTitle("ID");
			columnaTipo.setFixedSize(150);
			columnaTipo.bindContentsToProperty("id");
		
		Column<Indicador> columnaPeriodo = new Column<Indicador>(tablaIndicadores);
			columnaPeriodo.setFont(11).setTitle("Nombre");
			columnaPeriodo.setFixedSize(150);
			columnaPeriodo.bindContentsToProperty("nombre");
	}
	
	public void crearIndicador() {
		try {
			getModelObject().crearIndicador();
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
}