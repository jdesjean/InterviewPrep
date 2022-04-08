package org.ip.array;

import org.ip.Test;
import org.ip.Test.TriConsumer;

/**
 * <a href="https://leetcode.com/problems/merge-sorted-array/">LC: 88</a>
 */
public class MergeSorted {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,2,2,3,5,6}, new int[] {1,2,3,0,0,0}, 3, new int[] {2,5,6}, 3};
		Object[] tc2 = new Object[] {new int[] {1}, new int[] {1}, 1, new int[] {}, 0};
		Object[] tc3 = new Object[] {new int[] {1,1,1}, new int[] {1,1,0}, 2, new int[] {1}, 1};
		Object[] tc4 = new Object[] {new int[] {1}, new int[] {0}, 0, new int[] {1}, 1};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public void accept(int[] a, Integer b, int[] c) {
			int j = c.length - 1;
			for (int i = b - 1, k = a.length - 1; i >= 0 && j >= 0; ) {
				if (a[i] > c[j]) {
					a[k--] = a[i--];
				} else {
					a[k--] = c[j--];
				}
			}
			System.arraycopy(c, 0, a, 0, j + 1);
		}
		
	}
	public interface Problem extends TriConsumer<int[], Integer, int[]> {
		
	}
}
