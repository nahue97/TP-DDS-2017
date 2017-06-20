package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ExceptionsPackage.CuentaNotFoundException;
import model.Indicador;
import model.IndicadorCalculado;
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

	public BigDecimal calcularIndicador(Indicador indicador, String empresa, String periodo) {
		return indicador.getExpresion().evaluar(periodo, empresa);
	}

	public List<IndicadorCalculado> calcularIndicadores(String empresa, String periodo) {
		List<Indicador> _indicadores = new ArrayList<Indicador>();
		_indicadores.addAll(RepositorioIndicadores.getInstance().getIndicadores());
		List<IndicadorCalculado> indicadoresCalculados = new ArrayList<IndicadorCalculado>();
		for (int i = 0; i < _indicadores.size(); i++){
			indicadoresCalculados.add(new IndicadorCalculado(_indicadores.get(i), empresa, periodo, i));
		}
		return indicadoresCalculados;
	}
}
