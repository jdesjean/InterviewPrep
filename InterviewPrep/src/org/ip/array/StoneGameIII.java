package org.ip.array;

import java.util.Arrays;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/stone-game-iii/">LC: 1406</a>
 */
public class StoneGameIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"Bob", new int[] {1,2,3,7}};
		Object[] tc2 = new Object[] {"Alice", new int[] {1,2,3,-9}};
		Object[] tc3 = new Object[] {"Tie", new int[] {1,2,3,6}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver(), new DPSolver()};
		Test.apply(solvers, tcs);
	}
	private final static String[] WINNER = new String[] {"Bob", "Tie", "Alice"};
	private static int clamp(int v) {
		return v > 0 ? 1 : v < 0 ? -1 : 0;
	}
	static class DPSolver implements Problem {

		@Override
		public String apply(int[] t) {
			int[] res = new int[t.length];
			Arrays.fill(res, Integer.MIN_VALUE);
			for (int i = t.length - 1; i >= 0; i--) {
				int sum = 0;
				for (int j = i; j <= Math.min(i + 2, t.length - 1); j++) {
					sum += t[j];
					int next = j < t.length - 1 ? res[j + 1] : 0;
					res[i] = Math.max(res[i], sum - next);
				}
			}
			
			return WINNER[clamp(res[0]) + 1];
		}
		
	}
	static class Solver implements Problem {

		@Override
		public String apply(int[] t) {
			int res = _apply(t, 0);
			return WINNER[clamp(res) + 1];
		}
		int _apply(int[] t, int index) {
			if (index >= t.length) {
				return 0;
			}
			int max = Integer.MIN_VALUE;
			int sum = 0;
			for (int i = index; i <= Math.min(index + 2, t.length - 1); i++) {
				sum += t[i];
				max = Math.max(max, sum - _apply(t, i + 1));
			}
			return max;
		}
	}
	interface Problem extends Function<int[], String> {
		
	}
}
