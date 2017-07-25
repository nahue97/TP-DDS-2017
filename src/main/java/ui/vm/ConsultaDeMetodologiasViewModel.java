package ui.vm;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.model.UserException;
import model.EmpresaEvaluadaPorMetodologia;
import model.Metodologia;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioDeMetodologias;
import utils.CalculadorDeMetodologias;

public class ConsultaDeMetodologiasViewModel {
	
	private String empresa = "", conveniencia = "", metodologia = "", periodoInicio = "", periodoFin = "";
	private List<String> metodologias = RepositorioDeMetodologias.getInstance().getNombresDeMetodologias();
	private List<String> periodos = RepositorioCuentas.getInstance().getPeriodosDeCuenta();
	private List<EmpresaEvaluadaPorMetodologia> empresasEvaluadasPorMetodologias;
	
	public void setUp() {
		BasicConfigurator.configure();		
	}
	
	public void consultarMetodologia() {		
		if (metodologia.isEmpty()) {
			throw new UserException("Debe seleccionar una metodologia");
		}
		if (periodoInicio.isEmpty()) {
			throw new UserException("Debe seleccionar un periodo de inicio");
		}
		if(periodoFin.isEmpty()){
			throw new UserException("Debe seleccionar un periodo de fin");
		}
		if(Integer.parseInt(periodoInicio)<Integer.parseInt(periodoFin)){
			throw new UserException("El periodo de fin no puede ser menor que el de inicio");
		}
		else{
		Metodologia metodologiaM = RepositorioDeMetodologias.getInstance().getMetodologiaPorNombre(metodologia);
		empresasEvaluadasPorMetodologias = CalculadorDeMetodologias.getInstance().calcularMetodologiaPorPeriodo(metodologiaM, periodoInicio, periodoFin);
		}
	}

	// GETTERS
	
	public String getPeriodoInicio() {
		return periodoInicio;
	}

	public String getPeriodoFin() {
		return periodoFin;
	}

	public String getEmpresa() {
		return empresa;
	}
	
	public String getMetodologia() {
		return metodologia;
	}

	public String getConveniencia() {
		return conveniencia;
	}
	
	public List<String> getMetodologias() {
		return metodologias;
	}
	
	public List<String> getPeriodos() {
		return periodos;
	}

	// SETTERS
	public void setPeriodoFin(String periodoFin) {
		this.periodoFin = periodoFin;
	}

	public void setPeriodos(List<String> periodos) {
		this.periodos = periodos;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}
	
	public void setConveniencia(String conveniencia) {
		this.conveniencia = conveniencia;
	}

	public void setPeriodoInicio(String periodoInicio) {
		this.periodoInicio = periodoInicio;
	}
	
	public void setMetodologias(List<String> metodologias) {
		this.metodologias = metodologias;
	}

}
