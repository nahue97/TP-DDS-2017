package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ExceptionsPackage.IndicadorNotFoundException;
import dtos.CargaDeArchivoTxtJsonDTO;
import model.Indicador;
import model.IndicadorCalculado;
import utils.AppData;

public class RepositorioIndicadores {

	// Singleton
	private static RepositorioIndicadores instance;

	private List<Indicador> indicadores = new ArrayList<Indicador>();

	private CargaDeArchivoTxtJsonDTO dtoIndicadores = new CargaDeArchivoTxtJsonDTO();

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setDtoIndicadores(CargaDeArchivoTxtJsonDTO _dtoIndicadores) {
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

		_indicador.setId(getIdForNextIndicador());

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

	public void removerIndicadorPorId(int id) {
		removerIndicador(getIndicadorPorId(id));
		archivarRepositorio();
	}

	private int getIdForNextIndicador() {
		if (size() != 0) {
			Indicador ultimoIndicador = indicadores.get(size() - 1);
			return ultimoIndicador.getId() + 1;
		} else
			return 0;
	}

	public Indicador getIndicadorPorId(int id) {
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

	// TODO: Todavía no está terminado este método, falta completarlo cuando
	// avancemos con el modelado de los filtros como quedamos con Julián.
	public List<IndicadorCalculado> filtrarIndicadores(String empresa, String nombre, String periodo, String valor) {
		List<IndicadorCalculado> _indicadores;
		_indicadores = calcularTodosLosIndicadores();

		if (!nombre.isEmpty())
			_indicadores = filtrarIndicadoresCalculadosPorNombre(nombre, _indicadores);
		if (!empresa.isEmpty())
			_indicadores = filtrarIndicadoresPorEmpresa(empresa, _indicadores);
		if (!periodo.isEmpty())
			_indicadores = filtrarIndicadoresPorPeriodo(periodo, _indicadores);
		if (!valor.isEmpty())
			_indicadores = filtrarIndicadoresPorValor(valor, _indicadores);

		return _indicadores;
	}

	private List<IndicadorCalculado> filtrarIndicadoresCalculadosPorNombre(String nombre,
			List<IndicadorCalculado> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> nombre.equals(indicador.getNombre()))
				.collect(Collectors.toList());
		return _indicadores;
	}

	private List<IndicadorCalculado> filtrarIndicadoresPorEmpresa(String empresa,
			List<IndicadorCalculado> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> empresa.equals(indicador.getEmpresa()))
				.collect(Collectors.toList());
		return _indicadores;
	}

	private List<IndicadorCalculado> filtrarIndicadoresPorPeriodo(String periodo,
			List<IndicadorCalculado> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> periodo.equals(indicador.getPeriodo()))
				.collect(Collectors.toList());
		return _indicadores;
	}

	private List<IndicadorCalculado> filtrarIndicadoresPorValor(String valor, List<IndicadorCalculado> _indicadores) {
		BigDecimal valorNumerico = new BigDecimal(valor);
		_indicadores = _indicadores.stream().filter(indicador -> valorNumerico.compareTo(indicador.getValor()) == 0)
				.collect(Collectors.toList());
		return _indicadores;
	}

	private List<IndicadorCalculado> calcularIndicadores(String empresa, String periodo, List<Indicador> _indicadores) {
		List<IndicadorCalculado> calculados = new ArrayList<>();

		for (Indicador indicador : _indicadores)
			calculados.add(new IndicadorCalculado(indicador, empresa, periodo));
		return calculados;
	}

	private List<IndicadorCalculado> calcularTodosLosIndicadores() {
		Collection<String> empresasDeCuentas;
		empresasDeCuentas = RepositorioCuentas.getInstance().getEmpresasDeCuentas();
		Collection<String> periodosDeCuenta;
		periodosDeCuenta = RepositorioCuentas.getInstance().getPeriodosDeCuenta();
		List<IndicadorCalculado> calculados = new ArrayList<>();

		for (String empresa : empresasDeCuentas)
			for (String periodo : periodosDeCuenta)
				calculados.addAll(calcularIndicadores(empresa, periodo, indicadores));
		return calculados;
	}

}
