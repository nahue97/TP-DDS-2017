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

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ExceptionsPackage.CuentaNotFoundException;
import ExceptionsPackage.TransactionException;
import dtos.PathFile;
import model.Cuenta;
import model.Empresa;
import utils.AppData;
import utils.AmazingTransactionManager;

public class RepositorioCuentas{

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
//		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
//		EntityTransaction transaction = transactionManager.getTransaction();
//		transactionManager.beginTransaction(transaction);
		try {
			cuentas = new ArrayList<Cuenta>();
//			transactionManager.commitTransaction(transaction);
		} catch (Throwable e) {
//			transactionManager.rollbackTransaction(transaction);
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
		//_cuentas.forEach(this::agregarCuenta);
	}

	public void agregarCuenta(Cuenta cuenta) {
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		Empresa empresa = cuenta.getEmpresaCuenta();
		entityManager.persist(empresa);
		entityManager.persist(cuenta);
		tx.commit();
/*		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
		EntityTransaction transaction = transactionManager.getTransaction();
		transactionManager.beginTransaction(transaction);
		try {
			cuentas.add(cuenta);
			transactionManager.commitTransaction(transaction);
		} catch (Throwable e) {
			transactionManager.rollbackTransaction(transaction);
			throw new TransactionException(e.getMessage());
		}
*/	}

	public void removerCuenta(Cuenta cuenta) {
//		AmazingTransactionManager transactionManager = new AmazingTransactionManager();
//		EntityTransaction transaction = transactionManager.getTransaction();
//		transactionManager.beginTransaction(transaction);
		try {
			if (cuentas.contains(cuenta)) {
				cuentas.remove(cuenta);
				archivarRepositorio();
//				transactionManager.commitTransaction(transaction);		
			} else {
				throw new CuentaNotFoundException("La cuenta no existe");
			}
		} catch (Throwable e) {
//			transactionManager.rollbackTransaction(transaction);
			throw new TransactionException(e.getMessage());
		}
	}

	public void removerCuentaPorId(Long id) {
		removerCuenta(getCuentaPorId(id));
	}

	public Cuenta getCuentaPorId(Long id) {
		for (Cuenta cuenta : cuentas)
			if (cuenta.getId() == id)
				return cuenta;

		throw new CuentaNotFoundException("No se encuentra una cuenta con ID: " + id);
	}

	// Filtrar cuentas del repositorio

	public List<Cuenta> filtrarCuentas(String tipo, String empresa, String periodo, BigDecimal valor) {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		if (!periodo.isEmpty())
			_cuentas = filtrarCuentasPorPeriodo(periodo, _cuentas);
		if (!empresa.isEmpty())
			_cuentas = filtrarCuentasPorEmpresa(empresa, _cuentas);
		if (!tipo.isEmpty())
			_cuentas = filtrarCuentasPorTipo(tipo, _cuentas);
		if (valor != null)
			_cuentas = filtrarCuentasPorValor(valor, _cuentas);

		return _cuentas;
	}

	private List<Cuenta> filtrarCuentasPorTipo(String tipo, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> tipo.equals(cuenta.getTipo())).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> filtrarCuentasPorPeriodo(String periodo, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> periodo.equals(cuenta.getPeriodo())).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> filtrarCuentasPorEmpresa(String empresa, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> empresa.equals(cuenta.getEmpresa())).collect(Collectors.toList());
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
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getTipo)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorEmpresa() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getEmpresa)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorPeriodo() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getPeriodo)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorValor() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getValor)).collect(Collectors.toList());
		return _cuentas;
	}

	public Collection<String> getTiposDeCuenta() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		Collection<String> tipos;
		tipos = _cuentas.stream().map(cuenta -> cuenta.getTipo()).sorted().collect(Collectors.toSet());
		return tipos;
	}

	public List<String> getPeriodosDeCuenta() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		List<String> periodos = new ArrayList<String>();
		periodos.addAll(_cuentas.stream().map(cuenta -> cuenta.getPeriodo()).sorted().collect(Collectors.toSet()));
		Collections.sort(periodos);
		return periodos;
	}

	public List<String> getEmpresasDeCuentas() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		List<String> empresas = new ArrayList<String>();
		empresas.addAll(_cuentas.stream().map(cuenta -> cuenta.getEmpresa()).sorted().collect(Collectors.toSet()));
		Collections.sort(empresas);
		return empresas;
	}

	public List<String> getPeriodosParaEmpresa(String empresa) {
		List<String> periodos = new ArrayList<>();
		List<Cuenta> _cuentas = new ArrayList<>();
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
