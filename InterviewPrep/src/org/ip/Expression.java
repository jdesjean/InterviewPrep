package org.ip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
				x += multiply();
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
	
	public interface Evaluator {
		public List<String> evaluate(String integers, int target);
	}
	
	public static class IterativeEvaluator implements Evaluator {
		
		private static String join(String integers, String operators) {
			char[] buffer = new char[integers.length()*2-1];
			int length = 0;
			for (int i = 0; i < integers.length(); i++) {
				buffer[length++] = integers.charAt(i);
				if (i >= operators.length() || operators.charAt(i) == '.') continue;
				buffer[length++] = operators.charAt(i);
			}
			return String.copyValueOf(buffer, 0, length);
		}
		@Override
		public List<String> evaluate(String integers, int target) {
			int digits = integers.length()-1;
			return IntStream
			.rangeClosed(0, (int)Math.pow(3, digits)-1)
			.mapToObj(i -> NumberUtils.convertToBase(i, 3, digits))
			.map(base3 -> base3.stream().map(i -> i == 0 ? "." : i == 1 ? "+" : "*").collect(Collectors.joining("")))
			.map(operators -> join(integers,operators))
			.filter(expression -> (new StateMachine(expression)).evaluate() == target)
			.collect(Collectors.toList());
		}
		
	}
	
	public static class RecursiveEvaluator implements Evaluator {
		private static List<String> generate(String integers, int integersIndex, char[] expression, int expressionIndex,
				List<String> expressions, int target) {
			if (integersIndex == integers.length()) {
				String exp = String.copyValueOf(expression, 0, expressionIndex);
				if (new StateMachine(exp).evaluate() != target) return expressions; 
				expressions.add(exp);
				return expressions;
			}
			expression[expressionIndex] = integers.charAt(integersIndex);
			generate(integers, integersIndex + 1, expression, expressionIndex + 1, expressions, target);
			if (expressionIndex == 0 || expression[expressionIndex - 1] == '+' || expression[expressionIndex - 1] == '*')
				return expressions;
			expression[expressionIndex] = '+';
			generate(integers, integersIndex, expression, expressionIndex + 1, expressions, target);
			expression[expressionIndex] = '*';
			generate(integers, integersIndex, expression, expressionIndex + 1, expressions, target);
			return expressions;
		}
		
		@Override
		public List<String> evaluate(String integers, int target) {
			return generate(integers, 0, new char[integers.length() * 2 - 1], 0, new ArrayList<String>(), target);
		}
		
	}
	
	public static List<String> solve(Evaluator evaluator, String integers, int target) {
		return evaluator.evaluate(integers,target);
	}
	
	public static void main(String[] input) {
		System.out.println(solve(new IterativeEvaluator(), "222", 6));
		System.out.println(solve(new RecursiveEvaluator(), "222", 6));
	}
}
