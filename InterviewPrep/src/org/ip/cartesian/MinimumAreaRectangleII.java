package org.ip.cartesian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-area-rectangle-ii/">LC: 963</a>
 */
public class MinimumAreaRectangleII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 2, new int[][] {{1,2},{2,1},{1,0},{0,1}} };
		Object[] tc2 = new Object[] { 1, new int[][] {{0,1},{2,1},{1,1},{1,0},{2,0}} };
		Object[] tc3 = new Object[] { 0, new int[][] {{0,3},{1,2},{3,1},{1,3},{2,1}} };
		Object[] tc4 = new Object[] { 2, new int[][] {{3,1},{1,1},{0,1},{2,1},{3,3},{3,2},{0,2},{2,3}} };
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public double applyAsDouble(int[][] value) {
			Map<String, List<int[]>> map = new HashMap<>();
			for (int i = 0; i < value.length; i++) {
				for (int j = i + 1; j < value.length; j++) {
					long dist = dist1(value[i], value[j]);
					double x = (value[i][0] + value[j][0]) / 2d;
					double y = (value[i][1] + value[j][1]) / 2d;
					String key = dist + ":" + x + ":" + y;
					List<int[]> list = map.computeIfAbsent(key, (k) -> new ArrayList<>());
					list.add(new int[] {i,j});
				}
			}
			double dist = Double.MAX_VALUE;
			for (List<int[]> list : map.values()) {
				if (list.size() == 1) continue;
				for (int i = 0; i < list.size(); i++) {
					for (int j = i + 1; j < list.size(); j++) {
						int p1 = list.get(i)[0];
						int p2 = list.get(j)[0];
						int p3 = list.get(j)[1];
						double l1 = dist2(value[p1], value[p2]);
						double l2 = dist2(value[p1], value[p3]);
						dist = Math.min(dist, l1 * l2);
					}
				}
			}
			return dist == Double.MAX_VALUE ? 0 : dist;
		}
		
	}
	static int dist1(int[] a, int[] b) {
		return (int) Math.pow(a[0] - b[0], 2) + (int)Math.pow(a[1] - b[1], 2);
	}
	static double dist2(int[] a, int[] b) {
		return Math.sqrt(dist1(a, b));
	}
	interface Problem extends ToDoubleFunction<int[][]> {
		
	}
}
