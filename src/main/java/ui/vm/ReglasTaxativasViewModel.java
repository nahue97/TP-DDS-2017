package ui.vm;

import java.util.List;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import com.google.common.collect.Lists;
import java.math.BigDecimal;
import model.Indicador;
import model.ReglaTaxativa;
import model.repositories.RepositorioMetodologias;
import model.repositories.RepositorioIndicadores;

@Observable
public class ReglasTaxativasViewModel {

	private String nombreRegla = "", valorAComparar = "", indicador = "", comparador = "";
	private List<String> indicadores = RepositorioIndicadores.getInstance().getNombresDeIndicadores();
	private List<String> comparadores = Lists.newArrayList(">", "<", "=", "!=");
	BigDecimal _valorAComparar;

	public ReglaTaxativa crearRegla() {
		if (nombreRegla.isEmpty()) {
			throw new UserException("Debe ingresar un nombre");
		}
		if (valorAComparar.isEmpty()) {
			throw new UserException("El valor a comparar esta vacio");
		}
		if (indicador.isEmpty()) {
			throw new UserException("Debe seleccionar un indicador");
		}
		if (comparador.isEmpty()) {
			throw new UserException("Debe seleccionar un comparador");
		} else {
			char _comparador = comparador.charAt(0);
			Indicador _indicador = RepositorioIndicadores.getInstance().getIndicadorPorNombre(indicador);
			try {
				_valorAComparar = new BigDecimal(valorAComparar);
			} catch (Exception e) {
				throw new UserException("Debe ingresar un valor numerico");
			}
			return new ReglaTaxativa(nombreRegla, _indicador, _comparador, _valorAComparar);
		}
	}

	// GETTERS

	public List<String> getComparadores() {
		return comparadores;
	}

	public String getIndicador() {
		return indicador;
	}

	public String getComparador() {
		return comparador;
	}

	public String getValorAComparar() {
		return valorAComparar;
	}

	public List<String> getIndicadores() {
		return indicadores;
	}

	public String getNombreRegla() {
		return nombreRegla;
	}

	// SETTERS

	public void setValorAComparar(String valorAComparar) {
		this.valorAComparar = valorAComparar;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public void setComparador(String comparador) {
		this.comparador = comparador;
	}

	public void setIndicadores(List<String> indicadores) {
		this.indicadores = indicadores;
	}

	public void setNombreRegla(String nombreRegla) {
		this.nombreRegla = nombreRegla;
	}
}
