package model.componentes;


public abstract class OperacionBinaria implements Componente {
	
	private Componente componente1;
	private Componente componente2;

	public OperacionBinaria(Componente componente1, Componente componente2) {
		super();
		this.componente1 = componente1;
		this.componente2 = componente2;
	}

	public Componente getComponente1() {
		return componente1;
	}

	public void setComponente1(Componente componente1) {
		this.componente1 = componente1;
	}

	public Componente getComponente2() {
		return componente2;
	}

	public void setComponente2(Componente componente2) {
		this.componente2 = componente2;
	}
	
}
