package org.ip.matrix;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/shortest-path-in-binary-matrix/">LC: 1091</a>
 */
public class ShortestPathBinaryMatrix {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[][] {{0,1},{1,0}}};
		Object[] tc2 = new Object[] {4, new int[][] {{0,0,0},{1,1,0},{1,1,0}}};
		Object[] tc3 = new Object[] {-1, new int[][] {
			{1,0,0},
			{1,1,0},
			{1,1,0}}};
		Object[] tc4 = new Object[] {14, new int[][] {
			{0,1,1,0,0,0},
			{0,1,0,1,1,0},
			{0,1,1,0,1,0},
			{0,0,0,1,1,0},
			{1,1,1,1,1,0},
			{1,1,1,1,1,0}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static int[][] DIRS = new int[][] {{0,1},{1,0},{1,1}};
		@Override
		public int applyAsInt(int[][] value) {
			if (value == null || value.length == 0 || value[0].length == 0 || value[0][0] != 0) return -1;
			Deque<int[]> deque = new LinkedList<>();
			deque.add(new int[] {0,0});
			return bfs(value, deque);
		}
		int bfs(int[][] value, Deque<int[]> deque) {
			int length = 1;
			while (!deque.isEmpty()) {
				int size = deque.size();
				while (size-- > 0) {
					int[] pos = deque.remove();
					if (pos[0] == value.length - 1 && pos[1] == value[pos[0]].length - 1) {
						return length;
					}
					for (int row = -1; row <= 1; row++) {
						for (int col = -1; col <= 1; col++) {
							int row2 = pos[0] + row;
							int col2 = pos[1] + col;
							if (row2 < 0 || col2 < 0 || row2 >= value.length || col2 >= value[row2].length) continue;
							if (value[row2][col2] != 0) continue;
							value[row2][col2] = -1;
							deque.add(new int[] {row2,col2});
						}
					}
				}
				length++;
			}
			return -1;
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
