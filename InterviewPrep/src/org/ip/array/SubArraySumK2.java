package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/subarray-sum-equals-k/">LC: 560</a>
 */
public class SubArraySumK2 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[] {1,1,1}, 2};
		Object[] tc2 = new Object[] {2, new int[] {1,2,3}, 3};
		Object[] tc3 = new Object[] {1, new int[] {1,0,1}, 2};
		Object[] tc4 = new Object[] {2, new int[] {1,1,2,1}, 3};
		Object[] tc5 = new Object[] {1, new int[] {-1,-1,1}, 0};
		Object[] tc6 = new Object[] {2, new int[] {-1,-1,-1}, -2};
		Object[] tc7 = new Object[] {3, new int[] {1,-1,0}, 0};
		Object[] tc8 = new Object[] {6, new int[] {0,0,0}, 0};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {
		
		@Override
		public int applyAsInt(int[] t, Integer u) {
			int count = 0;
			Map<Integer, Integer> map = new HashMap<>();
			map.put(0, 1);
			for (int sum = 0, i = 0; i < t.length; i++) {
				sum += t[i];
				count += map.getOrDefault(sum - u, 0);
				map.compute(sum, (k, v) -> v == null ? 1 : v + 1);
			}
			return count;
		}
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
	
}
