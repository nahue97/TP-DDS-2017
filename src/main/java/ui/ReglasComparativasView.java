package ui;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import ui.vm.ReglasComparativasViewModel;
import ui.vm.ReglasTaxativasViewModel;

public class ReglasComparativasView extends Dialog<ReglasComparativasViewModel> {

	public ReglasComparativasView(WindowOwner owner) {
		super(owner, new ReglasComparativasViewModel());
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		// TODO Auto-generated method stub

	}

}
