package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

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
import utils.AmazingTransactionManager;

public class RepositorioIndicadores {

	// Singleton
	private static RepositorioIndicadores instance;

	private List<Indicador> indicadores = new ArrayList<Indicador>();

	private PathFile dtoIndicadores;

	public List<Indicador> getIndicadores() {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Indicador> result = entityManager.createQuery("SELECT i FROM Indicador i", Indicador.class);
		List<Indicador> indicadores = result.getResultList();
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

	/*public void limpiarRepositorio() {
		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
		EntityTransaction transaction = transactionManager.getTransaction();
		transactionManager.beginTransaction(transaction);
		try {
			indicadores = new ArrayList<Indicador>();
			transactionManager.commitTransaction(transaction);
		} catch (Throwable e) {
			transactionManager.rollbackTransaction(transaction);
			throw new TransactionException(e.getMessage());
		}
	}

	public void archivarRepositorio() {
		AppData.getInstance().guardar(indicadores, dtoIndicadores);
	}*/

	public void agregarIndicador(Indicador indicador) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(indicador);
		tx.commit();
		
/*		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
		transactionManager.beginTransaction();
		try {
			indicadores.add(indicador);
			transactionManager.commitTransaction();
			archivarRepositorio();
		} catch (Throwable e) {
			transactionManager.rollbackTransaction();
			throw new TransactionException(e.getMessage());
		}
*/	}

	public void agregarIndicadores(List<Indicador> _indicadores) {
		for (Indicador indicador : _indicadores)
			agregarIndicador(indicador);
	}

	public void removerIndicador(Indicador indicador) {
		EntityManager transactionManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = transactionManager.getTransaction();
		transaction.begin();
		try {
			if (getIndicadores().contains(indicador)) {
				indicadores.remove(indicador);
				//archivarRepositorio();
				transaction.commit();
			} else {
				transaction.rollback();
				throw new IndicadorNotFoundException("El indicador no existe");
			}
		} catch (Throwable e) {
			transaction.rollback();
			throw new TransactionException(e.getMessage());
		}
	}

	public void removerIndicadorPorId(Long id) {
		removerIndicador(getIndicadorPorId(id));
	}

	public Indicador getIndicadorPorId(Long id) {
		for (Indicador indicador : getIndicadores()) {
			if (indicador.getId() == id) {
				return indicador;
			}
		}
		throw new IndicadorNotFoundException("No se encuentra un indicador con ID: " + id);
	}

	public String getFormulaDeIndicador(String nombreIndicador) {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(getIndicadores());
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
		_indicadores.addAll(getIndicadores());
		List<String> nombres = _indicadores.stream().map(indicador -> indicador.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}

	// Este es el �nico que imorta para los que no son calculados
	public List<Indicador> filtrarIndicadoresPorNombre(String nombre) {
		List<Indicador> _indicadores = new ArrayList<>();
		_indicadores.addAll(getIndicadores());

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
		for (Indicador indicador : getIndicadores()) {
			if (indicador.getNombre().equals(nombreIndicador)) {
				return indicador;
			}
		}
		throw new IndicadorNotFoundException("No se encuentra un indicador con nombre: " + nombreIndicador);
	}

}
