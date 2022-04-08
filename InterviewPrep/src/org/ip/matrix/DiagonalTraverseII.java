package org.ip.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/diagonal-traverse-ii/">LC: 1424</a>
 */
public class DiagonalTraverseII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {1,4,2,7,5,3,8,6,9}, Arrays.asList(
			Arrays.asList(new Integer[]{1,2,3}),
			Arrays.asList(new Integer[]{4,5,6}),
			Arrays.asList(new Integer[]{7,8,9})
		)};
		Object[] tc2 = new Object[] { new int[] {1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16}, Arrays.asList(
			Arrays.asList(new Integer[]{1,2,3,4,5}),
			Arrays.asList(new Integer[]{6,7}),
			Arrays.asList(new Integer[]{8}),
			Arrays.asList(new Integer[]{9,10,11}),
			Arrays.asList(new Integer[]{12,13,14,15,16})
		)};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		
		@Override
		public int[] apply(List<List<Integer>> t) {
			if (t == null || t.size() == 0) return new int[0];
			List<List<Integer>> res = new ArrayList<>(t.size() * 2 - 1); // size when square
			for (int l = t.size() - 1; l >= 0; l--)  {
				for (int c = t.get(l).size() - 1; c >= 0; c--) {
					int pos = l + c;
					while (pos >= res.size()) {
						res.add(new ArrayList<>());
					}
					res.get(pos).add(t.get(l).get(c));
				}
			}
			return res.stream().flatMap(list -> list.stream()).mapToInt(Integer::intValue).toArray();
		}
		
	}
	interface Problem extends Function<List<List<Integer>>, int[]> {
		
	}
}
