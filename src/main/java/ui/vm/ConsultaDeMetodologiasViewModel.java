package ui.vm;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.uqbar.commons.model.UserException;

import model.Metodologia;
import model.MetodologiaCalculada;
import model.repositories.RepositorioDeMetodologias;
import utils.CalculadorDeMetodologias;

public class ConsultaDeMetodologiasViewModel {
	
	private String empresa = "", conveniencia = "", metodologia = "";
	private List<String> metodologias = RepositorioDeMetodologias.getInstance().getNombresDeMetodologias();

	public void setUp() {
		BasicConfigurator.configure();		
	}
	
	public void consultarMetodologia() {		
		if (metodologia.isEmpty()) {
			throw new UserException("Debe seleccionar una metodologia");
		}else {
		CalculadorDeMetodologias.getInstance();//.calcularMetodologia(metodologia);
		}
	}

	// GETTERS
	
	public String getEmpresa() {
		return empresa;
	}

	public String getConveniencia() {
		return conveniencia;
	}

	// SETTERS
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public void setConveniencia(String conveniencia) {
		this.conveniencia = conveniencia;
	}

}
