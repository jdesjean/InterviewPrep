package org.ip.array;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/process-restricted-friend-requests/">LC: 2076</a>
 */
public class RestrictedFriendRequest {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new boolean[] {true, false}, 3, new int[][] {{0,1}}, new int[][] {{0,2},{2,1}}};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean[] apply(Integer a, int[][] b, int[][] c) {
			int n = a;
			DSU dsu = new DSU(n);
			boolean[] res = new boolean[c.length];
			for (int i = 0; i < b.length; i++) {
				dsu.restrict(b[i][0], b[i][1]);
			}
			for (int i = 0; i < c.length; i++) {
				res[i] = dsu.union(c[i][0], c[i][1]);
			}
			
			return res;
		}
		static class DSU {
			private int[] parents;
			BitSet[] exclusion;

			public DSU(int size) {
				parents = new int[size];
				exclusion = new BitSet[size];
				for (int i = 0; i < size; i++) {
					parents[i] = i;
					exclusion[i] = new BitSet();
				}
			}
			int find(int x) {
				int px = parents[x];
				if (px == x) {
					return px;
				}
				return parents[x] = find(px);
			}
			void restrict(int x, int y) {
				exclusion[x].set(y);
				exclusion[y].set(x);
			}
			void rerestrict(int x, int y) {
				exclusion[x].stream().forEach(z -> {
					exclusion[z].clear(x);
					exclusion[z].set(y);
				});
				exclusion[y].or(exclusion[x]);
				exclusion[x].clear();
			}
			boolean union(int x, int y) {
				int px = find(x);
				int py = find(y);
				if (px == py) {
					return true;
				} else if (exclusion[px].get(py)) {
					return false;
				}
				rerestrict(px, py);
				parents[px] = find(y);
				return true;
				
			}
		}
		
	}
	interface Problem extends TriFunction<Integer, int[][], int[][], boolean[]> {
		
	}
}
