package org.ip;

import java.util.ArrayList;
import java.util.List;

public class Expression {
	private static class StateMachine {
		private int currentChar = -1;
		private int currentIndex = -1;
		private String expression;

		public StateMachine(String integers) {
			this.expression = integers;
		}

		private int add() {
			int x = multiply();
			while (eat('+')) {
				x += join();
			}
			return x;
		}

		private boolean eat(int target) {
			if (currentChar != target)
				return false;
			nextChar();
			return true;
		}

		public int evaluate() {
			nextChar();
			return add();
		}

		private int join() {
			int x = 0;
			while (Character.isDigit(currentChar)) {
				x = x * 10 + Character.getNumericValue(currentChar);
				nextChar();
			}
			return x;
		}

		private int multiply() {
			int x = join();
			while (eat('*')) {
				x *= join();
			}
			return x;

		}

		private int nextChar() {
			return currentChar = ++currentIndex < expression.length() ? expression.charAt(currentIndex) : -1;
		}
	}

	public static List<String> evaluate(String integers, int target) {
		List<String> expressions = new ArrayList<String>();
		generate(integers, 0, new char[integers.length() * 2 - 1], 0, expressions);
		expressions.removeIf(expression -> (new StateMachine(expression)).evaluate() != target);
		return expressions;
	}

	public static void generate(String integers, int integersIndex, char[] expression, int expressionIndex,
			List<String> expressions) {
		if (integersIndex == integers.length()) {
			expressions.add(String.copyValueOf(expression, 0, expressionIndex));
			return;
		}
		expression[expressionIndex] = integers.charAt(integersIndex);
		generate(integers, integersIndex + 1, expression, expressionIndex + 1, expressions);
		if (expressionIndex == 0 || expression[expressionIndex - 1] == '+' || expression[expressionIndex - 1] == '*')
			return;
		expression[expressionIndex] = '+';
		generate(integers, integersIndex, expression, expressionIndex + 1, expressions);
		expression[expressionIndex] = '*';
		generate(integers, integersIndex, expression, expressionIndex + 1, expressions);
	}

	public static void main(String[] input) {
		System.out.println(evaluate("222", 24));
	}
}
