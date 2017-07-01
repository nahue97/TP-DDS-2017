package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ExceptionsPackage.CuentaNotFoundException;
import ExceptionsPackage.IndicadorNotFoundException;
import model.Cuenta;
import model.Indicador;
import model.IndicadorCalculado;
import model.componentes.Componente;
import model.componentes.Constante;
import model.componentes.CuentaComponente;
import model.componentes.Expresion;
import model.componentes.OperacionBinaria;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;

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

	private void preparar(Componente componente, String empresa, String periodo) {
		if (componente instanceof Constante){
				return;
		}
		else{
			if (componente instanceof CuentaComponente){
				BigDecimal valor = this.calcularCuenta(((CuentaComponente) componente).getTipoDeCuenta(), periodo, empresa);
				((CuentaComponente) componente).setValor(valor);
			}
			else{
				if (componente instanceof OperacionBinaria){
					this.preparar(((OperacionBinaria) componente).getComponente1(), empresa, periodo);
					this.preparar(((OperacionBinaria) componente).getComponente2(), empresa, periodo);
				}
				else
					throw new IndicadorNotFoundException("Error en la preparacion del calculo");
			}
		}
	}

	public BigDecimal calcularIndicador(Indicador indicador, String empresa, String periodo) {
		String formula = indicador.getFormula();
		Expresion expresion = ModeladorDeExpresiones.getInstance().modelarFormula(formula);
		this.preparar(expresion.getFormula(), empresa, periodo);
		return expresion.evaluar();
	}

	public List<IndicadorCalculado> calcularIndicadores(String empresa, String periodo) {
		List<Indicador> _indicadores = new ArrayList<Indicador>();
		_indicadores.addAll(RepositorioIndicadores.getInstance().getIndicadores());
		List<IndicadorCalculado> indicadoresCalculados = new ArrayList<IndicadorCalculado>();
		for (int i = 0; i < _indicadores.size(); i++) {
			indicadoresCalculados.add(new IndicadorCalculado(_indicadores.get(i), empresa, periodo, i));
		}
		return indicadoresCalculados;
	}

	public BigDecimal calcularCuenta(String tipoDeCuenta, String periodo, String empresa) {
		List<Cuenta> cuentas = RepositorioCuentas.getInstance().filtrarCuentas(tipoDeCuenta, empresa, periodo, null);
		if (cuentas.size() == 0) {
			throw new CuentaNotFoundException("Cuenta no encontrada: Tipo - " + tipoDeCuenta + ", Empresa - " + empresa
					+ ", Periodo - " + periodo + ".");
		}
		return cuentas.get(0).getValor();
	}
	
}
