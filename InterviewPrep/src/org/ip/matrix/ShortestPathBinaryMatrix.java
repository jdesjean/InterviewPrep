package org.ip.matrix;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
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
		Problem[] solvers = new Problem[] { new Solver(), new SolverAStar() };
		Test.apply(solvers, tcs);
	}
	static class SolverAStar implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			if (value.length == 0 || value[0][0] != 0) return -1;
			return bfs(value);
		}
		int bfs(int[][] value) {
			PriorityQueue<int[]> pq = new PriorityQueue<>(new PosComparator(value.length - 1, value[0].length - 1));
			pq.add(new int[] {1, 0, 0});
			boolean[][] visited = new boolean[value.length][value[0].length];
			while (!pq.isEmpty()) {
				int[] pos = pq.remove();
				if (visited[pos[1]][pos[2]]) {
					continue;
				}
				visited[pos[1]][pos[2]] = true;
				if (pos[1] == value.length - 1 && pos[2] == value[0].length - 1) {
					return pos[0];
				}
				for (int r = -1; r <= 1; r++) {
					for (int c = -1; c <= 1; c++) {
						int rr = pos[1] + r;
						int cc = pos[2] + c;
						if (rr < 0 || rr >= value.length || cc < 0 || cc >= value.length) continue;
						if (value[rr][cc] != 0) continue;
						pq.add(new int[] {pos[0] + 1, rr, cc});
					}
				}
			}
			return -1;
		}
		static class PosComparator implements Comparator<int[]> {
			private int w;
			private int h;

			public PosComparator(int w, int h) {
				this.w = w;
				this.h = h;
			}

			@Override
			public int compare(int[] o1, int[] o2) {
				int s1 = o1[0] + Math.max(h - o1[1], w - o1[2]);
				int s2 = o2[0] + Math.max(h - o2[1], w - o2[2]);
				return Integer.compare(s1, s2);
			}
			
		}
	}
	static class Solver implements Problem {
		@Override
		public int applyAsInt(int[][] value) {
			if (value == null || value.length == 0 || value[0].length == 0 || value[0][0] != 0) return -1;
			Deque<int[]> deque = new LinkedList<>();
			deque.add(new int[] {0,0});
			return bfs(value, deque);
		}
		int bfs(int[][] value, Deque<int[]> deque) {
			int length = 1;
			boolean[][] visited = new boolean[value.length][value[0].length];
			while (!deque.isEmpty()) {
				int size = deque.size();
				while (size-- > 0) {
					int[] pos = deque.remove();
					if (visited[pos[0]][pos[1]]) {
						continue;
					}
					visited[pos[0]][pos[1]] = true;
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
