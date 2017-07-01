package tp1;

import org.junit.Test;

import parser.AnalizadorSintactico;

public class AnalizadorSintacticoTest {

	AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico();

	String formulaCorrectaNumeros = "1+2";
	String formulaCorrectaLetras = "cuenta*otra";
	String formulaCorrectaMixta = "(cuenta-2)*otra+4";
	String formulaConError = "cuenta)+?";

	String mensajeSatisfactorio = "Expresión correcta";
	String mensajeErroneo = "Error en la expresión";

	@Test
	public void chequeoFormulaNumeros() {
		try {
			analizadorSintactico.chequearIndicador(formulaCorrectaNumeros);
		} catch (Exception e) {
			e.equals(mensajeSatisfactorio);
		}
	}

	@Test
	public void chequeoFormulaLetras() {
		try {
			analizadorSintactico.chequearIndicador(formulaCorrectaNumeros);
		} catch (Exception e) {
			e.equals(mensajeSatisfactorio);
		}
	}

	@Test
	public void chequeoFormulaMixta() {
		try {
			analizadorSintactico.chequearIndicador(formulaCorrectaMixta);
		} catch (Exception e) {
			e.equals(mensajeSatisfactorio);
		}
	}

	@Test
	public void chequeoFormulaErroneo() {
		try {
			analizadorSintactico.chequearIndicador(formulaConError);
		} catch (Exception e) {
			e.equals(mensajeErroneo);
		}
	}

}
