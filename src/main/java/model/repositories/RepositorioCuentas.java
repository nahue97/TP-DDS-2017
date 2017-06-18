package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ExceptionsPackage.CuentaNotFoundException;
import dtos.DTO;
import model.Cuenta;
import utils.AppData;

public class RepositorioCuentas {

	// Singleton
	private static RepositorioCuentas instance;

	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	private DTO dtoCuentas;

	public int size() {
		return cuentas.size();
	}

	public void setDtoCuentas(DTO _dtoCuentas) {
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
		cuentas = new ArrayList<Cuenta>();
	}

	public void archivarRepositorio() {
		AppData.getInstance().guardar(cuentas, dtoCuentas);
	}

	public void agregarCuentas(List<Cuenta> _cuentas) {
		for (Cuenta cuenta : _cuentas) {
			agregarCuenta(cuenta);
		}
	}

	public void agregarCuenta(Cuenta cuenta) {
		Cuenta _cuenta = cuenta;

		_cuenta.setId(getIdForNextCuenta());

		cuentas.add(_cuenta);
	}

	public void removerCuenta(Cuenta cuenta) {
		if (cuentas.contains(cuenta)) {
			cuentas.remove(cuenta);
			archivarRepositorio();
		} else {
			throw new CuentaNotFoundException("La cuenta no existe");
		}
	}

	public void removerCuentaPorId(int id) {
		removerCuenta(getCuentaPorId(id));
		archivarRepositorio();
	}

	private int getIdForNextCuenta() {
		if (size() != 0) {
			Cuenta ultimaCuenta = cuentas.get(size() - 1);
			return ultimaCuenta.getId() + 1;
		} else
			return 0;
	}

	public Cuenta getCuentaPorId(int id) {
		for (Cuenta cuenta : cuentas)
			if (cuenta.getId() == id)
				return cuenta;

		throw new CuentaNotFoundException("No se encuentra una cuenta con ID: " + id);
	}
	
	public BigDecimal getValorDeCuentaPorTipoEmpresaYPeriodo(String tipoCuenta, String empresa, String periodo){
		List<Cuenta> cuentas = filtrarCuentas(tipoCuenta, empresa, periodo, null);
		if (cuentas.size() == 0){
			throw new CuentaNotFoundException("Cuenta no encontrada: Tipo - " + tipoCuenta + ", Empresa - " + empresa + ", Período - " + periodo + ".");
		}
		return cuentas.get(0).getValor();
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
			_cuentas = filtarCuentasPorTipo(tipo, _cuentas);
		if (valor != null)
			_cuentas = filtrarCuentasPorValor(valor, _cuentas);

		return _cuentas;
	}

	private List<Cuenta> filtarCuentasPorTipo(String tipo, List<Cuenta> _cuentas) {
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
		return periodos;
	}

	public List<String> getEmpresasDeCuentas() {
		List<Cuenta> _cuentas = new ArrayList<>();
		_cuentas.addAll(cuentas);
		List<String> empresas = new ArrayList<String>();
		empresas.addAll(_cuentas.stream().map(cuenta -> cuenta.getEmpresa()).sorted().collect(Collectors.toSet()));
		return empresas;
	}
}
