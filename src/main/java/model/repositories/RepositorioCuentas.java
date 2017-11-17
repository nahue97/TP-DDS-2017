package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import model.Cuenta;
import model.Empresa;

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
		_cuentas.forEach(this::addOrUpdate);
	}
	
	public void addOrUpdate(Cuenta cuenta){
		List<Cuenta> cuentasPreexistentes = searchByExample(cuenta);
		if (!cuentasPreexistentes.isEmpty()){			
			Cuenta cuentaPreexistente = cuentasPreexistentes.get(0);
			cuentaPreexistente.setValor(cuenta.getValor());
			add(cuentaPreexistente);
		} else {
			add(cuenta);
		}
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
				criteria.add(Restrictions.eq("empresa", cuenta.getEmpresa()));
			} else if (cuenta.getEmpresa().getNombre() != null) {
				List<Empresa> empresasEncontradas = RepositorioEmpresas.getInstance()
						.searchByExample(new Empresa(null, cuenta.getEmpresa().getNombre()));
				if (empresasEncontradas.isEmpty()) {
					// No existe esa empresa, limitamos los results a 0 y no se realiza la busqueda.
					criteria.setMaxResults(0);
				}else {
					//Buscamos el id de la empresa con ese nombre y lo usamos de restriction
					Empresa empresaEncontrada = empresasEncontradas.get(0);
					criteria.add(Restrictions.eq("empresa", empresaEncontrada));
				}
			} 
		}
		if (cuenta.getPeriodo() != null) {
			if (!cuenta.getPeriodo().isEmpty()){				
				criteria.add(Restrictions.eq("periodo", cuenta.getPeriodo()));
			}
		}
		if (cuenta.getTipo() != null) {
			if (!cuenta.getTipo().isEmpty()){
				criteria.add(Restrictions.eq("tipo", cuenta.getTipo()));				
			}
		}
		if (cuenta.getValor() != null) {
			criteria.add(Restrictions.eq("valor", cuenta.getValor()));
		}
	}

	public List<Empresa> getEmpresasConCuenta() {
		Set<Empresa> empresasConCuenta = new HashSet<Empresa>();
			this.getAll().forEach(cuenta -> empresasConCuenta.add(cuenta.getEmpresa()));
		return new ArrayList<Empresa>(empresasConCuenta);
	}

	@Override
	protected List<Cuenta> getAllFromUserId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
