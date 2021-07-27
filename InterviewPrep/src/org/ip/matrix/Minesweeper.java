package org.ip.matrix;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minesweeper/">LC: 529</a>
 */
public class Minesweeper {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {
				new char[][] {{'B','1','E','1','B'},{'B','1','M','1','B'},{'B','1','1','1','B'},{'B','B','B','B','B'}},
				new char[][]{{'E','E','E','E','E'},{'E','E','M','E','E'},{'E','E','E','E','E'},{'E','E','E','E','E'}},
				new int[] {3,0}};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		@Override
		public char[][] apply(char[][] t, int[] u) {
			if (t[u[0]][u[1]] == 'M') {
				t[u[0]][u[1]] = 'X';
				return t;
			}
			dfs(t, u);
			return t;
		}
		void dfs(char[][] t, int[] u) {
			t[u[0]][u[1]] = 'B';
			int sum = 0;
			for (int r = Math.max(u[0] - 1, 0); r <= Math.min(u[0] + 1, t.length - 1); r++) {
				for (int c = Math.max(u[1] - 1, 0); c <= Math.min(u[1] + 1, t[r].length - 1); c++) {
					if (t[r][c] != 'M') continue;
					sum++;
				}
			}
			if (sum != 0) {
				t[u[0]][u[1]] = Character.forDigit(sum, 10);
				return;
			}
			for (int r = Math.max(u[0] - 1, 0); r <= Math.min(u[0] + 1, t.length - 1); r++) {
				for (int c = Math.max(u[1] - 1, 0); c <= Math.min(u[1] + 1, t[r].length - 1); c++) {
					if (t[r][c] != 'E') continue;
					dfs(t, new int[] {r,c});
				}
			}
		}
		
	}
	interface Problem extends BiFunction<char[][], int[], char[][]> {
		
	}
}
