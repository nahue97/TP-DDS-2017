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
import ui.vm.ConsultaDeMetodologiasViewModel;

@SuppressWarnings("serial")
public class ConsultaDeMetodologiasView extends Dialog<ConsultaDeMetodologiasViewModel>{

	public ConsultaDeMetodologiasView(WindowOwner owner) {
		super(owner, new ConsultaDeMetodologiasViewModel());
	}

	@Override
	protected void createFormPanel(Panel consultaPanel) {

		setTitle("Consulta de Metodologias");
		consultaPanel.setLayout(new VerticalLayout());
		
		Panel metodologiasPanel = new Panel(consultaPanel);
		metodologiasPanel.setLayout(new HorizontalLayout());

		new Label(metodologiasPanel).setText("Metodologia: ").setFontSize(11).setWidth(200);

	}
}