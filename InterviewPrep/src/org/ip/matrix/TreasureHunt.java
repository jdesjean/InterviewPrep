package org.ip.matrix;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/584289/">OA 2020</a>
 */
public class TreasureHunt {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				TreasureHunt::tc1,
				TreasureHunt::tc2);
		Solver[] solvers = new Solver[] {new DP(), new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[][] {
			{9, 10, 15, 12, 11},
			{7, 5, 11, 6, 8},
			{4, 1, 27, 13, 17},
			{2, 4, 18, 2, 1},
			{15, 3, 22, 6, 10},
			{8, 2, 5, 9, 6}
		}));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[][] {
			{3, 6, 8, 2},
			{5, 2, 4, 3},
			{1, 1, 20, 10},
			{1, 1, 20, 10},
			{1, 1, 20, 10},
		}));
	}
	public static class DP implements Solver {

		@Override
		public int solve(int[][] m) {
			int w = Math.floorDiv(m.length - 1, 2);
			int[][] cache = new int[m[0].length][m[0].length];
			cache[0][m[0].length - 1] = m[0][0] + m[0][m[0].length - 1];
			
			for (int l = 1; l < m.length; l++) {
				int[][] next = new int[m[0].length][m[0].length];
				for (int c1 = 0; c1 <= w; c1++) {
					for (int c2 = m[0].length - w - 1; c2 <= m[0].length - 1; c2++) {
						if (cache[c1][c2] == 0) continue;
						for (int cc1 = Math.max(c1 - 1, 0); cc1 <= Math.min(w, c1 + 1); cc1++) {
							for (int cc2 = Math.min(c2 + 1, m[0].length - 1); cc2 >= Math.max(c2 - 1, m[0].length - w - 1); cc2--) {
								int val = cc1 != cc2 ? m[l][cc1] + m[l][cc2] : m[l][cc1];
								next[cc1][cc2] = Math.max(next[cc1][cc2], cache[c1][c2] + val);
							}
						}
					}
				}
				cache = next;
			}
			return cache[0][m[0].length - 1];
		}
		
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(int[][] m) {
			return solve(m, 0, 0, m[0].length - 1, 0);
		}
		public int solve(int[][] m, int l, int c1, int c2, int sum) {
			if (l == m.length) {
				return sum;
			}
			//2->0, 3->1, 4->1, 5->2
			int w = Math.min(m.length - 1 - l, l);
			int max = 0;
			for (int cc1 = Math.max(c1 - 1, 0); cc1 <= Math.min(w, c1 + 1); cc1++) {
				for (int cc2 = Math.min(c2 + 1, m[0].length - 1); cc2 >= Math.max(c2 - 1, m[0].length - w - 1); cc2--) {
					int val = cc1 != cc2 ? m[l][cc1] + m[l][cc2] : m[l][cc1];
					max = Math.max(max, solve(m, l + 1, cc1, cc2, sum + val));
				}
			}
			return max;
		}
	}
	public interface Solver {
		public int solve(int[][] m);
	}
}
