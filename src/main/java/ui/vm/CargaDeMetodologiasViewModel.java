package ui.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.Metodologia;
import model.Regla;
import model.repositories.RepositorioMetodologias;

@Observable
public class CargaDeMetodologiasViewModel {

	public String nombreRegla = "";
	public List<Regla> reglasTemporales = new ArrayList<>();
	public List<String> reglas = new ArrayList<String>();

	// Setters

	public void setNombreRegla(String _nombreRegla) {
		nombreRegla = _nombreRegla;
	}

	public void setReglas(List<String> _reglas) {
		reglas = _reglas;
	}

	// Getters

	public List<String> getReglas() {
		return reglas;
	}

	public String getNombreRegla() {
		return nombreRegla;
	}

	public void cargarMetodologia(String nombreMetodologia) {
		if (reglas.isEmpty())
			throw new UserException("Debe crear al menos una regla");
		else {
			Metodologia metodologia = new Metodologia(nombreMetodologia, reglasTemporales);
			RepositorioMetodologias.getInstance().agregarMetodologia(metodologia);
			vaciarReglasTemporales();
		}
	}

	public void eliminarRegla() {
		for (Regla regla : reglasTemporales) {
			if (regla.getNombre().equals(nombreRegla)) {
				reglasTemporales.remove(regla);
				return;
			}
		}
		reglas.stream().filter(n -> !n.equals(nombreRegla));

		this.refrescarReglas();
	}

	public void refrescarReglas() {
		List<Regla> _reglas = new ArrayList<>();
		_reglas.addAll(reglasTemporales);
		reglas = _reglas.stream().map(regla -> regla.getNombre()).collect(Collectors.toList());
	}

	public void agregarReglaTemporal(Regla regla) {
		reglasTemporales.add(regla);
	}

	public void vaciarReglasTemporales() {
		reglasTemporales = new ArrayList<>();
		refrescarReglas();
	}

	public List<Regla> getReglasTemporales() {
		return reglasTemporales;
	}
	
	public void setReglasTemporales(List<Regla> reglasTemporales) {
		this.reglasTemporales = reglasTemporales;
	}
}
