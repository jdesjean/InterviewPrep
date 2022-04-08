package org.ip.matrix;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/swim-in-rising-water/">LC: 778</a>
 */
public class SwimRisingWater {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new int[][] {{0,2},{1,3}}};
		Object[] tc2 = new Object[] {16, new int[][] {{0,1,2,3,4},{24,23,22,21,5},{12,13,14,15,16},{11,17,18,19,20},{10,9,8,7,6}}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {
		private final static int[][] DIRS = new int[][] {{0,1},{0,-1},{-1,0},{1,0}};
		
		@Override
		public int applyAsInt(int[][] value) {
			if (value == null || value.length == 0) return 0;
			int r = value.length * value[0].length - 1;
			for (int l = 0; l < r; ) {
				int z = l + (r - l) / 2;
				if (possible(value, z)) {
					r = z;
				} else {
					l = z + 1;
				}
			}
			return r;
		}
		boolean possible(int[][] value, int z) {
			BitSet visited = new BitSet();
			Deque<Integer> deque = new ArrayDeque<>();
			deque.add(0);
			while (!deque.isEmpty()) {
				int i = deque.remove();
				for (int[] dir : DIRS) {
					int l = i / value.length + dir[0];
					int c = i % value.length + dir[1];
					int j = l * value.length + c;
					if (l < 0|| c < 0 || l >= value.length || c >= value[l].length || value[l][c] > z || visited.get(j)) continue;
					visited.set(j);
					if (visited.get(0) && visited.get(value.length * value.length - 1)) return true;
					deque.add(j);
				}
			}
			return false;
		}
	}
	static class Solver2 implements Problem {
		private final static int[][] DIRS = new int[][] {{0,1},{0,-1},{-1,0},{1,0}};

		@Override
		public int applyAsInt(int[][] value) {
			if (value == null || value.length == 0) return 0;
			int[] grid = new int[value.length * value[0].length];
			for (int i = 0; i < grid.length; i++) {
				int l = i / value.length;
				int c = i % value.length;
				grid[value[l][c]] = i;
			}
			DSU dsu = new DSU(grid.length);
			for (int w = 0; w < grid.length; w++) {
				int v = grid[w];
				int l = v / value.length;
				int c = v % value.length;
				for (int[] dir : DIRS) {
					int x = l + dir[0];
					int y = c + dir[1];
					int z = x * value.length + y;
					if (x < 0 || y < 0 || x >= value.length || y >= value.length || value[x][y] >= w) continue;
					dsu.union(v, z);
				}
				if (dsu.find(0) == dsu.find(grid.length - 1)) {
					return w;
				}
			}
			return -1;
		}
		static class DSU {
			int[] parents;
			int[] ranks;
			public DSU(int size) {
				parents = new int[size];
				ranks = new int[size];
				for (int i = 0; i < parents.length; i++) {
					parents[i] = i;
					ranks[i] = 1;
				}
			}
			int find(int x) {
				int v = parents[x];
				if (x == v) return x;
				parents[x] = find(v);
				return parents[x];
			}
			void union(int x, int y) {
				int vx = find(x);
				int vy = find(y);
				if (vx == vy) return;
				if (ranks[vx] <= ranks[vy]) {
					parents[vx] = vy;
					ranks[vy] += ranks[vx];
				} else {
					parents[vy] = vx;
					ranks[vx] += ranks[vy];
				}
			}
		}
	}
	static class Solver implements Problem {

		private final static int[][] DIRS = new int[][] {{0,1},{0,-1},{-1,0},{1,0}};
		
		@Override
		public int applyAsInt(int[][] value) {
			PriorityQueue<int[]> pq = new PriorityQueue<>(new WaterComparator(value));
			pq.add(new int[] {0,0});
			BitSet visited = new BitSet();
			int res = 0;
			while (!pq.isEmpty()) {
				int[] current = pq.remove();
				res = Math.max(value(value, current), res);
				if (current[0] == value.length - 1 && current[1] == value[current[0]].length - 1) return res;
				for (int[] dir : DIRS) {
					int l = dir[0] + current[0];
					int c = dir[1] + current[1];
					int[] next = new int[] {l,c};
					int j = l * value.length + c;
					if (l < 0 || c < 0 || l >= value.length || c >= value[l].length || visited.get(j)) continue;
					visited.set(j);
					pq.add(next);
				}
			}
			return res;
		}
		static int value(int[][] value, int[] o1) {
			return value[o1[0]][o1[1]];
		}
		static class WaterComparator implements Comparator<int[]> {
			private int[][] value;

			public WaterComparator(int[][] value) {
				this.value = value;
			}

			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(value(value, o1), value(value, o2));
			}
			
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
