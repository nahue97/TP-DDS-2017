package utils;

import java.util.ArrayList;
import java.util.List;

import ExceptionsPackage.CuentaNotFoundException;
import ExceptionsPackage.FormulaException;
import ExceptionsPackage.IndicadorNotFoundException;
import model.repositories.RepositorioCarpeta;
import parser.AnalizadorSintactico;

public class AnalizadorDeFormulas {

	public String analizarYSimplificarFormula(String formula) throws FormulaException, CuentaNotFoundException {
		String formulaNueva = simplificarFormulaDeIndicador(formula);
		cuentasCorrectasEnFormula(formulaNueva);
		checkearParentesis(formulaNueva);
		checkearComas(formulaNueva);
		checkearCaracteresInvalidos(formulaNueva);
		AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico();
		analizadorSintactico.chequearIndicador(formulaNueva);

		return formulaNueva;
	}

	public String simplificarFormulaDeIndicador(String formula) throws CuentaNotFoundException, FormulaException {
		String formulaSimplificada = "";
		boolean todoOk = false;

		formulaSimplificada = reemplazarIndicadoresPorFormulas(formula);

		cuentasCorrectasEnFormula(formulaSimplificada);

		return formulaSimplificada;
	}

	public String reemplazarIndicadoresPorFormulas(String formula) throws FormulaException {
		String formulaConIndicadoresReemplazados = "";
		int contadorLetras = 0;
		for (int i = 0; i < formula.length(); i++) {

			if (Character.isLetter(formula.charAt(i))) {
				contadorLetras += 1;
				while (!((i + contadorLetras + 1) <= formula.length())
						&& Character.isLetter(formula.charAt(i + contadorLetras))) {
					contadorLetras += 1;
				}

				String nombreIndicadorOFormula = formula.substring(i, i + contadorLetras);

				if (isIndicador(nombreIndicadorOFormula)) {
					formulaConIndicadoresReemplazados += convertirIndicadorAFormula(nombreIndicadorOFormula);
				} else {
					formulaConIndicadoresReemplazados += nombreIndicadorOFormula;
				}

				i = i + contadorLetras;

				// Todo procesado, se reinicia el contador y se settea el i en
				// el valor deseado
				contadorLetras = 0;
			} else {
				formulaConIndicadoresReemplazados += formula.charAt(i);
			}
		}

		return formulaConIndicadoresReemplazados;
	}

	public String convertirIndicadorAFormula(String nombreIndicador) throws FormulaException {
		try {
			return RepositorioCarpeta.getInstance().getFormulaDeIndicador(nombreIndicador);
		} catch (IndicadorNotFoundException e) {
			throw new FormulaException(e.getMessage());
		}
	}

	public void cuentasCorrectasEnFormula(String formula) throws CuentaNotFoundException {
		int contadorLetras = 0;
		for (int i = 0; i < formula.length(); i++) {

			if (Character.isLetter(formula.charAt(i))) {
				contadorLetras += 1;
				while (!((i + contadorLetras + 1) <= formula.length())
						&& Character.isLetter(formula.charAt(i + contadorLetras))) {
					contadorLetras += 1;
				}

				String nombreCuenta = formula.substring(i, i + contadorLetras);

				if (!isCuenta(nombreCuenta)) {
					throw new CuentaNotFoundException("Cuenta no encontrada: " + nombreCuenta);
				}

				i = i + contadorLetras;

				// Todo procesado, se reinicia el contador y se settea el i en
				// el valor deseado
				contadorLetras = 0;
			}
		}
	}

	public boolean isIndicador(String posibleIndicador) {
		return RepositorioCarpeta.getInstance().getNombresDeIndicadores().contains(posibleIndicador);
	}

	public boolean isCuenta(String posibleCuenta) {
		return RepositorioCarpeta.getInstance().getTiposDeCuenta().contains(posibleCuenta);
	}

	public void checkearParentesis(String formula) throws FormulaException {
		int contadorParentesisAbiertos = 0;

		for (int i = 0; i < formula.length(); i++) {
			if (formula.charAt(i) == ')' && contadorParentesisAbiertos == 0) {
				throw new FormulaException(
						"Héctor, fijate que pusiste un paréntesis derecho sin poner uno izquierdo, media pila loco.");
			}

			if (formula.charAt(i) == '(') {
				contadorParentesisAbiertos += 1;

			} else if (formula.charAt(i) == ')') {
				contadorParentesisAbiertos -= 1;
			}

			if (formula.charAt(i) == ')' && (formula.charAt(i - 1) == '(')) {
				throw new FormulaException("Paréntesis vacío en la posición " + Integer.toString(i));
			}
		}

		if (contadorParentesisAbiertos > 0) {
			throw new FormulaException("Faltaron cerrar algunos paréntesis, específicamente "
					+ Integer.toString(contadorParentesisAbiertos));
		}
	}

	private void checkearCaracteresInvalidos(String formula) throws FormulaException {
		char caracter;
		for (int i = 0; i < formula.length(); i++) {
			caracter = formula.charAt(i);
			if (!Character.isLetter(caracter) && !Character.isDigit(caracter) && caracter != ',' && caracter != '*'
					&& caracter != '-' && caracter != '+' && caracter != '/' && caracter != '(' && caracter != ')') {
				throw new FormulaException("Caracter inválido en la posición " + Integer.toString(i)
						+ ". Recordá que los espacios no están permitidos.");
			}
		}

	}

	private void checkearComas(String formula) throws FormulaException {
		for (int i = 0; i < formula.length(); i++) {
			if (formula.charAt(i) == ',' && i != 0 && i != (formula.length() - 1)) {
				if (!(Character.isDigit(formula.charAt(i - 1)) && Character.isDigit(formula.charAt(i + 1)))) {
					throw new FormulaException("Coma mal ubicada, caracter nro " + Integer.toString(i));
				}
			} else if ((formula.charAt(i) == ',') && (i == 0 || i == formula.length() - 1)) {
				throw new FormulaException("Coma mal ubicada, caracter nro " + Integer.toString(i));
			}
		}
	}

}
