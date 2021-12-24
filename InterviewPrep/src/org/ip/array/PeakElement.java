package org.ip.array;

import java.util.Set;
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
		Object[] tc2 = new Object[] {Set.of(1, 5), new int[] {1,2,1,3,5,6,4}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2(), new Solver3() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			int l = 0;
			for (int r = value.length - 1; l < r; ) {
				int m = l + (r - l) / 2;
				if (value[m] < value[m + 1]) l = m + 1;
				else r = m;
			}
			return l;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			for (int i = 0; i < value.length; i++) {
				if (i == value.length - 1 || value[i] > value[i + 1]) return i;
			}
			return -1;
		}
		
	}
	static class Solver3 implements Problem {

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
