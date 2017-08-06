package model;

public class EmpresaEvaluadaPorMetodologia {

	private String nombreEmpresa;
	private String conveniencia;

	public EmpresaEvaluadaPorMetodologia(String nombreEmpresa, String conveniencia) {
		this.nombreEmpresa = nombreEmpresa;
		this.conveniencia = conveniencia;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public String getConveniencia() {
		return conveniencia;
	}

	public void setConveniencia(String conveniencia) {
		this.conveniencia = conveniencia;
	}

}
