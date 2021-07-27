package org.ip.matrix;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/path-with-minimum-effort/">LC: 1631</a>
 */
public class PathWithMinimumEffort {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 2 , new int[][] {
			{1,2,2},
			{3,8,2},
			{5,3,5}
		}};
		Object[] tc2 = new Object[] { 1, new int[][] {
			{1,2,3},
			{3,8,4},
			{5,3,5}
		}};
		Object[] tc3 = new Object[] { 0, new int[][] {
			{1,2,1,1,1},
			{1,2,1,2,1},
			{1,2,1,2,1},
			{1,2,1,2,1},
			{1,1,1,2,1},
		}};
		Object[] tc4 = new Object[] { 5, new int[][] {
			{ 4,3, 4,10, 5,5,9,2},
			{10,8, 2,10, 9,7,5,6},
			{ 5,8,10,10,10,7,4,2},
			{ 5,1, 3, 1, 1,3,1,9},
			{ 6,4,10, 6,10,9,4,6},
		}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new SolverDijkstra(), new SolverBinarySearch() };
		Test.apply(solvers, tcs);
	}
	/**
	 * MNlog(max(value))
	 */
	static class SolverBinarySearch implements Problem {
		private final int[][] directions = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};

		@Override
		public int applyAsInt(int[][] value) {
			if (value.length == 0) return 0;
			int max = max(value);
			int l = 0, h = max;
			for (; l < h; ) {
				int mid = l + (h - l) / 2;
				if (canSolve(value, mid)) {
					h = mid;
				} else {
					l = mid + 1;
				}
			}
			return l;
		}
		boolean canSolve(int[][] value, int k) {
			boolean[][] visited = new boolean[value.length][value[0].length];
			return canSolve(value, visited, k, 0, 0, 0);
		}
		boolean canSolve(int[][] value, boolean[][] visited, int k, int l, int c, int v) {
			if (l == value.length - 1 && c == value[0].length - 1) {
				return v <= k;
			}
			if (visited[l][c] || v > k) {
				return false;
			}
			visited[l][c] = true;
			for (int i = 0; i < directions.length; i++) {
				int ll = l + directions[i][0];
				int cc = c + directions[i][1];
				if (ll < 0 || cc < 0 || ll >= value.length || cc >= value[ll].length) continue;
				int vv = Math.abs(value[ll][cc] - value[l][c]);
				if (canSolve(value, visited, k, ll, cc, vv)) return true;
			}
			return false;
		}
		int max(int[][] value) {
			int start = value[0][0];
			int max = Integer.MIN_VALUE;
			for (int l = 0; l < value.length; l++) {
				for (int c = 0; c < value[l].length; c++) {
					max = Math.max(max, Math.abs(value[l][c] - start));
				}
			}
			return max;
		}
	}
	/**
	 * MNlog(MN)
	 */
	static class SolverDijkstra implements Problem {
		private final int[][] directions = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};
		@Override
		public int applyAsInt(int[][] value) {
			if (value.length == 0) return 0;
			boolean[][] visited = new boolean[value.length][value[0].length];
			PriorityQueue<Position> queue = new PriorityQueue<>(new ValueComparator());
			queue.add(new Position(0, 0, 0));
			int max = 0;
			while (!queue.isEmpty()) {
				Position pos = queue.remove();
				if (visited[pos.l][pos.c]) continue;
				visited[pos.l][pos.c] = true;
				int v = value[pos.l][pos.c];
				max = Math.max(max, pos.v);
				if (pos.l == value.length - 1 && pos.c == value[pos.l].length - 1) return max;
				for (int i = 0; i < directions.length; i++) {
					int l = pos.l + directions[i][0];
					int c = pos.c + directions[i][1];
					if (l < 0 || c < 0 || l >= value.length || c >= value[l].length) continue;
					queue.add(new Position(l, c, Math.abs(v - value[l][c])));
				}
			}
			return max;
		}
		static class Position {
			int c;
			int l;
			int v;
			public Position(int l, int c, int v) {
				this.l = l;
				this.c = c;
				this.v = v;
			}
		}
		static class ValueComparator implements Comparator<Position> {

			@Override
			public int compare(Position o1, Position o2) {
				return Integer.compare(o1.v, o2.v);
			}
			
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
