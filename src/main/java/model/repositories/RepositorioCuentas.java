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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import ExceptionsPackage.CuentaNotFoundException;
import ExceptionsPackage.TransactionException;
import dtos.PathFile;
import dtos.PathFileTxtJson;
import model.Cuenta;
import model.Empresa;
import utils.AppData;

public class RepositorioCuentas extends Repositorio<Cuenta> {

	// Singleton
	private static RepositorioCuentas instance;

	public List<Cuenta> getCuentas() {
		return this.getAll();
	}

	public RepositorioCuentas() {
		super();
	}

	public static synchronized RepositorioCuentas getInstance() {
		if (instance == null)
			instance = new RepositorioCuentas();
		return instance;
	}

	public void agregarCuentas(List<Cuenta> _cuentas) {
		_cuentas.forEach(this::add);
	}

	// Filtrar cuentas del repositorio

	public List<Cuenta> filtrarCuentas(String tipo, Empresa empresa, String periodo, BigDecimal valor) {
		List<Cuenta> _cuentas = new ArrayList<>();
		Cuenta cuentaEjemplo = new Cuenta(tipo, empresa, periodo, valor);
		_cuentas.addAll(this.searchByExample(cuentaEjemplo));
		
		return _cuentas;
	}


	// Devuelven una lista ordenada de determinada manera, sin alterar las
	// propias del repositorio
	
	public List<Cuenta> getCuentasPorTipo() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(this.getAll());
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getTipo)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorPeriodo() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(this.getAll());
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getPeriodo)).collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorValor() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(this.getAll());
		_cuentas = _cuentas.stream().sorted(Comparator.comparing(Cuenta::getValor)).collect(Collectors.toList());
		return _cuentas;
	}

	public Collection<String> getTiposDeCuenta() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(this.getAll());
		Collection<String> tipos;
		tipos = _cuentas.stream().map(cuenta -> cuenta.getTipo()).sorted().collect(Collectors.toSet());
		return tipos;
	}

	public List<String> getPeriodosDeCuenta() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(this.getAll());
		List<String> periodos = new ArrayList<String>();
		periodos.addAll(_cuentas.stream().map(cuenta -> cuenta.getPeriodo()).sorted().collect(Collectors.toSet()));
		Collections.sort(periodos);
		return periodos;
	}

	public List<String> getPeriodosParaEmpresa(String empresa) {
		List<String> periodos = new ArrayList<>();
		Cuenta cuentaEjemplo = new Cuenta();
		cuentaEjemplo.setEmpresa(new Empresa(null, empresa));
		List<Cuenta> _cuentas = this.searchByExample(cuentaEjemplo);

		_cuentas.forEach(cuenta -> periodos.add(cuenta.getPeriodo()));
		
		Collections.sort(periodos);
		return periodos;
	}

	@Override
	protected Class<Cuenta> getEntityType() {
		return Cuenta.class;
	}
	
	public Cuenta getCuentaPorId(Long id) {
		Cuenta cuentaEjemplo = new Cuenta();
		cuentaEjemplo.setId(id);
		List<Cuenta> result = this.searchByExample(cuentaEjemplo);
		return result.get(0);
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Cuenta cuenta) {
		if (cuenta.getId() != null) {
			criteria.add(Restrictions.eq("id", cuenta.getId()));
		}
		if (cuenta.getEmpresa() != null) {
			if (cuenta.getEmpresa().getId() != null) {
				criteria.add(Restrictions.eq("empresa", cuenta.getEmpresa().getId()));
			} else if (cuenta.getEmpresa().getNombre() != null) {
				List<Empresa> empresasEncontradas = RepositorioEmpresas.getInstance()
						.searchByExample(new Empresa(null, cuenta.getEmpresa().getNombre()));
				if (empresasEncontradas.isEmpty()) {
					// No existe esa empresa, limitamos los results a 0 y no se realiza la busqueda.
					criteria.setMaxResults(0);
				}else {
					//Buscamos el id de la empresa con ese nombre y lo usamos de restriction
					Long idEmpresa = empresasEncontradas.get(0).getId();
					criteria.add(Restrictions.eq("empresa", idEmpresa));
				}
			} 
		}
		if (cuenta.getPeriodo() != null) {
			criteria.add(Restrictions.eq("periodo", cuenta.getPeriodo()));
		}
		if (cuenta.getTipo() != null) {
			criteria.add(Restrictions.eq("tipo", cuenta.getTipo()));
		}
		if (cuenta.getValor() != null) {
			criteria.add(Restrictions.eq("valor", cuenta.getValor()));
		}
	}

	public void limpiarRepositorio() {
		this.getAll().forEach(this::delete);
	}
}
