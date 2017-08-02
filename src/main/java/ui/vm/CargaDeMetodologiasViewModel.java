package ui.vm;

import java.util.ArrayList;
import java.util.List;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.Metodologia;
import model.Regla;
import model.repositories.RepositorioMetodologias;

@Observable
public class CargaDeMetodologiasViewModel {
	
	public String nombreRegla = "";
	public List<String> reglas = new ArrayList<String>();
	
	// Setters
	
	public void setNombreRegla(String _nombreRegla){
		nombreRegla = _nombreRegla;
	}

	public void setReglas(List<String> _reglas){
		reglas = _reglas;
	}
	
	// Getters
	
	public List<String> getReglas(){
		return reglas;
	}
	
	public String getNombreRegla(){
		return nombreRegla;
	}
		
	public void cargarMetodologia(String nombreMetodologia){
		if (reglas.isEmpty())
			throw new UserException("Debe crear al menos una regla");
		else{
			List<Regla> reglas = RepositorioMetodologias.getInstance().getReglasTemporales();
			Metodologia metodologia = new Metodologia(nombreMetodologia, reglas);
			RepositorioMetodologias.getInstance().agregarMetodologia(metodologia);
			RepositorioMetodologias.getInstance().vaciarReglasTemporales();
		}
	}

	public void eliminarRegla() {
		RepositorioMetodologias.getInstance().eliminarReglaTemporal(nombreRegla);
	}

	public void refrescarReglas() {
		reglas = RepositorioMetodologias.getInstance().getNombresReglasTemporales();
	}
}
