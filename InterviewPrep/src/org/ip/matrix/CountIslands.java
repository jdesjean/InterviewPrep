package org.ip.matrix;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/number-of-islands/">LC: 200</a>
 */
public class CountIslands {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 1, new char[][] {
			{'1', '1', '1', '1', '0'},
			{'1', '1', '0', '1', '0'},
			{'1', '1', '0', '0', '0'},
			{'0', '0', '0', '0', '0'}}};
		Object[] tc2 = new Object[] { 3, new char[][] {
			{'1', '1', '0', '0', '0'},
			{'1', '1', '0', '0', '0'},
			{'0', '0', '1', '0', '0'},
			{'0', '0', '0', '1', '1'}}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new BFSSolver(), new DFSSolver() };
		Test.apply(solvers, tcs);
	}
	static class DFSSolver implements Problem {
		private final static int[][] directions = new int[][] {{0,-1},{0,1},{-1,0},{1,0}};
		@Override
		public int applyAsInt(char[][] matrix) {
			int count = 0;
			for (int x = 0; x < matrix.length; x++) {
				for (int y = 0; y < matrix[0].length; y++) {
					if (matrix[x][y] != '1') continue;
					dfs(matrix, new int[] {x, y});
					count++;
				}
			}
			return count;
		}
		public static void dfs(char[][] matrix, int[] start) {
			matrix[start[0]][start[1]] = '2';
			int x = start[0];
			int y = start[1];
			for (int i = 0; i < directions.length; i++) {
				int xx = x + directions[i][0];
				int yy = y + directions[i][1];
				if (xx < 0 || yy < 0 || xx >= matrix.length || yy >= matrix[xx].length) continue;
				if (matrix[xx][yy] != '1') continue;
				matrix[xx][yy] = '2';
				dfs(matrix, new int[] {xx, yy});
			}
		}
		
	}
	static class BFSSolver implements Problem {
		private final static int[][] directions = new int[][] {{0,-1},{0,1},{-1,0},{1,0}};
		@Override
		public int applyAsInt(char[][] matrix) {
			int count = 0;
			for (int x = 0; x < matrix.length; x++) {
				for (int y = 0; y < matrix[x].length; y++) {
					if (matrix[x][y] != '1') continue;
					bfs(matrix, new int[] {x, y});
					count++;
				}
			}
			return count;
		}
		public static void bfs(char[][] matrix, int[] start) {
			Queue<int[]> queue = new LinkedList<int[]>();
			queue.add(start);
			matrix[start[0]][start[1]] = '2';
			while (!queue.isEmpty()) {
				int[] current = queue.remove();
				int x = current[0];
				int y = current[1];
				for (int i = 0; i < directions.length; i++) {
					int xx = x + directions[i][0];
					int yy = y + directions[i][1];
					if (xx < 0 || yy < 0 || xx >= matrix.length || yy >= matrix[xx].length) continue;
					if (matrix[xx][yy] != '1') continue;
					matrix[xx][yy] = '2';
					queue.add(new int[] {xx, yy});
				}
			}
		}
		
	}
	interface Problem extends ToIntFunction<char[][]> {
		
	}
}
