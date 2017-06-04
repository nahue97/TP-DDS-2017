package model.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ExceptionsPackage.IndicadorNotFoundException;
import dtos.CargaDeArchivoTxtJsonDTO;
import model.Indicador;
import utils.AppData;

public class RepositorioIndicadores {

	// Singleton
	private static RepositorioIndicadores instance;

	private List<Indicador> indicadores = new ArrayList<Indicador>();

	private static CargaDeArchivoTxtJsonDTO dtoIndicadores = new CargaDeArchivoTxtJsonDTO();

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public static void setDtoIndicadores(CargaDeArchivoTxtJsonDTO _dtoIndicadores) {
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

	// TODO: Todavía no está terminado este método, falta completarlo cuando
	// avancemos con el modelado de los filtros como quedamos con Julián.
	public List<Indicador> filtrarIndicadores(String empresa, String nombre, String periodo, Double valor) {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(indicadores);
		if (!nombre.isEmpty())
			_indicadores = filtrarIndicadoresPorNombre(nombre, _indicadores);
		return _indicadores;
	}

	private List<Indicador> filtrarIndicadoresPorNombre(String nombre, List<Indicador> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> nombre.equals(indicador.getNombre()))
				.collect(Collectors.toList());
		return _indicadores;
	}

	public List<Indicador> filtrarIndicadoresPorEmpresa(String empresa, List<Indicador> _indicadores) {
		// TODO: FILTRO POR EMPRESA
		return null;
	}

}
