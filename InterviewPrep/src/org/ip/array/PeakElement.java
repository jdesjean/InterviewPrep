package org.ip.array;

import java.util.function.ToIntFunction;

import org.ip.Test;
import org.ip.array.PivotIndex.Problem;
import org.ip.array.PivotIndex.Solver;

/**
 * <a href="https://leetcode.com/problems/find-peak-element/">LC: 162</a>
 */
public class PeakElement {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[] {1,2,3,1}};
		Object[] tc2 = new Object[] {5, new int[] {1,2,1,3,5,6,4}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			for (int i = 0; i < value.length; i++) {
				if ((i == 0 || value[i] > value[i - 1])
						&& (i == value.length -1 || value[i] > value[i + 1])) {
					return i;
				}
			}
			return -1;
		}
		
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
