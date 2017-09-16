package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ExceptionsPackage.CuentaNotFoundException;
import ExceptionsPackage.TransactionException;
import dtos.PathFile;
import model.Cuenta;
import model.Empresa;
import utils.AppData;
import utils.AmazingTransactionManager;

public class RepositorioCuentas {

	// Singleton
	private static RepositorioCuentas instance;

	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	private PathFile dtoCuentas;

	public int size() {
		return cuentas.size();
	}

	public void setDtoCuentas(PathFile _dtoCuentas) {
		dtoCuentas = _dtoCuentas;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public RepositorioCuentas() {
		super();
	}

	public static synchronized RepositorioCuentas getInstance() {
		if (instance == null)
			instance = new RepositorioCuentas();
		return instance;
	}

	public void limpiarRepositorio() {
		// AmazingTransactionManager transactionManager = new
		// AmazingTransactionManager();
		// EntityTransaction transaction = transactionManager.getTransaction();
		// transactionManager.beginTransaction(transaction);
		try {
			cuentas = new ArrayList<Cuenta>();
			// transactionManager.commitTransaction(transaction);
		} catch (Throwable e) {
			// transactionManager.rollbackTransaction(transaction);
			throw new TransactionException(e.getMessage());
		}
	}

	public void archivarRepositorio() {
		AppData.getInstance().guardar(cuentas, dtoCuentas);
	}

	public void agregarCuentas(List<Cuenta> _cuentas) {
		for (Cuenta cuenta : _cuentas) {
			agregarCuenta(cuenta);
		}
		// _cuentas.forEach(this::agregarCuenta);
	}

	public void agregarCuenta(Cuenta cuenta) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		Empresa empresa = cuenta.getEmpresaCuenta();
		if (!existeEmpresa(empresa)) {
			entityManager.persist(empresa);
		}
		if (!existeCuenta(cuenta)) {
			cuenta.setEmpresaObject(empresa);
			entityManager.persist(cuenta);
			tx.commit();
			return;
		}
		tx.rollback();

		/*
		 * AmazingTransactionManager transactionManager = new
		 * AmazingTransactionManager(); EntityTransaction transaction =
		 * transactionManager.getTransaction();
		 * transactionManager.beginTransaction(transaction); try {
		 * cuentas.add(cuenta);
		 * transactionManager.commitTransaction(transaction); } catch (Throwable
		 * e) { transactionManager.rollbackTransaction(transaction); throw new
		 * TransactionException(e.getMessage()); }
		 */ }

	public boolean existeEmpresa(Empresa empresa) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();

		TypedQuery<Empresa> result = entityManager
				.createQuery("select e from Empresa e where e.nombre = '" + empresa.getNombre() + "'", Empresa.class);

		List<Empresa> empresas = result.getResultList();
		if (empresas.size() == 0) {
			return false;
		} else {
			empresa.setId(empresas.get(0).getId());
			return true;
		}
	}

	public boolean existeCuenta(Cuenta cuenta) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();

		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);

		List<Cuenta> cuentas = result.getResultList();
		boolean retorno = cuentas.stream()
				.anyMatch(c -> c.getEmpresaCuenta().getNombre().equals(cuenta.getEmpresaCuenta().getNombre())
						&& c.getPeriodo().equals(cuenta.getPeriodo()) && c.getTipo().equals(cuenta.getTipo())
						&& c.getValor().compareTo(cuenta.getValor()) == 0);
		return retorno;

	}

	public void removerCuenta(Cuenta cuenta) {
		// AmazingTransactionManager transactionManager = new
		// AmazingTransactionManager();
		// EntityTransaction transaction = transactionManager.getTransaction();
		// transactionManager.beginTransaction(transaction);
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();

		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);

		List<Cuenta> cuentas = result.getResultList();
		try {
			if (cuentas.contains(cuenta)) {
				cuentas.remove(cuenta);
				archivarRepositorio();
				tx.commit();
				// transactionManager.commitTransaction(transaction);
			} else {
				tx.rollback();
				throw new CuentaNotFoundException("La cuenta no existe");
			}
		} catch (Throwable e) {
			tx.rollback();
			// transactionManager.rollbackTransaction(transaction);
			throw new TransactionException(e.getMessage());
		}
	}

	public void removerCuentaPorId(Long id) {
		removerCuenta(getCuentaPorId(id));
	}

	public Cuenta getCuentaPorId(Long id) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);

		List<Cuenta> cuentas = result.getResultList();
		for (Cuenta cuenta : cuentas)
			if (cuenta.getId() == id)
				return cuenta;

		throw new CuentaNotFoundException("No se encuentra una cuenta con ID: " + id);
	}

	// Filtrar cuentas del repositorio

	public List<Cuenta> filtrarCuentas(String tipo, String empresa, String periodo, BigDecimal valor) {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		if (!periodo.isEmpty())
			_cuentas = filtrarCuentasPorPeriodo(periodo, _cuentas);
		if (!empresa.isEmpty())
			_cuentas = filtrarCuentasPorEmpresa(getEmpresaPorNombre(empresa), _cuentas);
		if (!tipo.isEmpty())
			_cuentas = filtrarCuentasPorTipo(tipo, _cuentas);
		if (valor != null)
			_cuentas = filtrarCuentasPorValor(valor, _cuentas);

		return _cuentas;
	}
	
	private Empresa getEmpresaPorNombre(String nombre){
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Empresa> result = entityManager
				.createQuery("select e from Empresa e where e.nombre = '" + nombre + "'", Empresa.class);
		List<Empresa> empresas = result.getResultList();
		if (empresas.size() != 0){
			return empresas.get(0);
		} else {
			return null;
		}
	}

	private List<Cuenta> filtrarCuentasPorTipo(String tipo, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> tipo.equals(cuenta.getTipo())).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> filtrarCuentasPorPeriodo(String periodo, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> periodo.equals(cuenta.getPeriodo())).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> filtrarCuentasPorEmpresa(Empresa empresa, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> cuenta.getEmpresaCuenta().equals(empresa)).collect(Collectors.toList());
		return _cuentas;
	}

	private List<Cuenta> filtrarCuentasPorValor(BigDecimal valor, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> cuenta.getValor().compareTo(valor) == 0)
				.collect(Collectors.toList());
		return _cuentas;
	}

	// Devuelven una lista ordenada de determinada manera, sin alterar las
	// propias del repositorio

	public List<Cuenta> getCuentasPorTipo() {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getTipo)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorEmpresa() {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getEmpresa)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorPeriodo() {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getPeriodo)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorValor() {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getValor)).collect(Collectors.toList());
		return _cuentas;
	}

	public Collection<String> getTiposDeCuenta() {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		Collection<String> tipos;
		tipos = _cuentas.stream().map(cuenta -> cuenta.getTipo()).sorted().collect(Collectors.toSet());
		return tipos;
	}

	public List<String> getPeriodosDeCuenta() {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		List<String> periodos = new ArrayList<String>();
		periodos.addAll(_cuentas.stream().map(cuenta -> cuenta.getPeriodo()).sorted().collect(Collectors.toSet()));
		Collections.sort(periodos);
		return periodos;
	}

	public List<String> getEmpresasDeCuentas() {
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);
		List<String> empresas = new ArrayList<String>();
		empresas.addAll(_cuentas.stream().map(cuenta -> cuenta.getEmpresa()).sorted().collect(Collectors.toSet()));
		Collections.sort(empresas);
		return empresas;
	}

	public List<String> getPeriodosParaEmpresa(String empresa) {
		List<String> periodos = new ArrayList<>();
		List<Cuenta> _cuentas = new ArrayList<>();
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		TypedQuery<Cuenta> result = entityManager.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
		List<Cuenta> cuentas = result.getResultList();
		
		_cuentas.addAll(cuentas);

		for (Cuenta cuenta : _cuentas) {
			if (cuenta.getEmpresa().equals(empresa)) {
				periodos.add(cuenta.getPeriodo());
			}
		}
		Collections.sort(periodos);
		return periodos;
	}
}
