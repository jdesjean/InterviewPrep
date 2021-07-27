package org.ip.string;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/confusing-number-ii/">LC: 1088</a>
 */
public class ConfusingNumberII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {6, 20};
		Object[] tc2 = new Object[] {19, 100};
		Object[] tc3 = new Object[] {12, 85};
		Object[] tc4 = new Object[] {0, 0};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static char[][] EVEN = new char[][] {{'0','0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};
		private final static char[] ODD = new char[] {'0', '1', '8'};
		private final static int[] CONFUSING_INDEX = new int[] {0,1,2,2,2,2,2,3,3,4};
		@Override
		public int applyAsInt(int operand) {
			return total(operand) - strobogrammatic(operand);
		}
		int total(int n) {
			char[] buf = String.valueOf(n).toCharArray();
			return _total(buf, 0);
		}
		int _total(char[] s, int i) {
			if (i == s.length) return 1;
	        char first = s[i];
	        int res = CONFUSING_INDEX[first - '0'] * (int) (Math.pow(5, s.length - i - 1));
	        if (first == '0' || first == '1' || first == '6' || first == '8' || first == '9') {
	            res += _total(s, i + 1);
	        }
	        return res;
		}
		int strobogrammatic(int n) {
			char[] number = String.valueOf(n).toCharArray();
			AtomicInteger count = new AtomicInteger();
			for (int i = 1; i <= number.length; i++) {
				dfs(buf -> {
					if (buf.length < number.length || compare(buf, number) <= 0) {
						count.incrementAndGet();
					}
				}, new char[i], 0, i - 1);
			}
			return count.get();
		}
		int compare(char[] aa, char[] ab) {
			for (int i = 0, len = Math.min(aa.length, ab.length); i < len; i++) {
	            char a = aa[i];
	            char b = ab[i];
	            if (a != b) {
	                return a - b;
	            }
	        }
	        return ab.length - aa.length;
		}
		void dfs(Consumer<char[]> consumer, char[] buffer, int left, int right) {
			if (left > right) {
				consumer.accept(buffer);
				return;
			}
			if (left == right) {
				for (char c : ODD) {
					buffer[left] = c;
					dfs(consumer, buffer, left + 1, right - 1);
				}
			} else {
				for (int i = left == 0 ? 1 : 0; i < EVEN.length; i++) {
					buffer[left] = EVEN[i][0];
					buffer[right] = EVEN[i][1];
					dfs(consumer, buffer, left + 1, right - 1);
				}
			}
		}
	}
	interface Problem extends IntUnaryOperator {
		
	}
}
