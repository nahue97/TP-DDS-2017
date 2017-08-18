package ui.vm;

import java.util.ArrayList;
import java.util.List;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioMetodologias;

@Observable
public class CargaDeMetodologiasViewModel {

	public String nombreRegla = "", nombre = "", indicador = "", criterio = "", comparador = "", valorAComparar = "";
	public List<String> reglas = new ArrayList<String>();
	public List<ReglaComparativa> reglasComparativas = new ArrayList<ReglaComparativa>();
	public List<ReglaTaxativa> reglasTaxativas = new ArrayList<ReglaTaxativa>();

	// Setters

	public void setNombreRegla(String _nombreRegla) {
		nombreRegla = _nombreRegla;
	}

	public void setNombre(String _nombre) {
		nombreRegla = _nombre;
	}

	public void setReglas(List<String> _reglas) {
		reglas = _reglas;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public void setReglasTaxativas(List<ReglaTaxativa> reglasTaxativas) {
		this.reglasTaxativas = reglasTaxativas;
	}

	public void setReglasComparativas(List<ReglaComparativa> reglasComparativas) {
		this.reglasComparativas = reglasComparativas;
	}
	
	public void setComparador(String comparador) {
		this.comparador = comparador;
	}

	public void setValorAComparar(String valorAComparar) {
		this.valorAComparar = valorAComparar;
	}
	// Getters

	public String getComparador() {
		return comparador;
	}
	
	public String getValorAComparar() {
		return valorAComparar;
	}

	public String getIndicador() {
		return indicador;
	}

	public String getCriterio() {
		return criterio;
	}

	public List<ReglaTaxativa> getReglasTaxativas() {
		return reglasTaxativas;
	}

	public List<String> getReglas() {
		return reglas;
	}

	public String getNombreRegla() {
		return nombreRegla;
	}

	public String getNombre() {
		return nombre;
	}

	public List<ReglaComparativa> getReglasComparativas() {
		return reglasComparativas;
	}

	public void cargarMetodologia(String nombreMetodologia) {
		if (reglas.isEmpty())
			throw new UserException("Debe crear al menos una regla");
		else {
			List<Regla> reglas = RepositorioMetodologias.getInstance().getReglasTemporales();
			Metodologia metodologia = new Metodologia(nombreMetodologia, reglas);
			RepositorioMetodologias.getInstance().agregarMetodologia(metodologia);
			reglasComparativas = RepositorioMetodologias.getInstance().getReglasComparativasTemporales();
			reglasTaxativas = RepositorioMetodologias.getInstance().getReglasTaxativasTemporales();
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
