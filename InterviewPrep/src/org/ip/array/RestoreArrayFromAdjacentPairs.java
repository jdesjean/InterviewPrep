package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/restore-the-array-from-adjacent-pairs/">LC: 1743</a>
 */
public class RestoreArrayFromAdjacentPairs {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,2,3,4}, new int[][] {{2,1},{3,4},{3,2}}};
		Object[] tc2 = new Object[] {new int[] {-2,4,1,-3}, new int[][] {{4,-2},{1,4},{-3,1}}};
		Object[] tc3 = new Object[] {new int[] {100000,-100000}, new int[][] {{100000,-100000}}};
		Object[] tc4 = new Object[] {new int[] {-10,4,-3,3,-1}, new int[][] {{4,-10},{-1,3},{4,-3},{-3,3}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[][] t) {
			Map<Integer, int[]> map = new HashMap<>();
			for (int i = 0; i < t.length; i++) {
				final int j = i;
				map.compute(t[i][0], (k, v) -> v == null ? new int[] {j} : new int[] {v[0], j});
				map.compute(t[i][1], (k, v) -> v == null ? new int[] {j} : new int[] {v[0], j});
			}
			int current = start(map);
			int[] res = new int[t.length + 1];
			int prevP = -1;
			for (int i = 0; i < res.length; i++) {
				int[] pos = map.get(current);
				res[i] = current;
				int p = pos.length == 1 ? pos[0] : pos[0] != prevP ? pos[0] : pos[1];
				if (t[p][0] == current) {
					current = t[p][1];
				} else {
					current = t[p][0];
				}
				prevP = p;
			}
			return res;
		}
		int start(Map<Integer, int[]> map) {
			for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
				if (entry.getValue().length == 1) return entry.getKey();
			}
			throw new RuntimeException("start not found");
		}
	}
	interface Problem extends Function<int[][], int[]> {
		
	}
}
