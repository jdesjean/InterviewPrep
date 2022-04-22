package org.ip.array;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/first-missing-positive/">LC: 41</a>
 */
public class FirstMissingPositive {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new int[] {1,2,0}};
		Object[] tc2 = new Object[] {2, new int[] {3,4,-1,1}};
		Object[] tc3 = new Object[] {1, new int[] {7,8,9,11,12}};
		Object[] tc4 = new Object[] {2, new int[] {1}};
		Object[] tc5 = new Object[] {1, new int[] {-5}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			for (int i = 0; i < value.length; i++) {
				while (value[i] > 0 && value[i] != i + 1 && value[i] <= value.length && value[i] != value[value[i] - 1]) { 
					swap(value, i, value[i] - 1);
				}
			}
			for (int i = 0; i < value.length; i++) {
				if (value[i] != i + 1) return i + 1;
			}
			return value.length + 1;
		}
		void swap(int[] value, int i, int j) {
			int tmp = value[i];
			value[i] = value[j];
			value[j] = tmp;
		}
	}
		
	interface Problem extends ToIntFunction<int[]> {
	}
}
