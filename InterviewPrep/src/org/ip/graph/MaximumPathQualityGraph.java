package org.ip.graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/maximum-path-quality-of-a-graph/">LC:
 * 2065</a>
 */
public class MaximumPathQualityGraph {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 75, new int[] { 0, 32, 10, 43 },
				new int[][] { { 0, 1, 10 }, { 1, 2, 15 }, { 0, 3, 10 } }, 49 };
		Object[] tc2 = new Object[] { 25, new int[] { 5,10,15,20 },
				new int[][] { { 0,1,10 }, {1,2,10 }, { 0,3,10 } }, 30 };
		Object[] tc3 = new Object[] { 7, new int[] { 1,2,3,4 },
				new int[][] { { 0,1,10 }, {1,2,11 }, { 2,3,12 },{1,3,13} }, 50 };
		Object[] tc4 = new Object[] { 16, new int[] { 3,5,17,16,1,6,30,13,6 },
				new int[][] { {0,4,16},{1,5,12},{4,6,31},{3,6,85},{5,6,66},{4,8,13},{1,4,99},{1,2,48},{2,7,15},{0,2,89},{1,7,59},{2,8,46},{0,5,13},{3,5,100},{3,8,77} }, 90 };
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Integer apply(int[] a, int[][] b, Integer c) {
			Map<Integer, List<Path>> g = new HashMap<>();
			for (int i = 0; i < b.length; i++) {
				g.computeIfAbsent(b[i][0], (k) -> new ArrayList<>())
				.add(new Path(b[i][1], b[i][2]));
				g.computeIfAbsent(b[i][1], (k) -> new ArrayList<>())
				.add(new Path(b[i][0], b[i][2]));
			}
			BitSet seen = new BitSet();
			int[][] dp = new int[a.length][c+1];
			return dfs(g, dp, a, seen, 0, c, 0);
		}
		int dfs(Map<Integer, List<Path>> map, int[][] dp, int[] values, BitSet seen, int edge, int time, int val) {
			List<Path> edges = map.get(edge);
			boolean edgeSeen = seen.get(edge);
			if (!edgeSeen) {
				val += values[edge];
			}
			
			int max = edge == 0 ? val : 0;
			if(edges == null || (dp[edge][time] != 0 && dp[edge][time] > val)) { // Why > instead of >=?
				return max;
			}
			seen.set(edge);
			dp[edge][time] = val;
			for (Path path : edges) {
				if (time < path.time) continue;
				max = Math.max(max, dfs(map, dp, values, seen, path.node, time - path.time, val));
			}
			if (!edgeSeen) {
				seen.clear(edge);
			}
			return max;
		}
	}
	static class Path {
		int node;
		int time;
		public Path(int node, int time) {
			super();
			this.node = node;
			this.time = time;
		}
	}
	interface Problem extends TriFunction<int[], int[][], Integer, Integer> {
		
	}
}
