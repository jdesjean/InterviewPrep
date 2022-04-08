package org.ip.matrix;

import java.util.function.IntPredicate;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-operations-to-make-a-uni-value-grid/">LC: 2033</a>
 */
public class MinimumOperationsMakeUniValueGrid {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4, new int[][] {{2,4},{6,8}}, 2};
		Object[] tc2 = new Object[] {5, new int[][] {{1,5},{2,3}}, 1};
		Object[] tc3 = new Object[] {-1, new int[][] {{1,2},{3,4}}, 2};
		Object[] tc4 = new Object[] {25, new int[][] {{529,529,989},{989,529,345},{989,805,69}}, 92};
		Object[] tc5 = new Object[] {45, new int[][] {{980,476,644,56},{644,140,812,308},{812,812,896,560},{728,476,56,812}}, 84};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] t, Integer u) {
			if (t == null || t.length == 0) return -1;
			int k = u;
			int[] a = toArray(t, k);
			if (a == null) return -1;
			int median = median(a); // TODO: quick select on 2d array
			int res = 0;
			for (int i = 0; i < a.length; i++) {
				int diff = Math.abs(a[i] - median);
				res += diff / k;
			}
			return res;
		}
		int[] toArray(int[][] t, int k) {
			int[] res = new int[t.length * t[0].length];
			int rem = t[0][0] % k;
			for (int l = 0; l < t.length ; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] % k != rem) return null;
					res[l * t[l].length + c] = t[l][c];
				}
			}
			return res;
		}
		int median(int[] t) {
			int mid = (t.length - 1) / 2;
			IntPredicate p = t.length % 2 == 0 ? (v) -> v == mid || v == mid + 1 : (v) -> v == mid;
			for (int l = 0, r = t.length -1; l < r; ) {
				int m = l + (r - l) / 2;
				int pivot = partition(t, l, r, m);
				if (p.test(pivot)) {
					break;
				} else if (pivot < mid) {
					l = pivot + 1;
				} else {
					r = pivot - 1;
				}
			}
			return t[mid];
		}
		int partition(int[] a, int left, int right, int pivot) {
			int j = left;
			int p = a[pivot];
			swap(a, pivot, right);
			for (int i = left; i < right; i++) {
				if (a[i] < p) {
					swap(a, j, i);
					j++;
				}
			}
			swap(a, j, right);
			return j;
		}
		public static void swap(int[] array, int i, int j) {
			if (i == j) return;
			int tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
	}
	interface Problem extends ToIntBiFunction<int[][], Integer> {
		
	}
}
