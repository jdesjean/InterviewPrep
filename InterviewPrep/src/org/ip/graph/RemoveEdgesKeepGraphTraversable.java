package org.ip.graph;

import java.util.Arrays;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/">LC: 1579</a>
 */
public class RemoveEdgesKeepGraphTraversable {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, 4, new int[][] {{3,1,2},{3,2,3},{1,1,3},{1,2,4},{1,1,2},{2,3,4}}};
		Object[] tc2 = new Object[] {0, 4, new int[][] {{3,1,2},{3,2,3},{1,1,4},{2,1,4}}};
		Object[] tc3 = new Object[] {-1, 4, new int[][] {{3,2,3},{1,1,2},{2,3,4}}};
		Object[] tc4 = new Object[] {2, 2, new int[][] {{1,1,2},{2,1,2},{3,1,2}}};
		
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Integer apply(Integer t, int[][] u) {
			Arrays.sort(u, (a, b) -> Integer.compare(b[0], a[0]));
			UnionFind alice = new UnionFind(t);
			UnionFind bob = new UnionFind(t);
			int edges = 0;
			for (int[] edge : u) {
				int type = edge[0];
				int x = edge[1];
				int y = edge[2];
				switch(type) {
					case 3:
						edges += (alice.union(x, y) | bob.union(x, y)) ? 1 : 0;
						break;
					case 2:
						edges += bob.union(x, y) ? 1 : 0;
						break;
					case 1:
						edges += alice.union(x, y) ? 1 : 0;
						break;
				}
			}
			return bob.isUnited() && alice.isUnited() ? u.length - edges : -1;
		}
		static class UnionFind {
			private int size;
			private final int[] parents;

			public UnionFind(int size) {
				parents = new int[size + 1];
				for (int i = 0; i <= size; i++) {
					parents[i] = i;
				}
				this.size = size;
			}
			int find(int x) {
				if (parents[x] != x) parents[x] = find(parents[x]);
				return parents[x];
			}
			boolean union(int x, int y) {
				int fx = find(x);
				int fy = find(y);
				if (fx != fy) {
					parents[fx] = fy;
					size--;
					return true;
				}
				return false;
			}
			boolean isUnited() {
				return size == 1;
			}
		}
	}
	interface Problem extends BiFunction<Integer, int[][], Integer> {
		
	}
}
