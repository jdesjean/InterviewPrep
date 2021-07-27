package org.ip.primitives;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/multiply-strings/">LC: 43</a>
 */
public class MultiplyString {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				MultiplyString::tc1,
				MultiplyString::tc2,
				MultiplyString::tc3,
				MultiplyString::tc4
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
		System.out.println(solver.solve("123", "456"));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve("123", "9"));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve("2", "3"));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve("99", "0"));
	}
	public static class Iterative implements Solver {

		@Override
		public String solve(String num1, String num2) {
			if (num1 == null || num2 == null) return null;
			if (num1.length() == 0 || num2.length() == 0) return "";
			if (num1.charAt(0) == '0' || num2.charAt(0) == '0') return "0";
			char[] res = new char[num1.length() + num2.length()];
			for (int i = 0; i < res.length; i++) {
				res[i] = Character.forDigit(0, 10);
			}
			char[] prev = new char[num1.length() + num2.length()];
			int r = 0;
			for (int i = num2.length() - 1; i >= 0; i--) {
				r = multiply(prev, num1, num2, i);
				r = add(res, prev, num2.length() - 1 - i, r);
			}
			revert(res, 0, r);
			return new String(res, 0, r + 1);
		}
		
		void revert(char[] res, int i, int j) {
			for (; i < j; i++, j--) {
				swap(res, i, j);
			}
		}
		
		void swap(char[] res, int i, int j) {
			char tmp = res[i];
			res[i] = res[j];
			res[j] = tmp;
		}
		
		int multiply(char[] res, String num1, String num2, int i) {
			int n2 = Character.getNumericValue(num2.charAt(i));
			int rest = 0;
			for (int j = num1.length() - 1; j >= 0; j--) {
				int n1 = Character.getNumericValue(num1.charAt(j));
				int v = n1 * n2 + rest;
				rest = v / 10;
				int mod = v % 10;
				res[num1.length() - 1 - j + num2.length() - 1 - i] = Character.forDigit(mod, 10);
			}
			
			if (rest > 0) {
				res[num1.length() + num2.length() - 1 - i] = Character.forDigit(rest, 10);
				return num1.length() + num2.length() - 1 - i;
			} else {
				return num1.length() + num2.length() - 1 - i - 1;
			}
		}
		
		int add(char[] res, char[] prev, int l, int r) {
			int rest = 0;
			for (int i = l; i <= r; i++) {
				int n1 = Character.getNumericValue(prev[i]);
				int n2 = Character.getNumericValue(res[i]);
				int v = n1 + n2 + rest;
				int mod = v % 10;
				res[i] = Character.forDigit(mod, 10);
				rest = v / 10;
			}
			if (rest > 0) {
				res[r + 1] = Character.forDigit(rest, 10);
				return r + 1;
			} else {
				return r;
			}
		}
	}
	public interface Solver {
		public String solve(String num1, String num2);
	}
}
