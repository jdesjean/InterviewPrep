package org.ip.array;

import java.util.ArrayList;
import java.util.List;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/intersection-of-three-sorted-arrays/">LC: 1213</a>
 */
public class IntersectionThreeSortedArray {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,5}, new int[] {1,2,3,4,5}, new int[] {1,2,5,7,9}, new int[] {1,3,4,5,8}};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<Integer> apply(int[] a, int[] b, int[] c) {
			List<Integer> res = new ArrayList<>();
			for (int i = 0, j = 0, k = 0; i < a.length && j < b.length && k < c.length;) {
				if (a[i] == b[j] && a[i] == c[k]) {
					res.add(a[i]);
					i++;
					j++;
					k++;
				} else {
					if (a[i] < b[j]) {
						i++;
					} else if (b[j] < c[k]) {
						j++;
					} else {
						k++;
					}
				}
			}
			return res;
		}
		
	}
	interface Problem extends TriFunction<int[], int[], int[], List<Integer>> {
		
	}
}
