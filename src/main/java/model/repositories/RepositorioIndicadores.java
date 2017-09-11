package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ExceptionsPackage.IndicadorNotFoundException;
import dtos.PathFile;
import model.Indicador;
import model.IndicadorCalculado;
import utils.AppData;
import utils.CalculadorDeIndicadores;

public class RepositorioIndicadores {

	// Singleton
	private static RepositorioIndicadores instance;

	private List<Indicador> indicadores = new ArrayList<Indicador>();

	private PathFile dtoIndicadores;

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setDtoIndicadores(PathFile _dtoIndicadores) {
		dtoIndicadores = _dtoIndicadores;
	}

	public int size() {
		return indicadores.size();
	}

	public RepositorioIndicadores() {
		super();
	}

	public static synchronized RepositorioIndicadores getInstance() {
		if (instance == null)
			instance = new RepositorioIndicadores();
		return instance;
	}

	public void limpiarRepositorio() {
		indicadores = new ArrayList<Indicador>();
	}

	public void archivarRepositorio() {
		AppData.getInstance().guardar(indicadores, dtoIndicadores);
	}

	public void agregarIndicador(Indicador indicador) {
		Indicador _indicador = indicador;

		indicadores.add(_indicador);
		archivarRepositorio();
	}

	public void agregarIndicadores(List<Indicador> _indicadores) {
		for (Indicador indicador : _indicadores)
			agregarIndicador(indicador);
	}

	public void removerIndicador(Indicador indicador) {
		if (indicadores.contains(indicador)) {
			indicadores.remove(indicador);
			archivarRepositorio();
		} else {
			throw new IndicadorNotFoundException("El indicador no existe");
		}
	}

	public void removerIndicadorPorId(Long id) {
		removerIndicador(getIndicadorPorId(id));
		archivarRepositorio();
	}

	public Indicador getIndicadorPorId(Long id) {
		for (Indicador indicador : indicadores) {
			if (indicador.getId() == id) {
				return indicador;
			}
		}
		throw new IndicadorNotFoundException("No se encuentra un indicador con ID: " + id);
	}

	public String getFormulaDeIndicador(String nombreIndicador) {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(indicadores);
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
		_indicadores.addAll(indicadores);
		List<String> nombres = _indicadores.stream().map(indicador -> indicador.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}

	// Este es el �nico que imorta para los que no son calculados
	public List<Indicador> filtrarIndicadoresPorNombre(String nombre) {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(indicadores);

		if (!nombre.isEmpty())
			_indicadores = _indicadores.stream().filter(indicador -> nombre.equals(indicador.getNombre()))
					.collect(Collectors.toList());
		return _indicadores;
	}

	public List<IndicadorCalculado> filtrarIndicadores(String empresa, String nombre, String periodo,
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

	public Indicador getIndicadorPorNombre(String nombreIndicador) {
		for (Indicador indicador : indicadores) {
			if (indicador.getNombre().equals(nombreIndicador)) {
				return indicador;
			}
		}
		throw new IndicadorNotFoundException("No se encuentra un indicador con nombre: " + nombreIndicador);
	}

}
