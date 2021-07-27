package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/expression-add-operators/">LC: 282</a>
 */
public class ExpressionAddOperators {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList(new String[] { "1+2+3", "1*2*3" }), "123", 6 };
		Object[] tc2 = new Object[] { Arrays.asList(new String[] { "2*3+2", "2+3*2" }), "232", 8 };
		Object[] tc3 = new Object[] { Arrays.asList(new String[] { "1*0+5", "10-5" }), "105", 5 };
		Object[] tc4 = new Object[] { Arrays.asList(new String[] { "0+0", "0-0", "0*0" }), "00", 0 };
		Object[] tc5 = new Object[] { Arrays.asList(new String[] {}), "3456237490", 9191 };
		Object[] tc6 = new Object[] { Arrays.asList(new String[] { "1*2*3+4+5", "1+2+3+4+5", "1+23-4-5", "1-2*3+4*5" }),
				"12345", 15 };
		Object[] tc7 = new Object[] { Arrays.asList(new String[] { "1*2*3+4+5+6", "1+2*3+4*5-6", "1+2+3+4+5+6",
				"1+2-3*4+5*6", "1+23-4-5+6", "1-2*3+4*5+6", "1-2*3-4+5*6", "1-2-34+56", "12*3-4-5-6" }), "123456", 21 };
		Object[] tc8 = new Object[] { Arrays.asList(new String[] { "1*2*3*4*5-6-78+9", "1*2*3*4+5+6-7+8+9",
				"1*2*3+4+5+6+7+8+9", "1*2*3+4+5-6*7+8*9", "1*2*3+4-5*6+7*8+9", "1*2*3+4-5*6-7+8*9", "1*2*3-4*5+6*7+8+9",
				"1*2*3-4*5-6+7*8+9", "1*2*3-4*5-6-7+8*9", "1*2*3-45+67+8+9", "1*2*34+56-7-8*9", "1*2*34-5+6-7-8-9",
				"1*2+3*4-56+78+9", "1*2+3+4+5*6+7+8-9", "1*2+3+4-5+6*7+8-9", "1*2+3+4-5-6+7*8-9", "1*2+3+45+67-8*9",
				"1*2+3-45+6+7+8*9", "1*2+34+5-6-7+8+9", "1*2+34+56-7*8+9", "1*2+34-5+6+7-8+9", "1*2+34-56+7*8+9",
				"1*2+34-56-7+8*9", "1*2-3*4+5+67-8-9", "1*2-3+4-5-6*7+89", "1*2-3-4*5+67+8-9", "1*2-3-4+56-7-8+9",
				"1*2-34+5*6+7*8-9", "1*23+4*5-6+7-8+9", "1*23-4-56-7+89", "1+2*3*4*5+6+7-89", "1+2*3*4+5*6+7-8-9",
				"1+2*3*4-5+6*7-8-9", "1+2*3+4*5*6+7-89", "1+2*3+4*5-6+7+8+9", "1+2*3-4-5-6*7+89", "1+2*34-5*6+7+8-9",
				"1+2+3*4*5+6-7-8-9", "1+2+3*4+5+6*7-8-9", "1+2+3*45-6-78-9", "1+2+3+4+5+6+7+8+9", "1+2+3+4+5-6*7+8*9",
				"1+2+3+4-5*6+7*8+9", "1+2+3+4-5*6-7+8*9", "1+2+3-4*5+6*7+8+9", "1+2+3-4*5-6+7*8+9", "1+2+3-4*5-6-7+8*9",
				"1+2+3-45+67+8+9", "1+2-3*4*5+6+7+89", "1+2-3*4+5*6+7+8+9", "1+2-3*4-5+6*7+8+9", "1+2-3*4-5-6+7*8+9",
				"1+2-3*4-5-6-7+8*9", "1+2-3+4*5+6*7-8-9", "1+2-3+45+6-7-8+9", "1+2-3+45-6+7+8-9", "1+2-3-4-5*6+7+8*9",
				"1+2-3-45-6+7+89", "1+2-34+5+6+7*8+9", "1+2-34+5+6-7+8*9", "1+2-34-5-6+78+9", "1+23*4+5-6-7*8+9",
				"1+23*4-5-6*7+8-9", "1+23*4-56+7-8+9", "1+23+4+5+6+7+8-9", "1+23+4-5*6+7*8-9", "1+23+4-5-67+89",
				"1+23-4*5+6*7+8-9", "1+23-4*5-6+7*8-9", "1+23-4-5+6+7+8+9", "1+23-4-5-6*7+8*9", "1+23-45+67+8-9",
				"1-2*3*4+5-6+78-9", "1-2*3*4-5-6+7+8*9", "1-2*3+4*5+6+7+8+9", "1-2*3+4*5-6*7+8*9", "1-2*3+4+5+6*7+8-9",
				"1-2*3+4+5-6+7*8-9", "1-2*3+4+56+7-8-9", "1-2*3+45-67+8*9", "1-2*3-4+5*6+7+8+9", "1-2*3-4-5+6*7+8+9",
				"1-2*3-4-5-6+7*8+9", "1-2*3-4-5-6-7+8*9", "1-2*34+5*6-7+89", "1-2+3*4*5-6-7+8-9", "1-2+3+4-5*6+78-9",
				"1-2+3+45+6-7+8-9", "1-2+3-4*5-6+78-9", "1-2+3-45+6-7+89", "1-2-3*4+5+6+7*8-9", "1-2-3*4-5-6+78-9",
				"1-2-3+4-5+67-8-9", "1-2-3+45-6-7+8+9", "1-2-34+5+6+78-9", "1-2-34+56+7+8+9", "1-2-34-5+6+7+8*9",
				"1-23*4+5+6*7+89", "1-23+4*5-6*7+89", "1-23+4-5+67-8+9", "1-23+45-67+89", "1-23-4+5+67+8-9",
				"1-23-4-5-6-7+89", "12*3*4-5*6-78+9", "12*3+4+5+6-7-8+9", "12*3+4+5-6+7+8-9", "12*3-4-5-6+7+8+9",
				"12*3-4-56+78-9", "12+3*4+5+6-7+8+9", "12+3*45-6-7-89", "12+3+4-56-7+89", "12+3-4*5+67-8-9",
				"12+3-45+6+78-9", "12+34-5-6-7+8+9", "12-3*4*5+6+78+9", "12-3*4-5+67-8-9", "12-3+4*5+6-7+8+9",
				"12-3+4+56-7-8-9", "12-3-4+5*6-7+8+9", "12-3-4-56+7+89", "12-3-45-6+78+9" }), "123456789", 45 };
		Object[][] tcs = new Object[][] { tc3, tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8 };
		Problem[] solvers = new Problem[] { new Solver2() };
		Test.apply(solvers, tcs);
	}
	
	public static class Solver2 implements Problem {

		@Override
		public List<String> apply(String num, Integer target) {
			List<String> res = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			_apply(string -> res.add(string), num, target, 0, (long) 0, (long) 0, sb);
			return res;
		}

		private void _apply(Consumer<String> consumer, String num, int target, int index, long sum, long lastSum,
				StringBuilder sb) {
			if (index == num.length()) {
				if (sum == target) {
					consumer.accept(sb.toString());
				}
				return;
			}
			
			if (index == 0) {
				long currSum = 0L;
				for (int i = index; i < num.length(); i++) {
					currSum = currSum * 10 + (num.charAt(i) - '0');
					sb.append(num.charAt(i));
					_apply(consumer, num, target, i + 1, currSum, currSum, sb);
					if (num.charAt(index) == '0')
						break;
				}
			} else {
				int len = sb.length();
				for (OP op : OP.values()) {
					if (op == OP.JOIN) continue;
					sb.append(op.symbol);
					long currSum = 0L;
					for (int i = index; i < num.length(); i++) {
						currSum = currSum * 10 + (num.charAt(i) - '0');
						sb.append(num.charAt(i));
						if (op == OP.PLUS) {
							_apply(consumer, num, target, i + 1, sum + currSum, currSum, sb);
						} else if (op == OP.MINUS) {
							_apply(consumer, num, target, i + 1, sum - currSum, -currSum, sb);
						} else if (op == OP.MULT) {
							_apply(consumer, num, target, i + 1, sum + lastSum * currSum - lastSum, lastSum * currSum, sb);
						}
						if (num.charAt(index) == '0')
							break;
					}
					sb.setLength(len);
				}
			}
		}

	}

	public static class Solver implements Problem {

		BasicCalculator.Solver basicCalulator = new BasicCalculator.BnfSolver();

		@Override
		public List<String> apply(String t, Integer u) {

			List<String> res = new ArrayList<>();
			_apply(exp -> res.add(exp), t, 0, u, new StringBuilder());
			return res;
		}

		void _apply(Consumer<String> consumer, String t, int index, int u, StringBuilder sb) {
			if (index == t.length()) {
				if (evaluate(sb) == u) {
					consumer.accept(sb.toString());
				}
				return;
			}
			int length = sb.length();
			if (sb.length() > 0) {
				for (OP op : OP.values()) {
					sb.setLength(length);
					sb.append(op.symbol);
					sb.append(t.charAt(index));
					if (op == OP.JOIN) {
						if (index > 0 && t.charAt(index - 1) == '0') { // leading zero
							continue;
						} else if (currentNumberOverflow(sb, index)) {
							continue;
						}
					}
					_apply(consumer, t, index + 1, u, sb);
				}
			} else {
				sb.append(t.charAt(index));
				_apply(consumer, t, index + 1, u, sb);
			}
		}

		boolean currentNumberOverflow(StringBuilder sb, int index) {
			int value = 0;
			try {
				int left = index;
				for (; left > 0; left--) {
					if (OPS.contains(sb.charAt(left)))
						break;
				}
				for (; left <= index; left++) {
					value = Math.multiplyExact(value, 10);
					value = Math.addExact(sb.charAt(left) - '0', value);
				}
			} catch (ArithmeticException e) {
				return true;
			}
			return false;
		}

		int evaluate(StringBuilder sb) {
			return basicCalulator.solve(sb.toString());
		}

	}

	enum OP {
		PLUS("+"), MINUS("-"), MULT("*"), JOIN("");
		String symbol;

		OP(String symbol) {
			this.symbol = symbol;
		}
	}

	static Set<String> OPS = Arrays.asList(OP.values()).stream().map(op -> op.symbol).collect(Collectors.toSet());

	public interface Problem extends BiFunction<String, Integer, List<String>> {

	}
}
