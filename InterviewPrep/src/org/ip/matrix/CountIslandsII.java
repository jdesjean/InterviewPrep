package org.ip.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/number-of-islands-ii/">LC: 305</a>
 */
public class CountIslandsII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,1,2,3}, 3, 3, new int[][] {{0,0},{0,1},{1,2},{2,1}}};
		Object[] tc2 = new Object[] {new int[] {1,2,1}, 2, 2, new int[][] {{0,0},{1,1},{0,1}}};
		Object[] tc3 = new Object[] {new int[] {1,1,2,2}, 3, 3, new int[][] {{0,0},{0,1},{1,2},{1,2}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static int[][] DIRS = new int[][] {{-1, 0},{1,0},{0,-1},{0,1}};
		@Override
		public List<Integer> apply(Integer a, Integer b, int[][] pos) {
			int m = a;
			int n = b;
			UnionFind uf = new UnionFind();
			List<Integer> res = new ArrayList<>();
			for (int i = 0; i < pos.length; i++) {
				int[] p = pos[i];
				int x = pos(m, n, p[0], p[1]);
				res.add(uf.setParent(x));
				for (int j = 0; j < DIRS.length; j++) {
					int r = p[0] + DIRS[j][0];
					int c = p[1] + DIRS[j][1];
					if (r < 0 || c < 0 || r >= m || c >= n) continue;
					int y = pos(m, n, r, c);
					if (uf.map.get(y) == null) continue;
					res.set(i, uf.union(x, y));
				}
			}
			return res;
		}
		static int pos(int m, int n, int r, int c) {
			return r * n + c;
		}
		static class UnionFind {
			Map<Integer, Integer> map = new HashMap<>();
			private int count;
			int setParent(int x) {
				if (map.containsKey(x)) return count;
				map.put(x, x);
				return ++count;
			}
			int find(int x) {
				Integer v = map.get(x);
				if (v != x) {
					v = find(v);
					map.put(x, v);
					return v;
				} else {
					return v; 
				}
			}
			int union(int x, int y) {
				int fx = find(x);
				int fy = find(y);
				if (fx != fy) {
					map.put(fx, fy);
					return --count;
				} else {
					return count;
				}
			}
		}
	}
	interface Problem extends TriFunction<Integer, Integer, int[][], List<Integer>> {
		
	}
}
