package org.ip.array;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/previous-permutation-with-one-swap/">LC: 1053</a>
 * 
 * find 1st element greater than previous element
 * swap with next biggest (either value or index)
 */
public class PreviousPermutation {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {3,1,2}, new int[]{3,2,1}};
		Object[] tc2 = new Object[] { new int[] {1,1,5}, new int[] {1,1,5}};
		Object[] tc3 = new Object[] { new int[] {1,7,4,6,9}, new int[] {1,9,4,6,7}};
		Object[] tc4 = new Object[] { new int[] {1,3,1,3}, new int[] {3,1,1,3}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t) {
			int i = t.length - 2;
			for (; i >= 0; i--) {
				if (t[i] > t[i + 1]) break;
			}
			if (i < 0) return t;
			int j = t.length - 1;
			for (; j > i;  j--) {
				if (t[j] >= t[i] || (j > 0 && t[j] == t[j - 1])) continue;
				break;
			}
			swap(t, i, j);
			return t;
		}
		void swap(int[] t, int i, int j) {
			int tmp = t[i];
			t[i] = t[j];
			t[j] = tmp;
		}
		
	}
	interface Problem extends Function<int[], int[]> {
		
	}
}
