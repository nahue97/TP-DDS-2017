package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import ExceptionsPackage.IndicadorNotFoundException;
import ExceptionsPackage.TransactionException;
import dtos.PathFile;
import model.Cuenta;
import model.Empresa;
import model.Indicador;
import model.IndicadorCalculado;
import utils.AppData;
import utils.CalculadorDeIndicadores;

public class RepositorioIndicadores extends Repositorio<Indicador> {

	// Singleton
	private static RepositorioIndicadores instance;

	public RepositorioIndicadores() {
		super();
	}

	public static synchronized RepositorioIndicadores getInstance() {
		if (instance == null)
			instance = new RepositorioIndicadores();
		return instance;
	}

	public void agregarIndicadores(List<Indicador> _indicadores) {
		for (Indicador indicador : _indicadores)
			this.add(indicador);
	}

	public String getFormulaDeIndicador(String nombreIndicador) {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(this.getAll());
		Optional<String> formulaIndicador = _indicadores.stream()
				.filter(_indicador -> _indicador.getNombre().equals(nombreIndicador)).map(_indic -> _indic.getFormula())
				.findFirst();
		if (formulaIndicador.isPresent()) {
			throw new IndicadorNotFoundException("Indicador no encontrado: " + nombreIndicador);
		} else {
			return formulaIndicador.get();
		}
	}

	public List<String> getNombresDeIndicadores() {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(this.getAll());
		List<String> nombres = _indicadores.stream().map(indicador -> indicador.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}

	// Este es el �nico que imorta para los que no son calculados
	public List<Indicador> filtrarIndicadoresPorNombre(String nombre) {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(this.getAll());

		if (!nombre.isEmpty())
			_indicadores = _indicadores.stream().filter(indicador -> nombre.equals(indicador.getNombre()))
					.collect(Collectors.toList());
		return _indicadores;
	}

	public List<IndicadorCalculado> filtrarIndicadores(Empresa empresa, String nombre, String periodo,
			BigDecimal valor) {
		List<IndicadorCalculado> _indicadores = new ArrayList<>();
		CalculadorDeIndicadores calculadorDeIndicadores = new CalculadorDeIndicadores();
		_indicadores.addAll(calculadorDeIndicadores.calcularIndicadores(empresa, periodo));

		if (!nombre.isEmpty())
			_indicadores = filtrarIndicadoresCalculadosPorNombre(nombre, _indicadores);
		if (valor != null)
			_indicadores = filtrarIndicadoresCalculadosPorValor(valor, _indicadores);

		return _indicadores;
	}

	private List<IndicadorCalculado> filtrarIndicadoresCalculadosPorNombre(String nombre,
			List<IndicadorCalculado> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> nombre.equals(indicador.getNombre()))
				.collect(Collectors.toList());
		return _indicadores;
	}

	private List<IndicadorCalculado> filtrarIndicadoresCalculadosPorValor(BigDecimal valor,
			List<IndicadorCalculado> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> valor.compareTo(indicador.getValor()) == 0)
				.collect(Collectors.toList());
		return _indicadores;
	}

	@Override
	protected Class<Indicador> getEntityType() {
		return Indicador.class;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Indicador indicador) {
		if (indicador.getId() != null) {
			criteria.add(Restrictions.eq("id", indicador.getId()));
		}
		if (indicador.getNombre() != null) {
			criteria.add(Restrictions.eq("nombre", indicador.getNombre()));
		}
		if (indicador.getFormula() != null) {
			criteria.add(Restrictions.eq("formula", indicador.getFormula()));
		}
	}

	public Indicador getIndicadorPorNombre(String nombreIndicador) {
		Indicador indicadorEjemplo = new Indicador(nombreIndicador, null);
		List<Indicador> resultadoBusqueda = RepositorioIndicadores.getInstance().searchByExample(indicadorEjemplo);
		if (resultadoBusqueda.size() != 0) {
			return resultadoBusqueda.get(0);
		}
		
		throw new IndicadorNotFoundException("No se encuentra un indicador con nombre: " + nombreIndicador);
	}

}
