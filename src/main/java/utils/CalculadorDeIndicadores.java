package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ExceptionsPackage.CuentaNotFoundException;
import ExceptionsPackage.EmpresaNotFoundException;
import ExceptionsPackage.IndicadorNotFoundException;
import model.Cuenta;
import model.Empresa;
import model.Indicador;
import model.IndicadorCalculado;
import model.componentes.Componente;
import model.componentes.Constante;
import model.componentes.CuentaComponente;
import model.componentes.Expresion;
import model.componentes.OperacionBinaria;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import model.repositories.RepositorioIndicadores;
import model.repositories.RepositorioIndicadoresCalculados;

public class CalculadorDeIndicadores {

	private static CalculadorDeIndicadores instance;

	public static synchronized CalculadorDeIndicadores getInstance() {
		if (instance == null)
			instance = new CalculadorDeIndicadores();
		return instance;
	}

	public String obtenerCuentasSeparadasPorComa(Indicador indicador) {
		String cuentas = "";

		String formula = indicador.getFormula();
		int contadorLetras = 0;
		for (int i = 0; i < formula.length(); i++) {

			if (Character.isLetter(formula.charAt(i))) {
				contadorLetras += 1;
				while (((i + contadorLetras + 1) <= formula.length())
						&& Character.isLetter(formula.charAt(i + contadorLetras))) {
					contadorLetras += 1;
				}

				String nombreCuenta = formula.substring(i, i + contadorLetras);

				if (!isCuenta(nombreCuenta)) {
					throw new CuentaNotFoundException("Cuenta no encontrada: " + nombreCuenta);
				} else {
					if (cuentas.isEmpty()) {
						cuentas += nombreCuenta;
					} else {
						cuentas += ", " + nombreCuenta;
					}
				}

				i = i + contadorLetras;
				// Todo procesado, se reinicia el contador y se settea el i en
				// el valor deseado
				contadorLetras = 0;
			}
		}

		return cuentas;
	}

	public boolean isCuenta(String posibleCuenta) {
		return RepositorioCuentas.getInstance().getTiposDeCuenta().contains(posibleCuenta);
	}

	private void preparar(Componente componente, Empresa empresa, String periodo) {
		if (componente instanceof Constante) {
			return;
		} else if (componente instanceof CuentaComponente) {
			BigDecimal valor = this.calcularCuenta(((CuentaComponente) componente).getTipoDeCuenta(), periodo, empresa);
			((CuentaComponente) componente).setValor(valor);
		} else if (componente instanceof OperacionBinaria) {
			this.preparar(((OperacionBinaria) componente).getComponente1(), empresa, periodo);
			this.preparar(((OperacionBinaria) componente).getComponente2(), empresa, periodo);
		} else
			throw new IndicadorNotFoundException("Error en la preparacion del calculo");
	}

	public BigDecimal calcularIndicador(Indicador indicador, Empresa empresa, String periodo) {
		String formula = indicador.getFormula();
		Expresion expresion = ModeladorDeExpresiones.getInstance().modelarFormula(formula);
		this.preparar(expresion.getFormula(), empresa, periodo);
		return expresion.evaluar();
	}

	public List<IndicadorCalculado> calcularIndicadores(Empresa empresa, String periodo) {
		List<Indicador> _indicadores = new ArrayList<Indicador>();
		_indicadores.addAll(RepositorioIndicadores.getInstance().getAll());
		List<IndicadorCalculado> indicadoresCalculados = new ArrayList<IndicadorCalculado>();
		for (int i = 0; i < _indicadores.size(); i++) {
			indicadoresCalculados.add(new IndicadorCalculado(_indicadores.get(i), empresa, periodo));
		}
		return indicadoresCalculados;
	}

	public BigDecimal calcularCuenta(String tipoDeCuenta, String periodo, Empresa empresa) {
		List<Empresa> empresasEncontradas = RepositorioEmpresas.getInstance().searchByExample(empresa);
		if (empresasEncontradas.size() != 0) {
			empresa = empresasEncontradas.get(0);
		} else {
			throw new EmpresaNotFoundException("Empresa no encontrada: " + empresa);
		}
		List<Cuenta> cuentas = RepositorioCuentas.getInstance().filtrarCuentas(tipoDeCuenta, empresa, periodo, null);
		if (cuentas.isEmpty()) {
			throw new CuentaNotFoundException("Cuenta no encontrada: Tipo - " + tipoDeCuenta + ", Empresa - " + empresa
					+ ", Periodo - " + periodo + ".");
		}
		return cuentas.get(0).getValor();
	}

	public void recalcularIndicadoresParaCuentas(List<Cuenta> cuentas) {
		cuentas.forEach(cuenta -> {
			List<IndicadorCalculado> indicadoresAModificar = RepositorioIndicadoresCalculados.getInstance()
					.getAllForCuenta(cuenta);

			indicadoresAModificar.forEach(indicadorCalculado -> {
				IndicadorCalculado indicadorRecalculado = new IndicadorCalculado((Indicador) indicadorCalculado,
						indicadorCalculado.getEmpresa(), indicadorCalculado.getPeriodo());
				RepositorioIndicadoresCalculados.getInstance().delete(indicadorCalculado);
				RepositorioIndicadoresCalculados.getInstance().add(indicadorRecalculado);
			});
		});

	}

	public void calcularNuevoIndicadorAgregado(Indicador indicadorAgregado) {
		List<Empresa> empresasParaCalcular = RepositorioEmpresas.getInstance().getAll();
		List<IndicadorCalculado> indicadoresAAgregar = new ArrayList<>();

		for (Empresa empresa : empresasParaCalcular) {
			List<String> periodosParaCalcular = RepositorioCuentas.getInstance()
					.getPeriodosParaEmpresa(empresa.getNombre());
			for (String periodo : periodosParaCalcular) {
				indicadoresAAgregar.add(new IndicadorCalculado(indicadorAgregado, empresa, periodo));
			}
		}

		indicadoresAAgregar.forEach(indicadorCalculado -> RepositorioIndicadoresCalculados.getInstance().add(indicadorCalculado));
	}

}
