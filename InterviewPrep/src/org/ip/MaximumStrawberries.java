package org.ip;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/396646/">OA 2020</a>
 * <a href="https://leetcode.com/problems/house-robber/">LC: 198</a>
 */
public class MaximumStrawberries {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				MaximumStrawberries::tc0, 
				MaximumStrawberries::tc1, 
				MaximumStrawberries::tc2, 
				MaximumStrawberries::tc3,
				MaximumStrawberries::tc4,
				MaximumStrawberries::tc5);
		Solver[] solvers = new Solver[] {new DP2(), new DP(), new Recursive(), new Recursive2(), new Binary()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc0(Solver solver) {
		System.out.println(solver.solve(new int[] {50, 10}, 100));
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[] {50, 10, 20, 30, 40}, 100));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[] {50, 20, 10}, 100));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new int[] {50, 20, 30, 10}, 100));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(new int[] {50, 10, 20, 30, 40}, 60));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve(new int[] {1, 2, 3, 4}, 10));
	}
	public interface Solver {
		public int solve(int[] a, int max);
	}
	public static class DP2 implements Solver {

		@Override
		public int solve(int[] a, int max) {
			boolean[][] f = new boolean[2][max + 1];
			boolean[][] g = new boolean[2][max + 1];
			for(int i = 0; i < f.length; i++) {
				f[i][0] = true;
				g[i][0] = true;
			}
			int m = 0;
			for (int i = 1; i <= a.length; i++) {
				int ai = a[i - 1];
				int im = i % 2;
				int ipm = (i - 1) % 2;
				for (int j = 1; j < f[0].length; j++) {
					if (j - ai >= 0) {
						f[im][j] = g[ipm][j - ai];
					}
					g[im][j] = g[ipm][j] || f[ipm][j];
					if (f[im][j] || g[im][j]) {
						m = Math.max(m, j);
					}
				}
			}
			return m;
		}
	}
	public static class DP implements Solver {

		@Override
		public int solve(int[] a, int max) {
			int[] cache = new int[max + 1];
			cache[0] = Integer.MIN_VALUE;
			int m = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] == 0) continue;
				for (int j = cache.length; j >= a[i]; j--) {
					int value = cache[j - a[i]]; 
					if (value != 0 && value != i) {
						cache[j] = i + 1;
						m = Math.max(m, j);
					}
				}
			}
			return m;
		}
	}
	public static class Binary implements Solver {

		@Override
		public int solve(int[] a, int max) {
			int res = 0;
			for (int i = 0; i < (int)Math.pow(2, a.length); i++) {
				if (!parity(i)) continue;
				int sum = sum(a, i);
				if (sum <= max) {
					res = Math.max(res, sum);
				}
			}
			return res;
		}
		int sum(int[] a, int v) {
			int sum = 0;
			for (int i = 0; i < a.length; i++) {
				boolean b = (v & 1) == 1;
				if (b) {
					sum += a[i];
				}
				v >>= 1;
			}
			return sum;
		}
		boolean parity(int v) {
			boolean prev = (v & 1) == 1;
			v >>= 1;
			while (v > 0) {
				boolean b = (v & 1) == 1;
				if (prev == true && b == true) return false;
				prev = b;
				v >>= 1;
			}
			return true;
		}
	}
	public static class Recursive2 implements Solver {

		@Override
		public int solve(int[] a, int max) {
			return solve(a, 0, max, 0, false);
		}
		private int solve(int[] a, int i, int max, int current, boolean prev) {
			if (i == a.length) {
				return current;
			}
			int m = solve(a, i + 1, max, current, false);
 			int v = a[i] + current;
			if (v <= max && !prev) {
				m = Math.max(m, solve(a, i + 1, max, v, true));
			}
			return m;
		}
		
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(int[] a, int max) {
			return solve(a, 0, max, 0, Integer.MIN_VALUE);
		}
		private int solve(int[] a, int i, int max, int current, int prev) {
			if (i == a.length) {
				return current;
			}
			int m = solve(a, i + 1, max, current, prev);
 			int v = a[i] + current;
			if (v <= max && prev != i - 1) {
				m = Math.max(m, solve(a, i + 1, max, v, i));
			}
			return m;
		}
		
	}
}
