package ui;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import ui.vm.ConsultaDeIndicadoresViewModel;

@SuppressWarnings("serial")
public class ConsultaDeIndicadoresView extends Dialog<ConsultaDeIndicadoresViewModel>{
	
	public ConsultaDeIndicadoresView(WindowOwner owner){
		super(owner, new ConsultaDeIndicadoresViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		// TODO Auto-generated method stub
		
	}


}