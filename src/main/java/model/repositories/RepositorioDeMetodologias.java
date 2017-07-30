package model.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.uqbar.commons.model.UserException;
import ExceptionsPackage.MetodologiaNotFoundException;
import model.Metodologia;
import model.Regla;


public class RepositorioDeMetodologias {
	
	private static RepositorioDeMetodologias instance;

	private List<Regla> reglasTemporales = new ArrayList<Regla>();
	private List<Metodologia> metodologias = new ArrayList<Metodologia>();

	public RepositorioDeMetodologias(){
		super();
	}
	
	public static synchronized RepositorioDeMetodologias getInstance() {
		if (instance == null)
			instance = new RepositorioDeMetodologias();
		return instance;
	}
	
	public void agregarMetodologia(Metodologia metodologiaNueva){
		metodologias.add(metodologiaNueva);
	}
	
	public List<Metodologia> getMetodologias() {
		return metodologias;
	}
	
	public List<String> getNombresDeMetodologias() {
		List<Metodologia> _metodologias = new ArrayList<>();
		_metodologias.addAll(metodologias);
		List<String> nombres = _metodologias.stream().map(metodologia -> metodologia.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}
	
	public Metodologia getMetodologiaPorNombre(String nombreMetodologia){
		for (Metodologia metodologia : metodologias) {
			if (metodologia.getNombre() == nombreMetodologia) {
				return metodologia;
			}
		}
		throw new MetodologiaNotFoundException("No se encuentra una metodologia llamada " + nombreMetodologia);
	}

	public void existeNombreMetodologia(String nuevaMetodologia) {
		try {
			this.getMetodologiaPorNombre(nuevaMetodologia);
		}
		catch(MetodologiaNotFoundException e){
			return;
		}
		throw new UserException("El nombre de la metodologia ya existe");
	}

	public void agregarReglaTemporal(Regla regla) {
		reglasTemporales.add(regla);
	}

	public List<Regla> getReglasTemporales() {		
		return reglasTemporales;
	}

	public void eliminarReglaTemporal(String nombreRegla) {
		for (Regla regla : reglasTemporales) {
			if (regla.getNombre() == nombreRegla) {
				reglasTemporales.remove(regla);
				return;
			}
		}
	}

	public List<String> getNombresReglasTemporales() {
		List<Regla> _reglas = new ArrayList<>();
		_reglas.addAll(reglasTemporales);
		List<String> nombres = _reglas.stream().map(regla -> regla.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}
}
