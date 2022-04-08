package org.ip.string;

import java.util.function.IntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/integer-to-english-words/">LC: 273</a>
 */
public class IntegerToEnglish {
	public static void main(String[] s) {
		var tc1 = new Object[] {"Nine Hundred Ninety Nine", 999};
		var tc2 = new Object[] {"Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven", Integer.MAX_VALUE};
		var tc3 = new Object[] {"Zero", 0};
		var tc4 = new Object[] {"One Hundred Eleven", 111};
		var tc5 = new Object[] {"Thirty", 30};
		var tc6 = new Object[] {"One Hundred", 100};
		var tc7 = new Object[] {"One Million", 1000000};
		var tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7};
		var solvers = new Problem[] {new Solver(), new Iterative()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {
		private final static String[] ZERO_TO_NINE = new String[] {
				"",
				"One",
				"Two",
				"Three",
				"Four",
				"Five",
				"Six",
				"Seven",
				"Eight",
				"Nine"
		};
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
				"Ninety"
		};
		private final static String[] GROUPS = new String[] {
				"",
				"Thousand",
				"Million",
				"Billion"
		};
		private final static String[] TEENS = new String[] {
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
		public String apply(int value) {
			if (value < 0) return null;
			if (value == 0) return "Zero";
			StringBuilder sb = new StringBuilder();
			for (int i = 0; value > 0; i++, value /= 1000) {
				int current = value % 1000;
				if (current == 0) continue;
				
				appendReversed(sb, GROUPS[i]);
				
				int hundreds = current / 100;
				int tens = (current % 100) / 10;
				int digit = current % 10;
				
				if (tens == 1) {
					appendReversed(sb, TEENS[digit]);
				}
				else {
					appendReversed(sb, ZERO_TO_NINE[digit]);
					appendReversed(sb, TENS[tens]);
				}
				if (hundreds > 0) {
					appendReversed(sb, "Hundred");
					appendReversed(sb, ZERO_TO_NINE[hundreds]);
				}
			}
			return sb.reverse().toString();
		}
		void appendReversed(StringBuilder sb, String s) {
			if (!sb.isEmpty() && s.length() > 0) {
				sb.append(" ");
			}
			for (int i = s.length() - 1; i >= 0; i--) {
				sb.append(s.charAt(i));
			}
		}
	}
	public static class Iterative implements Problem {

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
		public String apply(int n) {
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
	public interface Problem extends IntFunction<String> {
		
	}
}
