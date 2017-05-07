package org.ip;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.ip.primitives.Number;
import org.ip.string.Visitors.StringVisitor;

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
		public void evaluate(StringVisitor visitor, String integers, int target);
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
		public void evaluate(StringVisitor visitor, String integers, int target) {
			int digits = integers.length()-1;
			IntStream
			.rangeClosed(0, (int)Math.pow(3, digits)-1)
			.mapToObj(i -> Number.convertToBase(i, 3, digits))
			.map(base3 -> base3.stream().map(i -> i == 0 ? "." : i == 1 ? "+" : "*").collect(Collectors.joining("")))
			.map(operators -> join(integers,operators))
			.filter(expression -> (new StateMachine(expression)).evaluate() == target)
			.forEach(string -> visitor.visit(string));
		}
		
	}
	
	public static class RecursiveEvaluator implements Evaluator {
		private static void generate(String integers, int integersIndex, char[] expression, int expressionIndex,
				StringVisitor visitor, int target) {
			if (integersIndex == integers.length()) {
				String exp = String.copyValueOf(expression, 0, expressionIndex);
				if (new StateMachine(exp).evaluate() != target) return; 
				visitor.visit(exp);
				return;
			}
			expression[expressionIndex] = integers.charAt(integersIndex);
			generate(integers, integersIndex + 1, expression, expressionIndex + 1, visitor, target);
			if (expressionIndex == 0 || expression[expressionIndex - 1] == '+' || expression[expressionIndex - 1] == '*')
				return;
			expression[expressionIndex] = '+';
			generate(integers, integersIndex, expression, expressionIndex + 1, visitor, target);
			expression[expressionIndex] = '*';
			generate(integers, integersIndex, expression, expressionIndex + 1, visitor, target);
			return ;
		}
		
		@Override
		public void evaluate(StringVisitor visitor, String integers, int target) {
			generate(integers, 0, new char[integers.length() * 2 - 1], 0, visitor, target);
		}
		
	}
	
	public static void main(String[] input) {
		StringVisitor visitor = new StringVisitor(){

			@Override
			public void visit(String string) {
				System.out.println(string);
			}
			
		};
		new IterativeEvaluator().evaluate(visitor, "222", 6);
		System.out.println("");
		new RecursiveEvaluator().evaluate(visitor, "222", 6);
	}
}
