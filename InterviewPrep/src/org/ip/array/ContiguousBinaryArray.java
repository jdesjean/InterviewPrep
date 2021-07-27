package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/contiguous-array/">LC: 525</a>
 */
public class ContiguousBinaryArray {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 2, new int[] {0,1}};
		Object[] tc2 = new Object[] { 2, new int[] {0,1,0}};
		
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			if (value.length == 0) return 0;
			int count = 0;
			int length = 0;
			Map<Integer, Integer> counts = new HashMap<>();
			counts.put(0, -1);
			for (int i = 0; i < value.length; i++) {
				count += value[i] == 1 ? 1 : -1;
				final int j = i;
				int index = counts.computeIfAbsent(count, (v) -> j);
				if (index == i) continue;
				length = Math.max(length, i - index);
			}
			return length;
		}
		
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
