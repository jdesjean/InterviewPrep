package org.ip.matrix;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/longest-increasing-path-in-a-matrix/">LC: 329</a>
 */
public class LongestIncreasingPath {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 4, new int[][] {
			{9,9,4},
			{6,6,8},
			{2,1,1}
		}};
		Object[] tc2 = new Object[] { 4, new int[][] {
			{3,4,5},
			{3,2,6},
			{2,2,1}
		}};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		final static int[][] directions = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};
		final static int[] SENTINEL = new int[] {0,0};
		@Override
		public int applyAsInt(int[][] m) {
			int[][] res = new int[m.length][m[0].length];
			Deque<int[]> deque = new LinkedList<>();
			addLeaves(m, deque);
			deque.addLast(SENTINEL);
			int length = 1;
			while (!deque.isEmpty()) {
				int[] pos = deque.removeFirst();
				if (pos == SENTINEL) {
					if (!deque.isEmpty()) {
						deque.addLast(SENTINEL);
						length++;
					}
					continue;
				}
				if (res[pos[0]][pos[1]] >= length) continue;
				res[pos[0]][pos[1]] = length;
				for (int i = 0; i < directions.length; i++) {
					int l = pos[0] + directions[i][0];
					int c = pos[1] + directions[i][1];
					if (l < 0 || l >= m.length || c < 0 || c >= m[l].length) continue;
					if (m[l][c] <= m[pos[0]][pos[1]]) continue;
					deque.addLast(new int[] {l,c});
				}
			}
			return length;
		}
		void addLeaves(int[][] m, Deque<int[]> deque) {
			for (int l = 0; l < m.length; l++) {
				for (int c = 0; c < m[l].length; c++) {
					int min = Integer.MAX_VALUE;
					for (int i = 0; i < directions.length; i++) {
						int ll = l + directions[i][0];
						int cc = c + directions[i][1];
						if (ll < 0 || ll >= m.length || cc < 0 || cc >= m[ll].length) continue;
						min = Math.min(min, m[ll][cc]);
					}
					if (m[l][c] <= min) {
						deque.addLast(new int[] {l, c});
					}
				}
			}
		}
	}
	interface Problem extends ToIntFunction<int[][]>{
		
	}
}
