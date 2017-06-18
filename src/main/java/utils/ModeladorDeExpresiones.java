package utils;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.math.NumberUtils;

import model.Componente;
import model.Constante;
import model.Cuenta;
import model.CuentaComponente;
import model.Division;
import model.Expresion;
import model.Multiplicacion;
import model.Resta;
import model.Suma;

public class ModeladorDeExpresiones {
	
private static ModeladorDeExpresiones instance;
	
	

	public static synchronized ModeladorDeExpresiones getInstance() {
		if (instance == null)
			instance = new ModeladorDeExpresiones();
		return instance;
	}

	private enum Operator {
		ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4);
		final int precedence;

		Operator(int p) {
			precedence = p;
		}
	}

	/*public static void main(String[] args) {
		String postfijo = postfix("( 1 + EBITDA ) / 2 * 3 + 5 - ( FDS / 4 )");
		System.out.println(postfijo);
		System.out.println(convert(postfijo));
	}*/

	private static Map<String, Operator> ops = new HashMap<String, Operator>() {
		{
			put("+", Operator.ADD);
			put("-", Operator.SUBTRACT);
			put("*", Operator.MULTIPLY);
			put("/", Operator.DIVIDE);
		}
	};

	private static boolean isHigerPrec(String op, String sub) {
		return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
	}

	public static String postfix(String infix) {
		StringBuilder output = new StringBuilder();
		Deque<String> stack = new LinkedList<>();

		for (String token : infix.split("\\s")) {
			// operator
			if (ops.containsKey(token)) {
				while (!stack.isEmpty() && isHigerPrec(token, stack.peek()))
					output.append(stack.pop()).append(' ');
				stack.push(token);

				// left parenthesis
			} else if (token.equals("(")) {
				stack.push(token);

				// right parenthesis
			} else if (token.equals(")")) {
				while (!stack.peek().equals("("))
					output.append(stack.pop()).append(' ');
				stack.pop();

				// digit
			} else {
				output.append(token).append(' ');
			}
		}

		while (!stack.isEmpty())
			output.append(stack.pop()).append(' ');

		return output.toString();
	}

	private static boolean isOperator(String c) {
		if (ops.containsKey(c))
			return true;
		return false;
	}

	/**
	 * Converts any postfix to infix
	 * 
	 * @param postfix
	 *            String expression to be converted
	 * @return String infix expression produced
	 */
	public static Expresion convert(String postfix) {
		Stack<String> s = new Stack<>();
		Stack<Componente> c = new Stack<>();

		for (String token : postfix.split("\\s")) {
			if (isOperator(token)) {
				Componente b = c.pop();
				Componente a = c.pop();
				c.push(crearOperacionBinaria(token, a, b));
			} else {
				if (NumberUtils.isNumber(token)) {
					c.push(crearConstante(token));
				} else {
					c.push(crearCuenta(token));
				}
			}

		}
		return new Expresion(c.pop());
	}

	private static Componente crearOperacionBinaria(String token, Componente a, Componente b) {
		switch (token) {
		case ("+"):
			return new Suma(a, b);
		case ("-"):
			return new Resta(a, b);
		case ("*"):
			return new Multiplicacion(a, b);
		case ("/"):
			return new Division(a, b);
		default:
			return null;
		}
	}

	private static Componente crearCuenta(String token) {
		return new CuentaComponente(token);
	}

	private static Componente crearConstante(String token) {
		return new Constante(new BigDecimal(token));
	}

	public Expresion modelarFormula(String formula) {
		return convert(postfix(formula));
	}

}