package org.ip.array;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/squares-of-a-sorted-array/">LC: 977</a>
 */
public class SquareSorted {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {0,1,9,16,100}, new int[]{-4,-1,0,3,10}};
		Object[] tc2 = new Object[] { new int[] {4,9,9,49,121}, new int[] {-7,-3,2,3,11}};
		Object[] tc3 = new Object[] { new int[] {1,4,9}, new int[] {-3,-2,-1}};
		Object[] tc4 = new Object[] { new int[] {1,4,9}, new int[] {1,2,3}};
		Object[] tc5 = new Object[] { new int[] {1,4,9,16}, new int[] {-4,-3,1,2}};
		Object[] tc6 = new Object[] { new int[] {1,4,9}, new int[] {-2,-1,3}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5, tc6};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t) {
			int negative = -1;
			for (int i = 0; i < t.length; i++) {
				if (t[i] < 0) {
					negative = i;
				}
				t[i] = (int) Math.pow(t[i], 2);
			}
			if (negative == -1) return t;
			if (negative == t.length - 1) return reverse(t); 
			int[] res = new int[t.length];
			for (int l = 0, r = t.length - 1, i = t.length - 1; i >= 0; i--) {
				int lval = l <= negative ? t[l] : Integer.MIN_VALUE;
				int rval = r > negative ? t[r] : Integer.MIN_VALUE;
				if (lval > rval) {
					res[i] = t[l++];
				} else {
					res[i] = t[r--];
				}
			}
			return res;
		}
		static int[] reverse(int[] t) {
			for (int l = 0, r = t.length - 1; l < r; l++, r--) {
				swap(t, l, r);
			}
			return t;
		}
		static void swap(int[] array, int i, int j) {
			int tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
	}
	interface Problem extends Function<int[], int[]> {
		
	}
}
