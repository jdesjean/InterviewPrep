package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/integer-to-english-words/">LC: 273</a>
 */
public class IntegerToEnglish {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				IntegerToEnglish::tc1,
				IntegerToEnglish::tc2,
				IntegerToEnglish::tc3,
				IntegerToEnglish::tc4,
				IntegerToEnglish::tc5,
				IntegerToEnglish::tc6
				);
		Solver[] solvers = new Solver[] {new Iterative()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(999));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(Integer.MAX_VALUE));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(0));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(111));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve(30));
	}
	public static void tc6(Solver solver) {
		System.out.println(solver.solve(100));
	}
	public static class Iterative implements Solver {

		private final static String[] UNITS = new String[] {"Billion", "Million", "Thousand", ""};
		private final static String[] TENS = new String[] {
				"",
				"Ten",
				"Twenty",
				"Thirty",
				"Forty",
				"Fifty",
				"Sixty",
				"Seventy",
				"Eighty",
				"Ninety",
				"Hundred"
		};
		private final static String[] WORDS = new String[] {
				"Zero",
				"One",
				"Two",
				"Three",
				"Four",
				"Five",
				"Six",
				"Seven",
				"Eight",
				"Nine",
				"Ten",
				"Eleven",
				"Twelve",
				"Thirteen",
				"Fourteen",
				"Fifteen",
				"Sixteen",
				"Seventeen",
				"Eighteen",
				"Nineteen"
		};
		
		@Override
		public String solve(int n) {
			if (n < 0) return "";
			if (n == 0) return "Zero";
			StringBuilder sb = new StringBuilder();
			int unit = 1000000000;
			for (int i = 0; i < UNITS.length; i++) {
				int digit = n / unit;
				if (digit > 0) {
					append(sb, belowThousand(digit));
					append(sb, UNITS[i]);
				}
				n = n % unit;
				unit /= 1000;
			}
			return sb.toString();
		}
		
		String belowThousand(int n) {
			StringBuilder sb = new StringBuilder();
			int digit = n / 100;
			if (digit > 0) {
				append(sb, WORDS[digit]);
				append(sb, TENS[10]);
			}
			n = n % 100;
			if (n < WORDS.length) {
				if (n > 0) {
					append(sb, WORDS[n]);
				}
			} else {
				digit = n / 10;
				append(sb, TENS[digit]);
				int mod = n % 10;
				if (mod > 0) {
					append(sb, WORDS[mod]);
				}
			}
			return sb.toString();
		}
		
		void append(StringBuilder sb, String s) {
			if (s.length() > 0 && sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(s);
		}
		
	}
	public interface Solver {
		public String solve(int n);
	}
}
