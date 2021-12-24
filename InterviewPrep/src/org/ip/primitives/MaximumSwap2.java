package org.ip.primitives;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-swap/>LC: 670</a>
 */
public class MaximumSwap2 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4231, 1234};
		Object[] tc2 = new Object[] {4321, 4312};
		Object[] tc3 = new Object[] {4321, 4321};
		Object[] tc4 = new Object[] {7236, 2736};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	public static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int operand) {
			char[] digits = Integer.toString(operand).toCharArray();
			int[] pos = pos(digits);
			for (int i = 0; i < digits.length; i++) {
				for (int k = 9; k > digits[i] - '0'; k--) {
	                if (pos[k] > i) {
	                	swap(digits, i, pos[k]);
	                	return Integer.valueOf(new String(digits));
	                }
				}
			}
			return operand;
		}
		int[] pos(char[] digits) {
			int[] pos = new int[10];
			Arrays.fill(pos, -1);
			for (int i = 0; i < digits.length; i++) {
				pos[digits[i] - '0'] = i;
			}
			return pos;
		}
		void swap(char[] a, int i, int j) {
			char tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}
	public static class Solver implements Problem {

		@Override
		public int applyAsInt(int operand) {
			int[] digits = digits(operand);
			int[] pos = pos(digits);
			for (int i = 0, next = 10; i < digits.length; i++) {
				next = next(pos, next);
				if (digits[i] == next) continue;
				swap(digits, i, pos[next]);
				break;
			}
			return toInt(digits);
		}
		int toInt(int[] digits) {
			int res = 0;
			for (int i = 0; i < digits.length; i++) {
				res *= 10;
				res += digits[i];
			}
			return res;
		}
		void swap(int[] digits, int i, int j) {
			int tmp = digits[i];
			digits[i] = digits[j];
			digits[j] = tmp;
		}
		int[] digits(int operand) {
			int length = length(operand);
			int[] digits = new int[length];
			for (int i = length - 1; operand != 0; operand /= 10, i--) {
				digits[i] = operand % 10;
			}
			return digits;
		}
		int next(int[] digits, int j) {
			for (int i = j - 1; i >= 0; i--) {
				if (digits[i] != -1) return i;
			}
			return -1;
		}
		int length(int operand) {
			int length = 0;
			for (; operand != 0; operand /= 10, length++) {}
			return length;
		}
		int[] pos(int[] digits) {
			int[] pos = new int[10];
			Arrays.fill(pos, -1);
			for (int i = 0; i < digits.length; i++) {
				pos[digits[i]] = i;
			}
			return pos;
		}
	}
	
	public interface Problem extends IntUnaryOperator {
		
	}
}
