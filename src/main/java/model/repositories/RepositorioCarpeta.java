package model.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ExceptionsPackage.IndicadorNotFoundException;
import dtos.CargaDeCuentasDTO;
import dtos.IndicadoresDTO;
import model.Cuenta;
import model.Indicador;
import utils.AppData;

public class RepositorioCarpeta {

	private static final AppData appData = AppData.getInstance();
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	private List<Indicador> indicadores = new ArrayList<Indicador>();

	private static CargaDeCuentasDTO dtoCuentas = new CargaDeCuentasDTO();
	private static IndicadoresDTO dtoIndicadores = new IndicadoresDTO();

	private Boolean numeracionBase0 = true;

	// Singleton
	private static RepositorioCarpeta instance;

	public RepositorioCarpeta() {
		super();
	}

	public static synchronized RepositorioCarpeta getInstance() {
		if (instance == null)
			instance = new RepositorioCarpeta();
		return instance;
	}

	public void limpiarRepositorio() {
		cuentas = new ArrayList<Cuenta>();
	}

	// Aniadir cuentas a la lista forzosamente es public por los test

	public void agregarCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}

	public void agregarIndicador(Indicador indicador) {
		indicadores.add(indicador);
	}

	public void addCuentas(List<Cuenta> _cuentas) {
		for (Cuenta cuenta : _cuentas)
			agregarCuenta(cuenta);
	}

	public void addIndicadores(List<Indicador> _indicadores) {
		for (Indicador indicador : _indicadores)
			agregarIndicador(indicador);
	}

	public void archivarRepositorio() {
		appData.guardar(cuentas, dtoCuentas);
		appData.guardar(indicadores, dtoIndicadores);
	}

	// Metodos para agregar cuentas que respetan un orden logico en los ID

	public void agregarCuentaConId(Cuenta cuenta) {
		// Basicamente ignorar el ID que viene de la cuenta y meterle el nuestro
		// Crea una copia de esa cuenta para no cambiarle el id a la original
		Cuenta _cuenta = cuenta;

		_cuenta.setId(getIdForNextCuenta());

		cuentas.add(_cuenta);
	}

	public void agregarCuentas(List<Cuenta> _cuentas) {
		for (Cuenta cuenta : _cuentas)
			agregarCuentaConId(cuenta);

		archivarRepositorio();
	}

	public void agregarIndicadores(List<Indicador> _indicadores) {
		for (Indicador indicador : _indicadores)
			agregarIndicador(indicador);

		archivarRepositorio();
	}

	// Metodos para remover cuentas del repositorio

	public void removerCuenta(Cuenta cuenta) {
		if (cuentas.contains(cuenta))
			cuentas.remove(cuenta);
		else
			throw new Error("La cuenta no existe");
	}

	public void removerCuentaPorId(int id) {
		removerCuenta(getCuentaPorId(id));
	}

	public void removerCuentas(List<Cuenta> cuentasABorrar) {
		for (Cuenta cuenta : cuentasABorrar)
			removerCuenta(cuenta);
	}

	public void removerCuentasPorId(List<Integer> ids) {
		for (Integer id : ids)
			removerCuentaPorId(id);
	}

	// Utilidades

	private int getIdForNextCuenta() {
		// Solo funciona si las cuentas estan ordenadas dentro de la lista
		if (size() != 0) {
			Cuenta ultimaCuenta = cuentas.get(size() - 1);
			return ultimaCuenta.getId() + 1;
		} else
			return numeracionBase0 ? 0 : 1;
	}

	public void regenerarLosId() {
		// Regenera los ID de las cuentas segun su posici�n en la lista
		if (size() == 0)
			throw new Error("Repositorio vacio");

		int i = (numeracionBase0) ? 0 : 1;

		for (Cuenta cuenta : cuentas)
			cuenta.setId(i++);
	}

	public void reordenarCuentasPorId() {
		// Reordena las cuentas segun sus id, no cambia nada en las cuentas
		if (size() == 0)
			throw new Error("Repositorio vacio");
		cuentas = cuentas.stream().sorted(Comparator.comparingInt(Cuenta::getId)).collect(Collectors.toList());
	}

	public int size() {
		return cuentas.size();
	}

	public void setNumeracionBase0() {
		numeracionBase0 = true;
	}

	public void setNumeracionBase1() {
		numeracionBase0 = false;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public Cuenta getCuentaPorId(int id) {
		for (Cuenta cuenta : cuentas)
			if (cuenta.getId() == id)
				return cuenta;

		throw new Error("No se encuentra una cuenta con ID: " + id);
	}

	// Filtrar cuentas del repositorio

	public List<Cuenta> filtrarCuentas(String tipo, String empresa, String periodo, String valor) {
		List<Cuenta> _cuentas = cuentas;
		if (!periodo.isEmpty())
			_cuentas = filtrarCuentasPorPeriodo(periodo, _cuentas);
		if (!empresa.isEmpty())
			_cuentas = filtrarCuentasPorEmpresa(empresa, _cuentas);
		if (!tipo.isEmpty())
			_cuentas = filtarCuentasPorTipo(tipo, _cuentas);
		if (!valor.isEmpty())
			_cuentas = filtrarCuentasPorValor(valor, _cuentas);

		return _cuentas;
	}

	public List<Indicador> filtrarIndicadores(String empresa, String nombre, String periodo, String valor) {
		List<Indicador> _indicadores = indicadores;
		if (!empresa.isEmpty())
			_indicadores = filtrarIndicadoresPorEmpresa(empresa, _indicadores);
		if (!nombre.isEmpty())
			_indicadores = filtrarIndicadoresPorNombre(nombre, _indicadores);
		if (!periodo.isEmpty())
			_indicadores = filtrarIndicadoresPorPeriodo(periodo, _indicadores);
		if (!valor.isEmpty())
			_indicadores = filtrarIndicadoresPorValor(valor, _indicadores);
		return _indicadores;
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

	private List<Cuenta> filtrarCuentasPorValor(String valor, List<Cuenta> _cuentas) {
		_cuentas = _cuentas.stream().filter(cuenta -> cuenta.getValor() == Integer.parseInt(valor))
				.collect(Collectors.toList());
		return _cuentas;
	}

	// Devuelven una lista ordenada de determinada manera, sin alterar las
	// propias del repositorio

	public List<Cuenta> getCuentasPorTipo() {
		List<Cuenta> _cuentas = cuentas.stream().sorted(Comparator.comparing(Cuenta::getTipo))
				.collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorEmpresa() {
		List<Cuenta> _cuentas = cuentas.stream().sorted(Comparator.comparing(Cuenta::getEmpresa))
				.collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorPeriodo() {
		List<Cuenta> _cuentas = cuentas.stream().sorted(Comparator.comparing(Cuenta::getPeriodo))
				.collect(Collectors.toList());
		return _cuentas;
	}

	public List<Cuenta> getCuentasPorValor() {
		List<Cuenta> _cuentas = cuentas.stream().sorted(Comparator.comparingLong(Cuenta::getValor))
				.collect(Collectors.toList());
		return _cuentas;
	}

	public Collection<String> getTiposDeCuenta() {
		List<Cuenta> _cuentas = cuentas;
		Collection<String> tipos;
		tipos = _cuentas.stream().map(cuenta -> cuenta.getTipo()).sorted().collect(Collectors.toSet());
		return tipos;
	}

	public String getFormulaDeIndicador(String nombreIndicador) throws IndicadorNotFoundException {
		Optional<String> formulaIndicador = indicadores.stream()
				.filter(_indicador -> _indicador.getNombre().equals(nombreIndicador)).map(_indic -> _indic.getFormula())
				.findFirst();
		if (formulaIndicador.isPresent()) {
			throw new IndicadorNotFoundException("Indicador no encontrado: " + nombreIndicador);
		} else {
			return formulaIndicador.get();
		}
	}

	public List<String> getNombresDeIndicadores() {
		List<String> nombres = indicadores.stream().map(indicador -> indicador.getNombre())
				.collect(Collectors.toList());
		return nombres;
	}

	public List<Indicador> filtrarIndicadoresPorPeriodo(String periodo, List<Indicador> indicadores2) {
		indicadores2 = indicadores2.stream().filter(indicador -> periodo.equals(indicador.getPeriodo())).collect(Collectors.toList());
		return indicadores2;
	}

	public List<Indicador> filtrarIndicadoresPorEmpresa(String empresa, List<Indicador> indicadores2) {
		indicadores2 = indicadores2.stream().filter(indicador -> empresa.equals(indicador.getEmpresa())).collect(Collectors.toList());
		return indicadores2;
	}
	
	private List<Indicador> filtrarIndicadoresPorValor(Double valor, List<Indicador> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> indicador.getValor() == Integer.parseInt(valor))
				.collect(Collectors.toList());
		return _indicadores;
	}

	private List<Indicador> filtrarIndicadoresPorNombre(String nombre, List<Indicador> _indicadores) {
		_indicadores = _indicadores.stream().filter(indicador -> nombre.equals(indicador.getNombre())).collect(Collectors.toList());
		return _indicadores;
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public static void setDtoCuentas(CargaDeCuentasDTO _dtoCuentas) {
		dtoCuentas = _dtoCuentas;
	}

	public static void setDtoIndicadores(IndicadoresDTO _dtoIndicadores) {
		dtoIndicadores = _dtoIndicadores;
	}
}
