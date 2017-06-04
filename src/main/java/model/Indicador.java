package model;

public class Indicador {
	private int id;
	private String nombre;
	private String formula;

	public Indicador(String nombre, String formula) {
		super();
		this.nombre = nombre;
		this.formula = formula;
	}

	public Double obtenerValor(String empresa, String periodo) {
		return 0.0;
	}

	public String obtenerPeriodo() {
		// ac� deber�amos tener los periodos de las cuentas que componen el
		// indicador
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
}